package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.us.dao.UsAppApiDao;
import io.renren.modules.us.entity.UsAppApiEntity;
import io.renren.modules.us.service.UsAppApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Li
 */
@Service("usAppApiService")
public class UsAppApiServiceImpl extends ServiceImpl<UsAppApiDao, UsAppApiEntity> implements UsAppApiService {

    private UsAppApiDao appApiDao;

    @Override
    public Integer countId(String appid, String url) {
        return appApiDao.countId(appid, url);
    }

    @Autowired
    public void setAppApiDao(UsAppApiDao appApiDao) {
        this.appApiDao = appApiDao;
    }
}
