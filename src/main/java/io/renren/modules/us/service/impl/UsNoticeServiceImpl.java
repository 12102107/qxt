package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.UsNoticeDao;
import io.renren.modules.us.entity.UsNoticeEntity;
import io.renren.modules.us.service.UsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("usNoticeService")
public class UsNoticeServiceImpl extends ServiceImpl<UsNoticeDao, UsNoticeEntity> implements UsNoticeService {
      @Autowired
      private UsNoticeDao usNoticeDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //查询已发布，按照时间倒序排列（部分字段）
        String noticeType = (String)params.get("noticeType");
        EntityWrapper<UsNoticeEntity> wrapper = new EntityWrapper<>();
        Page<UsNoticeEntity> page = this.selectPage(
                new Query<UsNoticeEntity>(params).getPage(),
                wrapper.setSqlSelect("id","notice_title","notice_type","create_time").eq("notice_status",1)
                        .eq("notice_type",noticeType)
                        .orderBy("create_time", false)   // 时间的倒叙排列
        );

        return new PageUtils(page);
    }

    @Override
    public UsNoticeEntity findByNoticeType(String noticeType) {
        return usNoticeDao.findByNoticeType(noticeType);
    }

}
