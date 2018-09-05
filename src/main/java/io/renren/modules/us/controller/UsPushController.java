package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.UsCardService;
import io.renren.modules.us.service.UsPushService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author sys
 */
@RestController
@RequestMapping("/api/push")
@Api("推送接口")
public class UsPushController {

    @Autowired
    private UsPushService pushService;
    @Autowired
    private UsUserService userService;
    @Autowired
    private UsSessionUtil sessionUtil;

    @PostMapping("list")
    @ApiOperation("推送列表接口")
    public R list(@RequestBody UsPushListParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        UsUserEntity user = userService.selectById(userId);

        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();
        params.put("userId",userId);
        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        PageUtils page = pushService.list(params);
        return R.ok(page);
    }

    @PostMapping("detail")
    @ApiOperation("推送详请接口")
    public R detail(@RequestBody UsPushDetailParam param) {
        return pushService.detail(param);
    }

}
