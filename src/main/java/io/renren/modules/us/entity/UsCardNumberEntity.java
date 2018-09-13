package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author sys
 * @date 2018-08-29 13:09:39
 */
@TableName("us_card_number")
public class UsCardNumberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 卡号
     */
    private String electronicCardNumber;
    /**
     * 卡ID
     */
    private String usCardId;
    /**
     * 卡余额
     */
    private Double balance;

    /**
     * 获取：主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：用户ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置：用户ID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取：卡号
     */
    public String getElectronicCardNumber() {
        return electronicCardNumber;
    }

    /**
     * 设置：卡号
     */
    public void setElectronicCardNumber(String electronicCardNumber) {
        this.electronicCardNumber = electronicCardNumber;
    }

    /**
     * 获取：卡ID
     */
    public String getUsCardId() {
        return usCardId;
    }

    /**
     * 设置：卡ID
     */
    public void setUsCardId(String usCardId) {
        this.usCardId = usCardId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UsCardNumberEntity{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", electronicCardNumber='" + electronicCardNumber + '\'' +
                ", usCardId='" + usCardId + '\'' +
                '}';
    }
}
