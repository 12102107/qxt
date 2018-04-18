package io.renren.modules.us.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;


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
    private UsUserPlantParamService usUserPlantParamService;
    private IService iService;

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
            return R.error("此电话号码被注册过，请登录或尝试其他号码");
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
        //保存用户信息
        UsUserEntity user = new UsUserEntity();
        user.setMobilePhone(form.getMobilePhone());
        user.setPassword(form.getPassword());
        user.setSmsCode(form.getSmsCode());
        user.setId(UsIdUtil.generateId());
        user.setCreateDate(new Date());
        usUserService.insert(user);
        //保存设备信息
        UsUserPlantParamEntity userPlant = new UsUserPlantParamEntity();
        userPlant.setUserId(user.getId());
        userPlant.setId(UsIdUtil.generateId());
        userPlant.setUnitType(form.getUnitType());
        userPlant.setEquipmentManufacturer(form.getEquipmentManufacturer());
        userPlant.setScreenResolution(form.getScreenResolution());
        userPlant.setDpi(form.getDpi());
        userPlant.setSystemName(form.getSystemName());
        userPlant.setSystemVersion(form.getSystemVersion());
        userPlant.setNetworkType(form.getNetworkType());
        userPlant.setCreateDate(new Date());
        usUserPlantParamService.insert(userPlant);

        Map<String, Object> map = new HashMap<>();
        map.put("mobilePhone", user.getMobilePhone());
        return R.ok(map);
    }


    /**
     * 修改密码
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

        return R.ok(user);
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

        return R.ok(user);
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

        EntityWrapper<TSDepartEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSDepartEntity());
        wrapper.where("org_code={0}", "A04");
        List<TSDepartEntity> list = iService.selectList(wrapper);

        return R.ok(list);
    }

}
