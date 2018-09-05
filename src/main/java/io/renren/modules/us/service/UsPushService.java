package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsPushEntity;
import io.renren.modules.us.param.UsPushDetailParam;
import io.renren.modules.us.param.UsSessionParam;

import java.util.Map;


public interface UsPushService extends IService<UsPushEntity> {

    PageUtils list(Map<String, Object> param);

    R detail(UsPushDetailParam param);

}
