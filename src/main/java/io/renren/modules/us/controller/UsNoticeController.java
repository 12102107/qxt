package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsNoticeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsNoticeDetailPram;
import io.renren.modules.us.param.UsNoticePram;
import io.renren.modules.us.service.UsNoticeService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
@RestController
@RequestMapping("/api/usnotice")
@Api("消息公告接口")
public class UsNoticeController {
    @Autowired
    private UsNoticeService usNoticeService;
    @Autowired
    private UsUserService usUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:usnotice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usNoticeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:usnotice:info")
    public R info(@PathVariable("id") String id){
			UsNoticeEntity usNotice = usNoticeService.selectById(id);

        return R.ok().put("usNotice", usNotice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:usnotice:save")
    public R save(@RequestBody UsNoticeEntity usNotice){
			usNoticeService.insert(usNotice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:usnotice:update")
    public R update(@RequestBody UsNoticeEntity usNotice){
			usNoticeService.updateById(usNotice);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:usnotice:delete")
    public R delete(@RequestBody String[] ids){
			usNoticeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("noticeList")
    @ApiOperation("消息/公告列表")
    public R noticeList(@RequestBody UsNoticePram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("noticeType",form.getNoticeType());

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();

        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        PageUtils page = usNoticeService.queryPage(params);

        return R.ok(page);

    }

    @PostMapping("noticeDetail")
    @ApiOperation("消息公告明细")
    public R noticeDetail(@RequestBody UsNoticeDetailPram form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = UsSessionUtil.getUserid(form.getSession());
        if (userId == null){
            return R.error("session格式不正确");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("session格式不正确");
        }

        UsNoticeEntity tl = new UsNoticeEntity();

        if (form.getNoticeId()!=null){
            tl = usNoticeService.selectById(form.getNoticeId());
        }

        return R.ok(tl);

    }

}
