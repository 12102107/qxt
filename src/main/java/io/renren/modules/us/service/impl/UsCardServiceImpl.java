package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.dao.UsCardDao;
import io.renren.modules.us.entity.UsCardEntity;
import io.renren.modules.us.entity.UsCardMenuEntity;
import io.renren.modules.us.entity.UsCardNumberEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsBaseParam;
import io.renren.modules.us.param.UsCardDetailParam;
import io.renren.modules.us.param.UsCardUpdateParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsCardMenuService;
import io.renren.modules.us.service.UsCardNumberService;
import io.renren.modules.us.service.UsCardService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("usCardService")
public class UsCardServiceImpl extends ServiceImpl<UsCardDao, UsCardEntity> implements UsCardService {

    private UsUserService userService;
    private UsCardNumberService cardNumberService;
    private UsCardMenuService cardMenuService;
    private UsSessionUtil sessionUtil;
    @Value("${us.img.cardImg}")
    private String cardImg;

    @Override
    public R list(UsSessionParam param) {
        ValidatorUtils.validateEntity(param);
        String userId = sessionUtil.getUserId(param.getSession());
        UsUserEntity user = userService.selectById(userId);
        //根据用户ID获取用户卡号和卡ID
        EntityWrapper<UsCardNumberEntity> cardNumberWrapper = new EntityWrapper<>();
        cardNumberWrapper.where("uid = {0}", userId);
        List<UsCardNumberEntity> cardNumberList = cardNumberService.selectList(cardNumberWrapper);
        if (cardNumberList.isEmpty()) {
            return R.error("用户没有卡号");
        }
        //卡ID和卡号
        Map<String, String> cardNumberMap = new HashMap<>();
        //卡ID和卡余额
        Map<String, Double> cardBalanceMap = new HashMap<>();
        for (UsCardNumberEntity cardNumber : cardNumberList) {
            cardNumberMap.put(cardNumber.getUsCardId(), cardNumber.getElectronicCardNumber());
            if (cardNumber.getBalance() != null) {
                cardBalanceMap.put(cardNumber.getUsCardId(), cardNumber.getBalance());
            }
        }
        //根据用户拥有的卡ID获取卡信息
        EntityWrapper<UsCardEntity> cardWrapper = new EntityWrapper<>();
        cardWrapper.setSqlSelect("id", "card_type as cardType", "card_name as cardName", "card_alias as cardAlias", "qr_url as qrUrl"
                , "card_back_simg as cardBackSimg", "card_back_bimg as cardBackBimg", "is_payable as isPayable")
                .where("status = {0}", "1")
                .in("id", cardNumberMap.keySet().toArray())
                .orderBy("card_order", true);
        List<Map<String, Object>> cardList = this.selectMaps(cardWrapper);
        if (cardList.isEmpty()) {
            return R.error("没有卡");
        }
        //生成返回结果
        Map<String, Object> result = new HashMap<>();
        for (Map<String, Object> m : cardList) {
            //如果是身份证卡增加真实姓名返回值,并增加到idCard返回对象中
            if (m.get("cardType").toString().equals("0")) {
                m.put("realname", user.getRealname());
                m.put("electronicCardNumber", cardNumberMap.get(m.get("id").toString()));
                m.put("cardBackSimg", cardImg + m.get("cardBackSimg"));
                m.put("cardBackBimg", cardImg + m.get("cardBackBimg"));
                result.put("idCard", m);
            }
            //如果是可支付的卡则增加卡余额返回值,不是则不增加
            if (m.get("isPayable").toString().equals("1")) {
                m.put("balance", cardBalanceMap.get(m.get("id").toString()));
            }
            m.put("electronicCardNumber", cardNumberMap.get(m.get("id").toString()));
            m.put("cardBackSimg", cardImg + m.get("cardBackSimg"));
            m.put("cardBackBimg", cardImg + m.get("cardBackBimg"));
        }
        cardList.remove(result.get("idCard"));
        result.put("otherCardList", cardList);
        return R.ok(result);
    }

    @Override
    public R detail(UsCardDetailParam param) {
        ValidatorUtils.validateEntity(param);
        String cardId = param.getCardId();
        String userId = sessionUtil.getUserId(param.getSession());
        //查询卡号
        EntityWrapper<UsCardNumberEntity> cardNumberWrapper = new EntityWrapper<>();
        cardNumberWrapper.where("uid = {0}", userId)
                .and("us_card_id = {0}", cardId);
        UsCardNumberEntity cardNumberEntity = cardNumberService.selectOne(cardNumberWrapper);
        if (cardNumberEntity == null) {
            return R.error("用户卡号不存在");
        }
        //查询卡信息
        EntityWrapper<UsCardEntity> cardWrapper = new EntityWrapper<>();
        cardWrapper.setSqlSelect("id", "card_type as cardType", "card_name as cardName", "card_alias as cardAlias", "qr_url as qrUrl"
                , "card_back_simg as cardBackSimg", "card_back_bimg as cardBackBimg")
                .where("status = {0}", "1")
                .and("id = {0}", cardId);
        Map<String, Object> cardEntity = this.selectMap(cardWrapper);
        if (cardEntity == null) {
            return R.error("卡不存在");
        }
        cardEntity.put("electronicCardNumber", cardNumberEntity.getElectronicCardNumber());
        cardEntity.put("cardBackSimg", cardImg + cardEntity.get("cardBackSimg"));
        cardEntity.put("cardBackBimg", cardImg + cardEntity.get("cardBackBimg"));
        //查询父菜单
        EntityWrapper<UsCardMenuEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("id", "menu_name", "menu_icon", "card_url")
                .where("card_id = {0}", cardId)
                .and("status = {0}", "1")
                .and("parent_id is null")
                .orderBy("card_order", true);
        List<UsCardMenuEntity> parentList = cardMenuService.selectList(w1);
        if (parentList.isEmpty()) {
            cardEntity.put("menuList", new ArrayList<>(1));
            return R.ok(cardEntity);
        }
        List<String> parentId = new ArrayList<>();
        for (UsCardMenuEntity u : parentList) {
            parentId.add(u.getId());
        }
        //查询子菜单
        EntityWrapper<UsCardMenuEntity> w2 = new EntityWrapper<>();
        w2.setSqlSelect("id", "menu_name", "menu_icon", "card_url", "parent_id")
                .where("status = {0}", "1")
                .in("parent_id", parentId.toArray())
                .orderBy("card_order", true);
        List<UsCardMenuEntity> childList = cardMenuService.selectList(w2);
        //生成返回结果
        List<Map<String, Object>> menuList = new ArrayList<>();
        for (UsCardMenuEntity parent : parentList) {
            Map<String, Object> object = new HashMap<>();
            parent.setMenuIcon(parent.getMenuIcon() == null ? "" : cardImg + parent.getMenuIcon());
            object.put("parentMenu", parent);
            List<UsCardMenuEntity> childMenu = new ArrayList<>();
            for (UsCardMenuEntity child : childList) {
                if (child.getParentId().equals(parent.getId())) {
                    child.setMenuIcon(child.getMenuIcon() == null ? "" : cardImg + child.getMenuIcon());
                    childMenu.add(child);
                }
            }
            object.put("childMenu", childMenu);
            menuList.add(object);
        }
        cardEntity.put("menuList", menuList);
        return R.ok(cardEntity);
    }

    @Override
    public R update(UsCardUpdateParam param) {
        ValidatorUtils.validateEntity(param);
        return R.error();
    }

    @Override
    public R partnerList(UsBaseParam param) {
        ValidatorUtils.validateEntity(param);
        EntityWrapper<UsCardEntity> cardWrapper = new EntityWrapper<>();
        cardWrapper.setSqlSelect("id as partner", "card_name as cardName", "card_alias as cardAlias")
                .where("appid = {0}", param.getAppid())
                .and("status = {0}", "1");
        List<Map<String, Object>> list = this.selectMaps(cardWrapper);
        return R.ok(list);
    }

    @Autowired
    public void setUserService(UsUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCardNumberService(UsCardNumberService cardNumberService) {
        this.cardNumberService = cardNumberService;
    }

    @Autowired
    public void setCardMenuService(UsCardMenuService cardMenuService) {
        this.cardMenuService = cardMenuService;
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }
}
