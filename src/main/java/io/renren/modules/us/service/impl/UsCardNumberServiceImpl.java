package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.us.dao.UsCardNumberDao;
import io.renren.modules.us.entity.UsCardNumberEntity;
import io.renren.modules.us.service.UsCardNumberService;
import io.renren.modules.us.util.UsIdUtil;
import org.springframework.stereotype.Service;


@Service("usCardNumberService")
public class UsCardNumberServiceImpl extends ServiceImpl<UsCardNumberDao, UsCardNumberEntity> implements UsCardNumberService {

    @Override
    public boolean insertCardNumber(String userId, String cardId, String cardNumber, String isPayable) {
        UsCardNumberEntity cardNumberEntity = new UsCardNumberEntity();
        cardNumberEntity.setId(UsIdUtil.generateId());
        cardNumberEntity.setUid(userId);
        cardNumberEntity.setUsCardId(cardId);
        cardNumberEntity.setElectronicCardNumber(cardNumber);
        if ("1".equals(isPayable)) {
            cardNumberEntity.setBalance(0D);
        }
        return this.insert(cardNumberEntity);
    }

    @Override
    public boolean verifyUserCardIsPayable(String userId, String cardId) {
        UsCardNumberEntity cardNumberEntity = getUserCard(userId, cardId);
        return cardNumberEntity != null && cardNumberEntity.getBalance() != null;
    }

    @Override
    public UsCardNumberEntity getUserCard(String userId, String cardId) {
        EntityWrapper<UsCardNumberEntity> cardNumberWrapper = new EntityWrapper<>();
        cardNumberWrapper.where("uid = {0}", userId)
                .and("us_card_id = {0}", cardId);
        return this.selectOne(cardNumberWrapper);
    }
}