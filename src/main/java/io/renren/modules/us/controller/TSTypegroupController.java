package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.TSTypegroupEntity;
import io.renren.modules.us.service.TSTypegroupService;
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
 * @date 2018-04-20 09:55:04
 */
@RestController
@RequestMapping("us/tstypegroup")
public class TSTypegroupController {
    @Autowired
    private TSTypegroupService tSTypegroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:tstypegroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tSTypegroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:tstypegroup:info")
    public R info(@PathVariable("id") String id){
			TSTypegroupEntity tSTypegroup = tSTypegroupService.selectById(id);

        return R.ok().put("tSTypegroup", tSTypegroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:tstypegroup:save")
    public R save(@RequestBody TSTypegroupEntity tSTypegroup){
			tSTypegroupService.insert(tSTypegroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:tstypegroup:update")
    public R update(@RequestBody TSTypegroupEntity tSTypegroup){
			tSTypegroupService.updateById(tSTypegroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:tstypegroup:delete")
    public R delete(@RequestBody String[] ids){
			tSTypegroupService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
