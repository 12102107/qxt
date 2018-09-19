package io.renren.modules.us.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.dao.UsPayOrderDao;
import io.renren.modules.us.entity.UsCardNumberEntity;
import io.renren.modules.us.entity.UsPayOrderEntity;
import io.renren.modules.us.param.UsPayDetailParam;
import io.renren.modules.us.param.UsPayListParam;
import io.renren.modules.us.param.UsPayOrderNotifyParam;
import io.renren.modules.us.param.UsPayOrderParam;
import io.renren.modules.us.service.UsCardNumberService;
import io.renren.modules.us.service.UsPayOrderService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsPayUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("usPayOrderService")
public class UsPayOrderServiceImpl extends ServiceImpl<UsPayOrderDao, UsPayOrderEntity> implements UsPayOrderService {

    private UsPayUtil payUtil;

    private UsSessionUtil sessionUtil;

    private UsCardNumberService cardNumberService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public R order(UsPayOrderParam param, HttpServletRequest request) throws IOException {
        ValidatorUtils.validateEntity(param);
        //验证卡是否可充值和用户是否有卡
        boolean userCardIsPayable = cardNumberService.verifyUserCardIsPayable(sessionUtil.getUserId(param.getSession()), param.getCardId());
        if (!userCardIsPayable) {
            return R.error("用户没有卡或卡不可以充值");
        }
        String channelId = "";
        switch (param.getChannel()) {
            case "0":
                channelId = "WX_APP";
                break;
            case "1":
                channelId = "ALIPAY_MOBILE";
                break;
        }
        String orderNo = String.valueOf(System.currentTimeMillis());
        int amount = (int) (param.getAmount() * 100);
        String result = payUtil.createOrder(orderNo, channelId, amount, "cny", "192.168.1.1", "APP"
                , param.getSubject(), param.getBody(), "", "", "");
        logger.info("下单结果:" + result);
        JSONObject object = JSONObject.parseObject(result);
        //请求成功&&下单成功
        if ("SUCCESS".equals(object.get("retCode")) && "SUCCESS".equalsIgnoreCase(object.get("resCode").toString())) {
            //验证签名
            boolean b = payUtil.orderSignIsValid(result);
            logger.info("结果签名是否有效:" + b);
            if (b) {
                //保存结果到数据库
                UsPayOrderEntity order = new UsPayOrderEntity();
                order.setId(UsIdUtil.generateId());
                order.setUserId(sessionUtil.getUserId(param.getSession()));
                order.setCardId(param.getCardId());
                order.setAppid(param.getAppid());
                order.setOrderNo(orderNo);
                order.setSubject(param.getSubject());
                order.setBody(param.getBody());
                order.setChannel(param.getChannel());
                order.setType("0");
                order.setAmount(param.getAmount());
                order.setCurrency("cny");
                order.setStatus("0");
                Date date = new Date();
                order.setCreateDate(date);
                order.setUpdateDate(date);
                this.insert(order);
                //返回结果
                Object payParams = object.get("payParams");
                Map<String, Object> map = new HashMap<>();
                map.put("channel", param.getChannel());
                switch (param.getChannel()) {
                    case "0":
                        map.put("payParams", payParams);
                        return R.ok(map);
                    case "1":
                        Map<String, Object> m = new HashMap<>();
                        m.put("str", payParams);
                        map.put("payParams", m);
                        return R.ok(map);
                }
            } else {
                return R.error("签名未通过验证");
            }
        }
        //请求成功&&下单失败
        if ("SUCCESS".equals(object.get("retCode")) && "FAIL".equalsIgnoreCase(object.get("resCode").toString())) {
            return R.error(object.get("errCodeDes").toString());
        }
        //请求失败
        if ("FAIL".equals(object.get("retCode"))) {
            return R.error("请求失败");
        }
        return R.error();
    }

    @Override
    @Scope(value = "singleton")
    public String orderNotify(UsPayOrderNotifyParam param) {
        logger.info("收到通知" + param.toString());
        String orderNo = param.getMchOrderNo();
        if (orderNo == null || "".equals(orderNo)) {
            return "fail";
        }
        EntityWrapper<UsPayOrderEntity> wrapper = new EntityWrapper<>();
        wrapper.where("order_no = {0}", orderNo);
        UsPayOrderEntity order = this.selectOne(wrapper);
        if (order == null) {
            return "fail";
        }
        if (order.getStatus().equals("2")) {
            return "success";
        }
        String status = param.getStatus().toString();
        if (status.equals("1")) {
            return "success";
        }
        if (status.equals("2")) {
            settlement(order);
            return "success";
        }
        return "fail";
    }

    @Override
    public R list(UsPayListParam param) {
        ValidatorUtils.validateEntity(param);
        EntityWrapper<UsPayOrderEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "subject", "update_date", "type", "amount")
                .where("appid = {0}", param.getAppid())
                .and("user_id = {0}", sessionUtil.getUserId(param.getSession()))
                .and("card_id = {0}", param.getCardId())
                .and("status = {0}", "2")
                .orderBy("update_date", false);
        List<UsPayOrderEntity> list = this.selectList(wrapper);
        return R.ok(list);
    }

    @Override
    public R detail(UsPayDetailParam param) {
        ValidatorUtils.validateEntity(param);
        EntityWrapper<UsPayOrderEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "order_no", "channel", "type", "status", "subject"
                , "body", "update_date", "amount", "balance", "remark")
                .where("appid = {0}", param.getAppid())
                .and("user_id = {0}", sessionUtil.getUserId(param.getSession()))
                .and("id = {0}", param.getOrderId());
        UsPayOrderEntity order = this.selectOne(wrapper);
        return R.ok(order);
    }

    @Transactional
    synchronized void settlement(UsPayOrderEntity order) {
        //收入
        if ("0".equals(order.getType())) {
            UsCardNumberEntity cardNumber = cardNumberService.getUserCard(order.getUserId(), order.getCardId());
            if (cardNumber == null) {
                throw new RRException("用户卡不存在");
            }
            double balance = cardNumber.getBalance();
            double amount = order.getAmount();
            double newBalance = balance + amount;
            cardNumber.setBalance(newBalance);
            cardNumberService.updateById(cardNumber);
            order.setBalance(newBalance);
            order.setStatus("2");
            order.setUpdateDate(new Date());
            this.updateById(order);
        }
    }

    @Autowired
    public void setPayUtil(UsPayUtil payUtil) {
        this.payUtil = payUtil;
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Autowired
    public void setCardNumberService(UsCardNumberService cardNumberService) {
        this.cardNumberService = cardNumberService;
    }
}
