package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.param.UsUserCooperationParam;

/**
 * @author sys
 * @date 2018-04-12 16:21:17
 */
public interface UsUserCooperationService extends IService<UsUserCooperationEntity> {
    R signIn(UsUserCooperationParam cooperationParam);
}

