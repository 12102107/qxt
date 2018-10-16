package io.renren.modules.us.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsSmsEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.UsSmsService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sys
 * @date 2018-04-12 16:26:30
 */
@RestController
@Api("基础接口")
public class UsUserController {
    @Autowired
    private UsUserService usUserService;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private UsSmsService usSmsService;
    @Autowired
    private UsSessionUtil sessionUtil;
    @Autowired
    private UsCardNumberUtil cardNumberUtil;

    /**
     * 验证手机号码是否注册
     */
    @PostMapping("/api/user/checkIsRegister")
    @ApiOperation("验证手机号码是否注册")
    public R checkIsRegister(@RequestBody UsIsRegParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        Map hp = new HashMap();
        hp.put("mobile_phone", form.getMobilePhone());
        hp.put("appid", form.getAppid());
        List rs = usUserService.selectByMap(hp);
        if (rs.size() > 0) {
            return R.error(Constant.Result.REG_MOBILE.getValue(), Constant.Message.REG_MOBILE.getValue());
        } else {
            return R.ok();
        }
    }

    /**
     * 找回密码
     */
    @PostMapping("/api/user/findPassword")
    @ApiOperation("找回密码")
    public R findPassword(@RequestBody UsIsRegParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        Map hp = new HashMap();
        hp.put("mobile_phone", form.getMobilePhone());
        List rs = usUserService.selectByMap(hp);
        //判断手机号码是否注册
        if (rs.isEmpty()) {
            return R.error(Constant.Result.NO_REG_MOBILE.getValue(), Constant.Message.NO_REG_MOBILE.getValue());
        } else if (rs.size() == 1) {
            //如果已经注册，发送短信验证码
            String smsCode = UsSmsUtil.getCode(form.getMobilePhone());//发送短信验证码
            if (!smsCode.equals(Constant.Message.SMS_FAIL.getValue())) {
                UsSmsEntity smsEntity = new UsSmsEntity();
                smsEntity.setId(UsIdUtil.generateId());
                smsEntity.setAppid(form.getAppid());
                smsEntity.setMobile(form.getMobilePhone());
                smsEntity.setCode(smsCode);
                long now = System.currentTimeMillis();
                smsEntity.setCreateDate(new Date(now));
                long expire = now + 5 * 60 * 1000;
                smsEntity.setExpireDate(new Date(expire));
                boolean b = usSmsService.insert(smsEntity);
                if (b) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mobile_phone", form.getMobilePhone());
                    return R.ok(map);
                } else {
                    return R.error(Constant.Message.SMS_FAIL.getValue());
                }
            }
            return R.error(Constant.Message.SMS_FAIL.getValue());
        }
        return R.error(Constant.Message.FAIL.getValue());
    }

    /**
     * 重置密码
     */
    @PostMapping("/api/user/resetPassword")
    @ApiOperation("重置密码")
    public R resetPassword(@RequestBody UsResetpwdParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        //输入的短信验证码是否正确
        Integer smsCode = usSmsService.checkCode(form.getAppid(), form.getMobilePhone(), form.getSmsCode());
        if (smsCode == Constant.Result.SMS_CODE_CORRECT.getValue()) {
            //修改密码
            Map hp = new HashMap();
            hp.put("mobile_phone", form.getMobilePhone());
            List<UsUserEntity> rs = usUserService.selectByMap(hp);
            if (rs.isEmpty()) {
                return R.error(Constant.Result.NO_REG_MOBILE.getValue(), Constant.Message.NO_REG_MOBILE.getValue());
            } else if (rs.size() == 1) {
                UsUserEntity user = rs.get(0);
                user.setPassword(form.getNewPassword());
                user.setAppid(form.getAppid());
                usUserService.updateById(user);
                return R.ok();
            } else {
                return R.error(Constant.Message.FAIL.getValue());
            }
        } else if (smsCode == Constant.Result.SMS_CODE_ERROR.getValue()) {
            return R.error(Constant.Result.SMS_CODE_ERROR.getValue(), "验证码不正确");
        } else if (smsCode == Constant.Result.SMS_CODE_EXPIRE.getValue()) {
            return R.error(Constant.Result.SMS_CODE_EXPIRE.getValue(), "验证码过期");
        } else {
            return R.error(Constant.Result.SMS_CODE_NULL.getValue(), "验证码查询结果为空");
        }
    }

    /**
     * 注册
     */
    @PostMapping("/api/user/register")
    @ApiOperation("注册")
    public R register(@RequestBody UsRegisterParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        //短信验证码是否正确
        int code = usSmsService.checkCode(form.getAppid(), form.getMobilePhone(), form.getSmsCode());
        if (code == Constant.Result.SMS_CODE_CORRECT.getValue()) {
            UsUserEntity us = usUserService.reg(form);
            return R.ok(usUserService.unifyUserDataReturned(us.getId(), cardNumberUtil.getIdCard()));
        } else if (code == Constant.Result.SMS_CODE_ERROR.getValue()) {
            return R.error(Constant.Result.SMS_CODE_ERROR.getValue(), "验证码不正确");
        } else if (code == Constant.Result.SMS_CODE_EXPIRE.getValue()) {
            return R.error(Constant.Result.SMS_CODE_EXPIRE.getValue(), "验证码过期");
        } else {
            return R.error(Constant.Result.SMS_CODE_NULL.getValue(), "验证码查询结果为空");
        }
    }

    /**
     * 登录
     */
    @PostMapping("/api/user/login")
    @ApiOperation("登录")
    public R login(@RequestBody UsLoginParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        return usUserService.signIn(form);
    }

    @PostMapping("/api/user/modifypwd")
    @ApiOperation("修改密码")
    public R modifypwd(@RequestBody UsModifypwdParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();
        if (oldPassword.equals(newPassword)) {
            return R.error("旧密码跟新密码相同");
        }
        String session = form.getSession();
        String userId = sessionUtil.getUserId(session);
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user0 = usUserService.checkUserExits(userId, oldPassword);
        if (user0 == null) {
            return R.error("旧密码错误");
        }
        user.setPassword(newPassword);
        user.setAppid(form.getAppid());
        usUserService.updateById(user);
        return R.ok("修改成功");
    }

    @PostMapping("/api/user/personalInfo")
    @ApiOperation("查询个人信息")
    public R personalInfo(@RequestBody UsSessionParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        return R.ok(usUserService.unifyUserDataReturned(user.getId(), cardNumberUtil.getIdCard()));
    }

    @PostMapping("/api/user/editPersonalInfo")
    @ApiOperation("修改个人信息")
    public R editPersonalInfo(@RequestBody UsUserParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        user = usUserService.updatePersonalInfo(user, form);
        return R.ok(usUserService.unifyUserDataReturned(user.getId(), cardNumberUtil.getIdCard()));
    }

    @PostMapping("/api/user/departList")
    @ApiOperation("查询部门信息列表")
    public R departList(@RequestBody UsSessionParam form) {
        ValidatorUtils.validateEntity(form);
        //验证是否登录
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        List<TSTypeEntity> list = tSTypeService.queryList("dep_list");
        return R.ok(list);
    }

    @PostMapping("/api/user/jobList")
    @ApiOperation("查询职业信息列表")
    public R jobList(@RequestBody UsSessionParam form) {
        ValidatorUtils.validateEntity(form);
        //验证是否登录
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        List<TSTypeEntity> list = tSTypeService.queryList("job_list");
        return R.ok(list);
    }

    @PostMapping("/api/user/realnameCertification")
    @ApiOperation("实名认证")
    public R realnameCertification(@RequestBody UsUserRealCertParam form) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity usUserEntity = usUserService.realnameCert(user, form);
        return R.ok(usUserService.unifyUserDataReturned(usUserEntity.getId(), cardNumberUtil.getIdCard()));
    }

    @PostMapping("/api/user/editPortrait")
    @ApiOperation("修改头像")
    public R editPortrait(@RequestBody UsUserPortraiParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null) {
            return R.error("查询不到此用户");
        }
        UsUserEntity user = usUserService.selectById(userId);
        if (user == null) {
            return R.error("查询不到此用户");
        }
        // 将已修改的图片url对应的id返回前端
        return usUserService.uploadPortrait(user, form);
    }

}