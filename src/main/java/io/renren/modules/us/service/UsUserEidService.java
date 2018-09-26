package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsEidLoginParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.param.UsUserAuthParam;

import java.io.UnsupportedEncodingException;

public interface UsUserEidService extends IService<UsUserEntity> {
    R eidLogin(UsEidLoginParam form) throws InterruptedException, UnsupportedEncodingException;

    R eidAuth(UsSessionParam form) throws InterruptedException, UnsupportedEncodingException;

    boolean updateEidLevel(String id, Integer level);

    R auth(UsUserAuthParam param) throws InterruptedException, UnsupportedEncodingException;
}
