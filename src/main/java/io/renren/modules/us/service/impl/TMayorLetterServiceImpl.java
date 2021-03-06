package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.TMayorLetterDao;
import io.renren.modules.us.entity.TMayorLetterEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsSendLetterParam;
import io.renren.modules.us.service.TMayorLetterService;
import io.renren.modules.us.util.UsIdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("tMayorLetterService")
public class TMayorLetterServiceImpl extends ServiceImpl<TMayorLetterDao, TMayorLetterEntity> implements TMayorLetterService {

    public static final String INITIALIZE_SEND_STATUS = "1";//发信后初始状态
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String senderId = (String)params.get("senderId");
        Page<TMayorLetterEntity> page = this.selectPage(
                new Query<TMayorLetterEntity>(params).getPage(),
                new EntityWrapper<TMayorLetterEntity>().eq(StringUtils.isNotBlank(senderId),"sender_id", senderId)
                        .orderBy("create_date",false)
        );

        return new PageUtils(page);

    }

    @Override
    public PageUtils queryPageOther(Map<String, Object> params) {
        String letterCode = (String)params.get("letterCode");
        String personIdCard = (String)params.get("personIdCard");

        Page<TMayorLetterEntity> page = this.selectPage(
                new Query<TMayorLetterEntity>(params).getPage(),
                new EntityWrapper<TMayorLetterEntity>().like(StringUtils.isNotBlank(personIdCard),"person_id_card", personIdCard)
                        .and(StringUtils.isNotBlank(letterCode),"letter_code={0}",letterCode)
                        .orderBy("create_date",false)

        );

        return new PageUtils(page);

    }



    /**
     * 市长信箱发信
     * @param user
     * @param form
     * @return
     */
    public synchronized TMayorLetterEntity sendLetter(UsUserEntity user, UsSendLetterParam form){


        TMayorLetterEntity tmay = new TMayorLetterEntity();

        tmay.setCreateDate(new Date());
        tmay.setCreateBy(user.getMobilePhone());//手机号码
        tmay.setCreateName(user.getRealname());
        tmay.setSenderId(user.getId());//创建人id

        tmay.setBpmStatus(INITIALIZE_SEND_STATUS);//已发送初始状态

        tmay.setPersonName(form.getPersonName());
        tmay.setPersonSex(form.getPersonSex());

        tmay.setuJobid(form.getuJobid());
        tmay.setuDepartid(form.getuDepartid());

        tmay.setPersonIdCard(form.getPersonIdCard());
        tmay.setPersonMobile(form.getPersonMobile());
        tmay.setPersonEmail(form.getPersonEmail());

        tmay.setQuestionProvince(form.getQuestionProvince());
        tmay.setQuestionCity(form.getQuestionCity());
        tmay.setQuestionTitle(form.getQuestionTitle());
        tmay.setQuestionContent(form.getQuestionContent());
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        tmay.setLetterCode(simpledateformat.format(new Date()));
        tmay.setAppid(form.getAppid());
        tmay.setId(UsIdUtil.generateId());

        tmay.setDeleteFlag("0");//初始值
        this.insert(tmay);
        return tmay;
    }

    public TMayorLetterEntity queryDetailByLetterCode(String letterCode) {

        EntityWrapper<TMayorLetterEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TMayorLetterEntity());
        wrapper.where("letter_code={0}", letterCode);
        List<TMayorLetterEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        }

        return null;

    }

}
