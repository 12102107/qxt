package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsChildCategoryParam;
import io.renren.modules.us.param.UsPageParam;
import io.renren.modules.us.service.TSCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 分类接口
 *
 * @author sys
 * @date 2018-04-25 14:29:10
 */
@RestController
@RequestMapping("/api/category")
@Api("分类接口")
public class TSCategoryController {

    private TSCategoryService tSCategoryService;

    /**
     * 分类接口
     */
    @PostMapping("list")
    @ApiOperation("分类接口")
    public R list(@RequestBody UsPageParam pageParam) {
        return tSCategoryService.list(pageParam);
    }

    /**
     * 子类接口
     */
    @PostMapping("childList")
    @ApiOperation("子类接口")
    public R childList(@RequestBody UsChildCategoryParam childCategoryParam) {
        return tSCategoryService.childList(childCategoryParam);
    }

    @Autowired
    public void settSCategoryService(TSCategoryService tSCategoryService) {
        this.tSCategoryService = tSCategoryService;
    }
}
