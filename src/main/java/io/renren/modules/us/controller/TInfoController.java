package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.TInfoEntity;
import io.renren.modules.us.service.TInfoService;
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
 * @date 2018-04-18 16:06:54
 */
@RestController
@RequestMapping("us/tinfo")
public class TInfoController {
    @Autowired
    private TInfoService tInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:tinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:tinfo:info")
    public R info(@PathVariable("id") String id){
			TInfoEntity tInfo = tInfoService.selectById(id);

        return R.ok().put("tInfo", tInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:tinfo:save")
    public R save(@RequestBody TInfoEntity tInfo){
			tInfoService.insert(tInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:tinfo:update")
    public R update(@RequestBody TInfoEntity tInfo){
			tInfoService.updateById(tInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:tinfo:delete")
    public R delete(@RequestBody String[] ids){
			tInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
