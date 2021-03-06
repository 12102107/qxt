package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.param.UsResourceListParam;
import io.renren.modules.us.param.UsResourceParam;

/**
 * @author sys
 * @date 2018-04-25 14:29:11
 */
public interface UsResourceService extends IService<UsResourceEntity> {

    R list(UsResourceListParam resourceParam);

}

