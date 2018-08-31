package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsCardEntity;
import io.renren.modules.us.param.UsCardDetailParam;
import io.renren.modules.us.param.UsSessionParam;

/**
 * @author sys
 */
public interface UsCardService extends IService<UsCardEntity> {
    R list(UsSessionParam param);

    R detail(UsCardDetailParam param);
}