package io.renren.modules.us.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.dao.UsHistoryDao;
import io.renren.modules.us.entity.UsHistoryEntity;
import io.renren.modules.us.entity.UsPushEntity;
import io.renren.modules.us.param.UsPushDetailParam;
import io.renren.modules.us.service.UsHistoryService;
import io.renren.modules.us.service.UsPushService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ushistoryService")
public class UsHistoryServiceImpl extends ServiceImpl<UsHistoryDao, UsHistoryEntity> implements UsHistoryService {

    @Autowired
    private UsUserService userService;
    @Autowired
    private UsHistoryService historyService;
    @Autowired
    private UsSessionUtil sessionUtil;
    @Autowired
    private UsHistoryDao usHistoryDao;


    @Override
    public PageUtils list(Map<String, Object> param) {
        EntityWrapper<UsHistoryEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("id","type","strategy","city","origin","origin_amap_latitude as originAmapLatitude",
                "origin_amap_longitude as originAmapLongitude","destination",
                "destination_amap_latitude as destinationAmapLatitude",
                "destination_amap_longitude as destinationAmapLongitude","create_date as createDate")
                .where("user_id = {0}", param.get("userId"))
                .and("is_deleted = {0}","0");

        /*if(null!= param.get("findMethod")&&!"".equals(param.get("findMethod"))){
            w1.and("find_method = {0}",param.get("findMethod"));
        }*/
        w1.orderBy("create_date", false);

        Page<UsHistoryEntity> page = this.selectPage(
                new Query<UsHistoryEntity>(param).getPage(),w1);
                 // 时间的倒叙排列

        return new PageUtils(page);

    }
    /**
     * 删除
     * @param userId
     * @return
     */

    @Override
    public void delete(String userId/*,String findMethod*/) {

        usHistoryDao.deleteHistory(userId/*,findMethod*/);
    }
    /**
     * 判断是否存在历史记录
     * @param userId
     * @return
     */
    @Override
    public List<Map> isExistOrgin(String userId, String origin, String destination/*,String findMethod*/) {
        return usHistoryDao.isExistOrgin(userId,origin,destination/*,findMethod*/);
    }
    /**
     * 当用户已经有的历史记录，修改当前时间
     * @param id,dateString
     * @return
     */
    @Override
    public void updateDate(String id, String dateString/*,String findMethod*/) {
        usHistoryDao.updateDate(id,dateString/*,findMethod*/);
    }


}
