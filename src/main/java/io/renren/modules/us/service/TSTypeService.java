package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.TSTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-18 15:30:38
 */
public interface TSTypeService extends IService<TSTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查询具体一条type值
    TSTypeEntity queryByCode(String typecode, String typegroupcode);

    //查询某个数据字典列表
    List<TSTypeEntity> queryList(String typegroupcode);
  
}

