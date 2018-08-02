package io.renren.modules.us.util;

import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsElectronicCardNumberService;
import io.renren.modules.us.service.UsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 返回user公共信息
 * @author gaoxipeng
 *
 */
@Component
public class UsUserUtils {
    @Autowired
    private UsUserService usUserService;
    @Autowired
    private UsElectronicCardNumberService usElectronicCardNumber;

    public UsUserEntity getUser(String id) {
        UsUserEntity usUserEntity = usUserService.selectById(id);
        String cardnumber=usElectronicCardNumber.electronicCardNumber(id);
        usUserEntity.setCardNumber(cardnumber);
        return usUserEntity;
    }

}
