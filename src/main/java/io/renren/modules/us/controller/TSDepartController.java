package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.service.TSDepartService;
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
 * @date 2018-04-18 14:21:15
 */
@RestController
@RequestMapping("us/tsdepart")
public class TSDepartController {
    @Autowired
    private TSDepartService tSDepartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:tsdepart:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tSDepartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:tsdepart:info")
    public R info(@PathVariable("id") String id){
			TSDepartEntity tSDepart = tSDepartService.selectById(id);

        return R.ok().put("tSDepart", tSDepart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:tsdepart:save")
    public R save(@RequestBody TSDepartEntity tSDepart){
			tSDepartService.insert(tSDepart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:tsdepart:update")
    public R update(@RequestBody TSDepartEntity tSDepart){
			tSDepartService.updateById(tSDepart);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:tsdepart:delete")
    public R delete(@RequestBody String[] ids){
			tSDepartService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
