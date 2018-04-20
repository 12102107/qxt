package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsSmsEntity;
import io.renren.modules.us.param.UsSmsParam;

/**
 * @author sys
 * @email
 * @date 2018-04-18 15:54:19
 */
public interface UsSmsService extends IService<UsSmsEntity> {

    R getCode(UsSmsParam usSmsParam);

    int checkCode(String appid, String mobile, String code);
}

