package io.renren.modules.us.entity;


import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 *
 *
 * @author gxp
 * @email
 * @date 2018-07-16
 */
@TableName("us_card_number")
public class UsCardNumber implements Serializable {

    private String id;
    private String uid;
    private String electronicCardNumber; //电子卡号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getElectronicCardNumber() {
        return electronicCardNumber;
    }

    public void setElectronicCardNumber(String electronicCardNumber) {
        this.electronicCardNumber = electronicCardNumber;
    }
}
