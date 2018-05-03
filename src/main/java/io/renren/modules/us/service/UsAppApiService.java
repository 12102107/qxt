package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.us.entity.UsAppApiEntity;

/**
 * @author sys
 * @date 2018-05-03 10:30:53
 */
public interface UsAppApiService extends IService<UsAppApiEntity> {

    Integer countId(String appid, String url);

}