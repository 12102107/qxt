package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.us.dao.UsElectronicCardNumberDao;
import io.renren.modules.us.entity.UsCardNumber;
import io.renren.modules.us.service.UsElectronicCardNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * 实名认证通过后返回电子卡号
 *
 * @author gaoxipeng
 * @date 2018-07-16
 */

@Service("UsElectronicCardNumberService")
public class UsElectronicCardNumberServiceImpl extends ServiceImpl<UsElectronicCardNumberDao, UsCardNumber> implements UsElectronicCardNumberService {

    @Autowired
    private  UsElectronicCardNumberService usElectronicCardNumberService;

    @Value("${us.cooperation.cardNumber}")
    private String cardNumber;

    @Override
    public String electronicCardNumber(String uid) {

        StringBuffer sb=new StringBuffer("622623");
        //生成12位整数
        String substring = getCardNumbers();
        sb.append(substring);
        String electronicCardNumber = sb.toString();
        //uuid生成主见
        String s1 = UUID.randomUUID().toString();
        String id = s1.replaceAll("\\-", "");
        UsCardNumber card=new UsCardNumber();
        card.setId(id);
        card.setUid(uid);
        card.setElectronicCardNumber(electronicCardNumber);
        usElectronicCardNumberService.insert(card);


        return electronicCardNumber;
    }
    
    public synchronized  String getCardNumbers (){
        Calendar calendar = Calendar.getInstance();
        long cardNumber = calendar.getTime().getTime();
//        long card=cardNumber+1L;
        String s = String.valueOf(cardNumber);
        //截取掉前一位
        String substring = s.substring(1);

        return substring;
    }
}
