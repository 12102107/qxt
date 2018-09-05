package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.dao.UsCardDao;
import io.renren.modules.us.dao.UsPushDao;
import io.renren.modules.us.entity.*;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.*;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("usPushService")
public class UsPushServiceImpl extends ServiceImpl<UsPushDao, UsPushEntity> implements UsPushService {

    @Autowired
    private UsUserService userService;
    @Autowired
    private UsPushService usPushService;
    @Autowired
    private UsSessionUtil sessionUtil;


    @Override
    public PageUtils list(Map<String, Object> param) {
        EntityWrapper<UsPushEntity> w1 = new EntityWrapper<>();
        Page<UsPushEntity> page = this.selectPage(
                new Query<UsPushEntity>(param).getPage(),
                w1.setSqlSelect("id",  "push_title as pushTitle", "push_content as pushContent","push_img as pushImg", "create_time as createTime")
                .where("user_id = {0}", param.get("userId"))
                .orderBy("create_time", false) );  // 时间的倒叙排列

        return new PageUtils(page);

    }

    /**
     * 推送详情
     * @param param
     * @return
     */
    @Override
    public R detail(UsPushDetailParam param) {
        ValidatorUtils.validateEntity(param);
        String userId = sessionUtil.getUserId(param.getSession());
        String id = param.getId();

        EntityWrapper<UsPushEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("id", "user_id as userId", "client_id as clientId", "push_title as pushTitle", "push_content as pushContent"
                , "push_img as pushImg", "create_time as createTime");
               w1.where("user_id = {0}", userId).and("id = {0}", id);
        List<UsPushEntity> pushList = usPushService.selectList(w1);
        if(pushList.size()==0||pushList.isEmpty()){
            return R.error("消息详情为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pushDetailList",pushList);
        return R.ok(map);
    }




}
