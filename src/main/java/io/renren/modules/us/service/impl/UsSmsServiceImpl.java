package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.dao.UsSmsDao;
import io.renren.modules.us.entity.UsSmsEntity;
import io.renren.modules.us.param.UsSmsParam;
import io.renren.modules.us.service.UsSmsService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSmsUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Li
 */
@Service("usSmsService")
public class UsSmsServiceImpl extends ServiceImpl<UsSmsDao, UsSmsEntity> implements UsSmsService {

    @Override
    public R getCode(UsSmsParam smsParam) {
        ValidatorUtils.validateEntity(smsParam);
        String code = UsSmsUtil.getCode(smsParam.getMobile());
        if (!code.equals(Constant.Message.SMS_FAIL.getValue())) {
            UsSmsEntity smsEntity = new UsSmsEntity();
            smsEntity.setId(UsIdUtil.generateId());
            smsEntity.setAppid(smsParam.getAppid());
            smsEntity.setMobile(smsParam.getMobile());
            smsEntity.setCode(code);
            long now = System.currentTimeMillis();
            smsEntity.setCreateDate(new Date(now));
            long expire = now + 5 * 60 * 1000;
            smsEntity.setExpireDate(new Date(expire));
            boolean b = this.insert(smsEntity);
            if (b) {
                Map<String, Object> map = new HashMap<>();
                map.put("code", code);
                return R.ok(map);
            } else {
                return R.error(Constant.Message.SMS_FAIL.getValue());
            }
        } else {
            return R.error(Constant.Message.SMS_FAIL.getValue());
        }
    }

    @Override
    public int checkCode(String appid, String mobile, String code) {
        EntityWrapper<UsSmsEntity> wrapper = new EntityWrapper<>();
        wrapper.where("appid={0}", appid)
                .and("mobile={0}", mobile)
                .orderBy("create_date", false)
                .last("limit 1");
        wrapper.setEntity(new UsSmsEntity());
        UsSmsEntity smsEntity = this.selectOne(wrapper);
        if (smsEntity != null) {
            if (!code.equals(smsEntity.getCode())) {
                return Constant.Result.SMS_CODE_ERROR.getValue();
            } else {
                Date now = new Date();
                Date expireDate = smsEntity.getExpireDate();
                return now.before(expireDate) ? Constant.Result.SMS_CODE_CORRECT.getValue() : Constant.Result.SMS_CODE_EXPIRE.getValue();
            }
        } else {
            return Constant.Result.SMS_CODE_NULL.getValue();
        }
    }


}
