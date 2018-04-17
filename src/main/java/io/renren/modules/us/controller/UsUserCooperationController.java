package io.renren.modules.us.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.us.param.UsUserCooperationParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.service.UsUserCooperationService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * @author sys
 * @date 2018-04-12 16:21:17
 */
@RestController
@RequestMapping("/api/cooperation")
@Api("第三方接口")
public class UsUserCooperationController {

    @Autowired
    private UsUserCooperationService cooperationService;

    /**
     * 第三方登录接口
     */
    @PostMapping("signin")
    @ApiOperation("第三方登录接口")
    public R signin(@RequestBody UsUserCooperationParam cooperationParam) {
        System.out.println(1);
        ValidatorUtils.validateEntity(cooperationParam);
        UsUserCooperationEntity cooperation = new UsUserCooperationEntity();
        cooperation.setType("1");
        cooperation.setOpenid("12345");
        cooperation.setId("12345");
        cooperationService.insert(cooperation);
        return new R();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:ususercooperation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cooperationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:ususercooperation:info")
    public R info(@PathVariable("id") String id) {
        UsUserCooperationEntity usUserCooperation = cooperationService.selectById(id);

        return R.ok().put("usUserCooperation", usUserCooperation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:ususercooperation:save")
    public R save(@RequestBody UsUserCooperationEntity usUserCooperation) {
        cooperationService.insert(usUserCooperation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:ususercooperation:update")
    public R update(@RequestBody UsUserCooperationEntity usUserCooperation) {
        cooperationService.updateById(usUserCooperation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:ususercooperation:delete")
    public R delete(@RequestBody String[] ids) {
        cooperationService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
