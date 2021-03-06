package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author sys
 * @email
 * @date 2018-04-12 16:26:30
 */
public interface UsUserService extends IService<UsUserEntity> {

    R signIn(UsLoginParam form);

    UsUserEntity reg(UsRegisterParam form);

    UsUserEntity checkUserExits(String userId, String oldPassword);

    UsUserEntity updatePersonalInfo(UsUserEntity user, UsUserParam form);

    UsUserEntity realnameCert(UsUserEntity user, UsUserRealCertParam form) throws IOException;

    UsUserEntity queryName(UsUserEntity user);

    R uploadPortrait(UsUserEntity user, UsUserPortraiParam form);

    Map<String,Object> unifyUserDataReturned(String userId,String cardId);
}