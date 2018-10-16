package io.renren.modules.us.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsAdvertisingEntity;
import io.renren.modules.us.entity.UsHealthyEntity;
import io.renren.modules.us.param.UsAdvertisingDetailPram;
import io.renren.modules.us.param.UsAdvertisingPram;
import io.renren.modules.us.param.UsHealthyDetailPram;
import io.renren.modules.us.param.UsHealthyPram;
import io.renren.modules.us.service.UsHealthyService;
import io.renren.modules.us.service.UsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
@RestController
@RequestMapping("/api/healthy")
@Api("健康宣教接口")
public class UsHealthyController {
    @Autowired
    private UsHealthyService usHealthyService;
    @Autowired
    private UsUserService usUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:healthy:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usHealthyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:healthy:info")
    public R info(@PathVariable("id") String id){
		UsHealthyEntity healthy = usHealthyService.selectById(id);

        return R.ok().put("healthy", healthy);
    }

   
    @PostMapping("healthyList")
    @ApiOperation("健康宣教列表")
    public R noticeList(@RequestBody UsHealthyPram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();

        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        PageUtils page = usHealthyService.queryPage(params);

        return R.ok(page);

    }

    @PostMapping("healthyDetail")
    @ApiOperation("健康宣教明细")
    public R healthyDetail(@RequestBody UsHealthyDetailPram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UsHealthyEntity tl = new UsHealthyEntity();

        if (form.getHealthyId()!=null){
            tl = usHealthyService.selectById(form.getHealthyId());
        }

        return R.ok(tl);

    }

}
