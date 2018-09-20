package io.renren.modules.us.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsHistoryEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsPushDetailParam;
import io.renren.modules.us.param.UsPushListParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsHistoryService;
import io.renren.modules.us.service.UsPushService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sys
 */
@RestController
@RequestMapping("/api/history")
@Api("公交历史信息接口")
public class UsHistoryController {

    @Autowired
    private UsHistoryService ushistoryService;
    @Autowired
    private UsUserService userService;
    @Autowired
    private UsSessionUtil sessionUtil;

    @PostMapping("/queryHistory")
    @ApiOperation("历史记录查询展示接口")
    public R list(@RequestBody UsPushListParam form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        UsUserEntity user = userService.selectById(userId);

        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();
        params.put("userId",userId);
        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        PageUtils page = ushistoryService.list(params);
        return R.ok(page);
    }

    @PostMapping("/addHistory")
    @ApiOperation("历史信息添加接口")
    public R addHistory(@RequestBody UsHistoryEntity historyEntity,@RequestBody UsSessionParam form) {

        String userId = sessionUtil.getUserId(form.getSession());
        historyEntity.setId((UsIdUtil.generateId()));
        historyEntity.setUserId(userId);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        historyEntity.setCreateDate(dateString);

        List<Map> list = ushistoryService.isExistOrgin(userId,historyEntity.getOrigin(),historyEntity.getDestination()/*,historyEntity.getFindMethod()*/);
        if(list.isEmpty()){
            ushistoryService.insert(historyEntity);

        }else{
            String id = list.get(0).get("id").toString();
            ushistoryService.updateDate(id,dateString/*,historyEntity.getFindMethod()*/);//修改时间

        }
        return R.ok();

    }

    @PostMapping("/delHistory")
    @ApiOperation("清空历史信息接口")
    public R delHistory(@RequestBody UsSessionParam form/*,@RequestBody UsHistoryEntity historyEntity*/) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        UsUserEntity user = userService.selectById(userId);
        ushistoryService.delete(userId/*,historyEntity.getFindMethod()*/);

        return R.ok();
    }

}
