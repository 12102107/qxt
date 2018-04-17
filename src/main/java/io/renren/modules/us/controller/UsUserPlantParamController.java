package io.renren.modules.us.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-12 16:21:17
 */
@RestController
@RequestMapping("us/ususerplantparam")
public class UsUserPlantParamController {
    @Autowired
    private UsUserPlantParamService usUserPlantParamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:ususerplantparam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usUserPlantParamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:ususerplantparam:info")
    public R info(@PathVariable("id") String id){
			UsUserPlantParamEntity usUserPlantParam = usUserPlantParamService.selectById(id);

        return R.ok().put("usUserPlantParam", usUserPlantParam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:ususerplantparam:save")
    public R save(@RequestBody UsUserPlantParamEntity usUserPlantParam){
			usUserPlantParamService.insert(usUserPlantParam);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:ususerplantparam:update")
    public R update(@RequestBody UsUserPlantParamEntity usUserPlantParam){
			usUserPlantParamService.updateById(usUserPlantParam);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:ususerplantparam:delete")
    public R delete(@RequestBody String[] ids){
			usUserPlantParamService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
