package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsAdvertisingEntity;
import io.renren.modules.us.entity.UsNoticeEntity;
import io.renren.modules.us.param.UsAdvertisingDetailPram;
import io.renren.modules.us.param.UsAdvertisingPram;
import io.renren.modules.us.param.UsNoticeDetailPram;
import io.renren.modules.us.param.UsNoticePram;
import io.renren.modules.us.service.UsAdvertisingService;
import io.renren.modules.us.service.UsNoticeService;
import io.renren.modules.us.service.UsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
@RestController
@RequestMapping("/api/usadvertising")
@Api("广告接口")
public class UsAdvertisingController {
    @Autowired
    private UsAdvertisingService usAdvertisingService;
    @Autowired
    private UsUserService usUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usadvertising:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usAdvertisingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:advertising:info")
    public R info(@PathVariable("id") String id){
			UsAdvertisingEntity usadvertising = usAdvertisingService.selectById(id);

        return R.ok().put("advertising", usadvertising);
    }

   
    @PostMapping("advertisingList")
    @ApiOperation("广告列表")
    public R noticeList(@RequestBody UsAdvertisingPram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();

        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        PageUtils page = usAdvertisingService.queryPage(params);

        return R.ok(page);

    }

    @PostMapping("advertisingDetail")
    @ApiOperation("广告明细")
    public R advertisingDetail(@RequestBody UsAdvertisingDetailPram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UsAdvertisingEntity tl = new UsAdvertisingEntity();

        if (form.getAdvertisingId()!=null){
            tl = usAdvertisingService.selectById(form.getAdvertisingId());
        }

        return R.ok(tl);

    }

}
