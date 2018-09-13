package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.us.dao.UsApiDao;
import io.renren.modules.us.entity.UsApiEntity;
import io.renren.modules.us.service.UsApiService;
import org.springframework.stereotype.Service;


@Service("usApiService")
public class UsApiServiceImpl extends ServiceImpl<UsApiDao, UsApiEntity> implements UsApiService {

    @Override
    public UsApiEntity getWithAppId(String appId, String url) {
        return this.baseMapper.getWithAppId(appId, url);
    }

    @Override
    public UsApiEntity getWithoutAppId(String url) {
        return this.baseMapper.getWithoutAppId(url);
    }

}
