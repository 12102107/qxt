package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsRegionsEntity;
import io.renren.modules.us.service.UsRegionsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-19 14:43:50
 */
@RestController
@RequestMapping("us/usregions")
public class UsRegionsController {
    @Autowired
    private UsRegionsService usRegionsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usregions:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usRegionsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{regionId}")
    @RequiresPermissions("us:usregions:info")
    public R info(@PathVariable("regionId") Integer regionId){
			UsRegionsEntity usRegions = usRegionsService.selectById(regionId);

        return R.ok().put("usRegions", usRegions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:usregions:save")
    public R save(@RequestBody UsRegionsEntity usRegions){
			usRegionsService.insert(usRegions);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:usregions:update")
    public R update(@RequestBody UsRegionsEntity usRegions){
			usRegionsService.updateById(usRegions);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:usregions:delete")
    public R delete(@RequestBody Integer[] regionIds){
			usRegionsService.deleteBatchIds(Arrays.asList(regionIds));

        return R.ok();
    }

}
