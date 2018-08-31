package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.us.dao.UsApiDao;
import io.renren.modules.us.entity.UsApiEntity;
import io.renren.modules.us.service.UsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("usApiService")
public class UsApiServiceImpl extends ServiceImpl<UsApiDao, UsApiEntity> implements UsApiService {

    private UsApiDao apiDao;

    @Override
    public UsApiEntity getWithAppId(String appId, String url) {
        return apiDao.getWithAppId(appId, url);
    }

    @Override
    public UsApiEntity getWithoutAppId(String url) {
        return apiDao.getWithoutAppId(url);
    }

    @Autowired
    public void setApiDao(UsApiDao apiDao) {
        this.apiDao = apiDao;
    }
}
