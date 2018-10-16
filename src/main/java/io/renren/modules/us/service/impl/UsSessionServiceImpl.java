package io.renren.modules.us.service.impl;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsSessionService;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("usSessionService")
public class UsSessionServiceImpl implements UsSessionService {

    private UsSessionUtil sessionUtil;

    @Override
    public R getUserId(UsSessionParam param) {
        ValidatorUtils.validateEntity(param);
        String userId = sessionUtil.getUserId(param.getSession());
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        return R.ok(map);
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }
}
