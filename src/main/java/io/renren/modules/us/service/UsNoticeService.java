package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsNoticeEntity;
import io.renren.modules.us.param.UsCompanyProfile;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
public interface UsNoticeService extends IService<UsNoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查询公司简介
    UsNoticeEntity findByNoticeType(UsCompanyProfile companyProfile);
}

