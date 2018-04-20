package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.TSDepartEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-18 14:21:15
 */
public interface TSDepartService extends IService<TSDepartEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //根据父id查询下一级子部门
    List<TSDepartEntity> queryDepartListByPid(String pid);

    //根据orgcode查询部门列表
    List<TSDepartEntity> queryDepartListByOrgcode(String orgcode);

}

