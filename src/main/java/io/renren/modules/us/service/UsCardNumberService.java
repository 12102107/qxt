package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.us.entity.UsCardNumberEntity;

/**
 * @author sys
 * @email
 * @date 2018-08-29 13:09:39
 */
public interface UsCardNumberService extends IService<UsCardNumberEntity> {

    boolean insertCardNumber(String userId, String cardId, String cardNumber, String isPayable);

    boolean verifyUserCardIsPayable(String userId, String cardId);

    UsCardNumberEntity getUserCard(String userId, String cardId);
}