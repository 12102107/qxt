package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsRegionsProEntity;
import io.renren.modules.us.service.UsRegionsProService;
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
@RequestMapping("us/usregionspro")
public class UsRegionsProController {
    @Autowired
    private UsRegionsProService usRegionsProService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usregionspro:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usRegionsProService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{regionId}")
    @RequiresPermissions("us:usregionspro:info")
    public R info(@PathVariable("regionId") Integer regionId){
			UsRegionsProEntity usRegionsPro = usRegionsProService.selectById(regionId);

        return R.ok().put("usRegionsPro", usRegionsPro);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:usregionspro:save")
    public R save(@RequestBody UsRegionsProEntity usRegionsPro){
			usRegionsProService.insert(usRegionsPro);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:usregionspro:update")
    public R update(@RequestBody UsRegionsProEntity usRegionsPro){
			usRegionsProService.updateById(usRegionsPro);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:usregionspro:delete")
    public R delete(@RequestBody Integer[] regionIds){
			usRegionsProService.deleteBatchIds(Arrays.asList(regionIds));

        return R.ok();
    }


}
