package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.us.entity.UsApiEntity;

/**
 * @author sys
 */
public interface UsApiService extends IService<UsApiEntity> {
    UsApiEntity getWithAppId(String appId, String url);

    UsApiEntity getWithoutAppId(String url);
}