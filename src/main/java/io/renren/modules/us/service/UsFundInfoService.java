package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsFundInfoEntity;
import io.renren.modules.us.param.UsFundInfoParam;

/**
 * @author sys
 * @date 2018-04-28 14:01:06
 */
public interface UsFundInfoService extends IService<UsFundInfoEntity> {

    R info(UsFundInfoParam fundInfoParam);

}