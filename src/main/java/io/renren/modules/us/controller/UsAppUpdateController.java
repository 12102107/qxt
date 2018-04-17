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

import io.renren.modules.us.entity.UsAppUpdateEntity;
import io.renren.modules.us.service.UsAppUpdateService;
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
@RequestMapping("us/usappupdate")
public class UsAppUpdateController {
    @Autowired
    private UsAppUpdateService usAppUpdateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usappupdate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usAppUpdateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:usappupdate:info")
    public R info(@PathVariable("id") String id){
			UsAppUpdateEntity usAppUpdate = usAppUpdateService.selectById(id);

        return R.ok().put("usAppUpdate", usAppUpdate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:usappupdate:save")
    public R save(@RequestBody UsAppUpdateEntity usAppUpdate){
			usAppUpdateService.insert(usAppUpdate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:usappupdate:update")
    public R update(@RequestBody UsAppUpdateEntity usAppUpdate){
			usAppUpdateService.updateById(usAppUpdate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:usappupdate:delete")
    public R delete(@RequestBody String[] ids){
			usAppUpdateService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
