package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.TMayorLetterEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsSendLetterParam;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-18 16:06:20
 */
public interface TMayorLetterService extends IService<TMayorLetterEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageOther(Map<String, Object> params);

    TMayorLetterEntity sendLetter(UsUserEntity user, UsSendLetterParam form);

    TMayorLetterEntity queryDetailByLetterCode(String letterCode);

}

