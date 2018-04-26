package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.TSCategoryEntity;
import io.renren.modules.us.param.UsChildCategoryParam;
import io.renren.modules.us.param.UsPageParam;

/**
 * 分类接口
 *
 * @author sys
 * @date 2018-04-25 14:29:10
 */
public interface TSCategoryService extends IService<TSCategoryEntity> {

    R list(UsPageParam pageParam);

    R childList(UsChildCategoryParam childCategoryParam);
}

