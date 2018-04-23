package io.renren.modules.us.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.TSDepartService;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.UsSmsService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-12 16:26:30
 */
@RestController
@RequestMapping("/api/user")
@Api("基础接口")
public class UsUserController {
    @Autowired
    private UsUserService usUserService;
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private UsSmsService usSmsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:ususer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:ususer:info")
    public R info(@PathVariable("id") String id){
			UsUserEntity usUser = usUserService.selectById(id);

        return R.ok().put("usUser", usUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:ususer:save")
    public R save(@RequestBody UsUserEntity usUser){
			usUserService.insert(usUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:ususer:update")
    public R update(@RequestBody UsUserEntity usUser){
			usUserService.updateById(usUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:ususer:delete")
    public R delete(@RequestBody String[] ids){
			usUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 验证手机号码是否注册
     */
    @PostMapping("checkIsRegister")
    @ApiOperation("验证手机号码是否注册")
    public R checkIsRegister(@RequestBody UsIsRegParam form ){
        //表单校验
        ValidatorUtils.validateEntity(form);
        Map hp = new HashMap();
        hp.put("mobile_phone",form.getMobilePhone());
        List rs = usUserService.selectByMap(hp);
        if (rs.size()>0){
            return R.error(Constant.Result.REG_MOBILE.getValue(),Constant.Message.REG_MOBILE.getValue());
        }else{
            return R.ok();
        }

    }

    /**
     * 注册
     */
    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody UsRegisterParam form ) throws ParseException {
        //表单校验
        ValidatorUtils.validateEntity(form);
        //短信验证码是否正确
/*        Integer code = usSmsService.checkCode(form.getAppid(),form.getMobilePhone(),form.getSmsCode());

        if (code == Constant.Result.SMS_CODE_CORRECT.getValue()){
            UsUserEntity us = usUserService.reg(form);
            Map<String, Object> map = new HashMap<>();
            map.put("mobilePhone", us.getMobilePhone());
            map.put("session",us.getSession());
            return R.ok(map);

        }else if(code == Constant.Result.SMS_CODE_ERROR.getValue()){
            return R.error(Constant.Result.SMS_CODE_ERROR.getValue(),"验证码不正确");
        }else if(code == Constant.Result.SMS_CODE_EXPIRE.getValue()){
            return R.error(Constant.Result.SMS_CODE_EXPIRE.getValue(),"验证码过期");
        }else{
            return R.error(Constant.Result.SMS_CODE_NULL.getValue(),"验证码查询结果为空");
        }*/

        UsUserEntity us = usUserService.reg(form);
        Map<String, Object> map = new HashMap<>();
        map.put("mobilePhone", us.getMobilePhone());
        map.put("session",us.getSession());
        return R.ok(map);

    }


    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody UsLoginParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        return usUserService.signIn(form);
    }

    @PostMapping("modifypwd")
    @ApiOperation("修改密码")
    public R modifypwd(@RequestBody UsModifypwdParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();

        if (oldPassword.equals(newPassword)){
            return R.error("旧密码跟新密码相同");
        }

        String session = form.getSession();

        String userId = UsSessionUtil.getUserid(session);
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user0 = usUserService.checkUserExits(userId, oldPassword);
        if(user0 == null){
            return R.error("旧密码错误");
        }
        user.setPassword(newPassword);
        user.setAppid(form.getAppid());
        usUserService.updateById(user);
        return R.ok("修改成功");
    }

    @PostMapping("personalInfo")
    @ApiOperation("查询个人信息")
    public R personalInfo(@RequestBody UsSessionParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        //获取工作单位/职业名称
        user = this.queryName(user);

        //返回user隐藏部分字段
        UsUserHPram user_ = usUserService.usHiddenProperty(user);
        return R.ok(user_);
    }



    /**
     * 根据工作单位id获取名称等
     * @param
     * @return
     */
    public UsUserEntity queryName(UsUserEntity user) {
        //工作单位
        if (null != user.getUDepartid()  &&  !"".equals(user.getUDepartid())){
            TSDepartEntity tSDepart =  tSDepartService.selectById(user.getUDepartid());
            user.setPersonDepartname(tSDepart.getDepartname());
        }
        //职业
        if (null != user.getUJobid()  &&  !"".equals(user.getUJobid())){
            TSTypeEntity ts = tSTypeService.queryByCode(user.getUJobid(),"job_list");
            user.setPersonJob(ts.getTypename());
        }
        return user;
    }


    @PostMapping("editPersonalInfo")
    @ApiOperation("修改个人信息")
    public R editPersonalInfo(@RequestBody UsUserParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        user = usUserService.updatePersonalInfo(user,form);

        user = this.queryName(user);
        //返回user隐藏部分字段
        UsUserHPram user_ = usUserService.usHiddenProperty(user);
        return R.ok(user_);
    }

    @PostMapping("departList")
    @ApiOperation("查询部门信息列表")
    public R departList(@RequestBody UsSessionParam form){

        ValidatorUtils.validateEntity(form);
        //验证是否登录
        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        List<TSDepartEntity> list = tSDepartService.queryDepartListByPid("297eb468623bd89b01623ce8a17d000f");

        return R.ok(list);
    }


    @PostMapping("jobList")
    @ApiOperation("查询职业信息列表")
    public R jobList(@RequestBody UsSessionParam form){

        ValidatorUtils.validateEntity(form);
        //验证是否登录
        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        List<TSTypeEntity> list = tSTypeService.queryList("job_list");

        return R.ok(list);
    }


    @PostMapping("realnameCertification")
    @ApiOperation("实名认证")
    public R realnameCertification(@RequestBody UsUserRealCertParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        user = usUserService.realnameCert(user,form);

        user = this.queryName(user);
        //返回user隐藏部分字段
        UsUserHPram user_ = usUserService.usHiddenProperty(user);
        return R.ok(user_);
    }




}
