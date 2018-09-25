package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsPayOrderEntity;
import io.renren.modules.us.param.UsPayDetailParam;
import io.renren.modules.us.param.UsPayListParam;
import io.renren.modules.us.param.UsPayOrderNotifyParam;
import io.renren.modules.us.param.UsPayOrderParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author sys
 * @email
 * @date 2018-09-17 14:43:06
 */
public interface UsPayOrderService extends IService<UsPayOrderEntity> {

    R order(UsPayOrderParam param, HttpServletRequest request) throws IOException;

    String orderNotify(UsPayOrderNotifyParam param);

    R list(UsPayListParam param);

    R detail(UsPayDetailParam param);

    void settlement(UsPayOrderEntity order);

    R charge(UsPayOrderParam param);
}