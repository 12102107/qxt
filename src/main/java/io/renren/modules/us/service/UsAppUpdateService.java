package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsAppUpdateEntity;
import io.renren.modules.us.param.UsBaseParam;

/**
 * @author sys
 * @date 2018-04-12 16:21:17
 */
public interface UsAppUpdateService extends IService<UsAppUpdateEntity> {

    R list(UsBaseParam baseParam);

}

