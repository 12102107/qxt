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

import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-12 16:26:30
 */
@RestController
@RequestMapping("us/ususer")
public class UsUserController {
    @Autowired
    private UsUserService usUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:ususer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:ususer:info")
    public R info(@PathVariable("id") String id){
			UsUserEntity usUser = usUserService.selectById(id);

        return R.ok().put("usUser", usUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:ususer:save")
    public R save(@RequestBody UsUserEntity usUser){
			usUserService.insert(usUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:ususer:update")
    public R update(@RequestBody UsUserEntity usUser){
			usUserService.updateById(usUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:ususer:delete")
    public R delete(@RequestBody String[] ids){
			usUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
