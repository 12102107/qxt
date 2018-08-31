package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.us.entity.UsCardNumberEntity;

/**
 *
 *
 * @author gaoxipeng
 * @email
 * @date 2018-07-13 
 */
public interface UsElectronicCardNumberService extends IService<UsCardNumberEntity> {

    String electronicCardNumber(String id);

    String getElectronicCardNumber(String userId);

}