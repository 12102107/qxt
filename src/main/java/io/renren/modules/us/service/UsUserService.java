package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsLoginParam;
import io.renren.modules.us.param.UsUserParam;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-12 16:26:30
 */
public interface UsUserService extends IService<UsUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R signIn(UsLoginParam form);

    UsUserEntity checkUserExits(String userId, String oldPassword);

    UsUserEntity updatePersonalInfo(UsUserEntity user, UsUserParam form);
}

