package io.renren.modules.us.util;

import io.renren.modules.us.entity.UsUserEntity;

import java.util.HashMap;
import java.util.Map;

public class UsUserUtil {

    private UsUserUtil() {
    }

    /**
     * 统一User对象返回值
     */
    public static Map<String, Object> trim(UsUserEntity user) {
        Map<String, Object> map = new HashMap<>();
        map.put("cardNumber", user.getCardNumber() == null ? "" : user.getCardNumber());
        map.put("mobilePhone", user.getMobilePhone() == null ? "" : user.getMobilePhone());
        map.put("realname", user.getRealname() == null ? "" : user.getRealname());
        map.put("citizenNo", user.getCitizenNo() == null ? "" : user.getCitizenNo());
        map.put("sex", user.getSex() == null ? "" : user.getSex());
        map.put("portrait", user.getPortrait() == null ? "" : user.getPortrait());
        map.put("session", user.getSession());
        map.put("eidLevel", user.getEidLevel() == null ? "" : user.getEidLevel());
        map.put("clientId", user.getClientId() == null ? "" : user.getClientId());
        map.put("email", user.getEmail() == null ? "" : user.getEmail());
        map.put("address", user.getAddress() == null ? "" : user.getAddress());
        map.put("uJobid", user.getuJobid() == null ? "" : user.getuJobid());
        map.put("personJob", user.getPersonJob() == null ? "" : user.getPersonJob());
        map.put("uDepartid", user.getuDepartid() == null ? "" : user.getuDepartid());
        map.put("personDepartname", user.getPersonDepartname() == null ? "" : user.getPersonDepartname());
        return map;
    }

}
