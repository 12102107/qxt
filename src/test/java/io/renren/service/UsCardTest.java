package io.renren.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.modules.us.entity.UsCardEntity;
import io.renren.modules.us.entity.UsCardMenuEntity;
import io.renren.modules.us.entity.UsCardNumberEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsCardMenuService;
import io.renren.modules.us.service.UsCardNumberService;
import io.renren.modules.us.service.UsCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsCardTest {
    @Autowired
    private UsCardService cardService;
    @Autowired
    private UsCardNumberService cardNumberService;
    @Autowired
    private UsCardMenuService cardMenuService;

    @Test
    public void test1() {
        String userId = "e6ee158e857e401c8aa8c1b5c726ff4a";
        UsUserEntity user = new UsUserEntity();
        user.setRealname("大魔王");
        //根据用户ID获取用户卡号和卡ID
        EntityWrapper<UsCardNumberEntity> cardNumberWrapper = new EntityWrapper<>();
        cardNumberWrapper.where("uid = {0}", userId);
        List<UsCardNumberEntity> cardNumberList = cardNumberService.selectList(cardNumberWrapper);
        //List<String> cardIdList = new ArrayList<>();
        Map<String, String> cardNumberMap = new HashMap<>();
        for (UsCardNumberEntity cardNumber : cardNumberList) {
            System.out.println(cardNumber.toString());
            //cardIdList.add(cardNumber.getUsCardId());
            cardNumberMap.put(cardNumber.getUsCardId(), cardNumber.getElectronicCardNumber());
        }
        //用户没有卡
        if (cardNumberMap.isEmpty()) {
            return;
        }
        //根据用户拥有的卡ID获取卡信息
        EntityWrapper<UsCardEntity> cardWrapper = new EntityWrapper<>();
        cardWrapper.setSqlSelect("id", "card_type as cardType", "card_name as cardName", "card_alias as cardAlias", "qr_url as qrUrl"
                , "card_back_simg as cardBackSimg", "card_back_bimg as cardBackBimg")
                .where("status = {0}", "1")
                .in("id", cardNumberMap.keySet().toArray())
                .orderBy("card_order", true);
        List<Map<String, Object>> cardList = cardService.selectMaps(cardWrapper);
        //生成返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("balance", 0);
        for (Map<String, Object> m : cardList) {
            if (m.get("cardType").toString().equals("0")) {
                m.put("realname", user.getRealname());
                m.put("electronicCardNumber", cardNumberMap.get(m.get("id").toString()));
                result.put("idCard", m);
                //cardList.remove(m);
            }
            if (m.get("cardType").toString().equals("1")) {
                m.put("electronicCardNumber", cardNumberMap.get(m.get("id").toString()));
            }
        }
        cardList.remove(result.get("idCard"));
        result.put("otherCardList", cardList);
        System.out.println(result.toString());
    }

    @Test
    public void test2() {
        String cardId = "2c908105657e0d7a01657e30fef8000f";
        String userId = "e6ee158e857e401c8aa8c1b5c726ff4a";
        //查询卡号
        EntityWrapper<UsCardNumberEntity> cardNumberWrapper = new EntityWrapper<>();
        cardNumberWrapper.where("uid = {0}", userId)
                .and("us_card_id = {0}", cardId);
        UsCardNumberEntity cardNumberEntity = cardNumberService.selectOne(cardNumberWrapper);
        if (cardNumberEntity == null) {
            return;
        }
        //查询卡信息
        EntityWrapper<UsCardEntity> cardWrapper = new EntityWrapper<>();
        cardWrapper.setSqlSelect("id", "card_type as cardType", "card_name as cardName", "card_alias as cardAlias", "qr_url as qrUrl"
                , "card_back_simg as cardBackSimg", "card_back_bimg as cardBackBimg")
                .where("status = {0}", "1")
                .and("id = {0}", cardId);
        Map<String, Object> cardEntity = cardService.selectMap(cardWrapper);
        if (cardEntity == null) {
            return;
        }
        //查询父菜单
        EntityWrapper<UsCardMenuEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("id", "menu_name", "menu_icon", "card_url")
                .where("card_id = {0}", cardId)
                .and("status = {0}", "1")
                .and("parent_id is null")
                .orderBy("card_order", true);
        List<UsCardMenuEntity> parentList = cardMenuService.selectList(w1);
        if (parentList.isEmpty()) {
            return;
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
            parent.setMenuIcon("http" + parent.getMenuIcon());
            object.put("parentMenu", parent);
            List<UsCardMenuEntity> childMenu = new ArrayList<>();
            for (UsCardMenuEntity child : childList) {
                if (child.getParentId().equals(parent.getId())) {
                    child.setMenuIcon("http" + child.getMenuIcon());
                    childMenu.add(child);
                }
            }
            object.put("childMenu", childMenu);
            menuList.add(object);
        }
        cardEntity.put("menuList", menuList);
        System.out.println("1");
    }
}
