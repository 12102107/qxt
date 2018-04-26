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

import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.service.UsResourceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-25 14:29:11
 */
@RestController
@RequestMapping("us/usresource")
public class UsResourceController {
    @Autowired
    private UsResourceService usResourceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usresource:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usResourceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:usresource:info")
    public R info(@PathVariable("id") String id){
			UsResourceEntity usResource = usResourceService.selectById(id);

        return R.ok().put("usResource", usResource);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:usresource:save")
    public R save(@RequestBody UsResourceEntity usResource){
			usResourceService.insert(usResource);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:usresource:update")
    public R update(@RequestBody UsResourceEntity usResource){
			usResourceService.updateById(usResource);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:usresource:delete")
    public R delete(@RequestBody String[] ids){
			usResourceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
