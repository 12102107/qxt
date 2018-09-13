package io.renren.modules.us.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class UsCardNumberUtil {

    @Value("${us.card.idCard}")
    private String idCard;

    @Value("${us.card.idCardPrefix}")
    private String idCardPrefix;

    @Value("${us.card.trafficCard}")
    private String trafficCard;

    @Value("${us.card.trafficCardPrefix}")
    private String trafficCardPrefix;

    private synchronized String generateNumber() {
        Calendar calendar = Calendar.getInstance();
        long cardNumbers = calendar.getTime().getTime();
        String s = String.valueOf(cardNumbers);
        return s.substring(1);
    }

    public String generateIdCardNumber() {
        String str = this.generateNumber();
        return idCardPrefix + str;
    }

    public String generateTrafficCardNumber() {
        String str = this.generateNumber();
        return trafficCardPrefix + str;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getTrafficCard() {
        return trafficCard;
    }
}
