package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.service.TSTypeService;
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
 * @date 2018-04-18 15:30:38
 */
@RestController
@RequestMapping("us/tstype")
public class TSTypeController {
    @Autowired
    private TSTypeService tSTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:tstype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tSTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:tstype:info")
    public R info(@PathVariable("id") String id){
			TSTypeEntity tSType = tSTypeService.selectById(id);

        return R.ok().put("tSType", tSType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:tstype:save")
    public R save(@RequestBody TSTypeEntity tSType){
			tSTypeService.insert(tSType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:tstype:update")
    public R update(@RequestBody TSTypeEntity tSType){
			tSTypeService.updateById(tSType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:tstype:delete")
    public R delete(@RequestBody String[] ids){
			tSTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }




}
