package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sys
 * @email
 * @date 2018-08-29 13:09:39
 */
@TableName("us_card")
public class UsCardEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * appid
     */
    private String appid;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 卡名称
     */
    private String cardName;
    /**
     * 卡的logo图地址
     */
    private String cardLogo;
    /**
     * 卡类型 1 政务 2生活 0 身份证
     */
    private String cardType;
    /**
     * 启用状态 1启用 0未启用
     */
    private String status;
    /**
     * 顺序
     */
    private Integer cardOrder;
    /**
     * 卡别名
     */
    private String cardAlias;
    /**
     * 卡小背景图
     */
    private String cardBackSimg;
    /**
     * 卡大背景图
     */
    private String cardBackBimg;
    /**
     * 二维码地址
     */
    private String qrUrl;
    /**
     * 卡号查询URL
     */
    private String cardNumberUrl;

    /**
     * 获取：主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置：appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：更新日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置：更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取：卡名称
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * 设置：卡名称
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * 获取：卡的logo图地址
     */
    public String getCardLogo() {
        return cardLogo;
    }

    /**
     * 设置：卡的logo图地址
     */
    public void setCardLogo(String cardLogo) {
        this.cardLogo = cardLogo;
    }

    /**
     * 获取：卡类型 1 政务 2生活 0 身份证
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * 设置：卡类型 1 政务 2生活 0 身份证
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取：启用状态 1启用 0未启用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置：启用状态 1启用 0未启用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取：顺序
     */
    public Integer getCardOrder() {
        return cardOrder;
    }

    /**
     * 设置：顺序
     */
    public void setCardOrder(Integer cardOrder) {
        this.cardOrder = cardOrder;
    }

    /**
     * 获取：卡别名
     */
    public String getCardAlias() {
        return cardAlias;
    }

    /**
     * 设置：卡别名
     */
    public void setCardAlias(String cardAlias) {
        this.cardAlias = cardAlias;
    }

    /**
     * 获取：卡小背景图
     */
    public String getCardBackSimg() {
        return cardBackSimg;
    }

    /**
     * 设置：卡小背景图
     */
    public void setCardBackSimg(String cardBackSimg) {
        this.cardBackSimg = cardBackSimg;
    }

    /**
     * 获取：卡大背景图
     */
    public String getCardBackBimg() {
        return cardBackBimg;
    }

    /**
     * 设置：卡大背景图
     */
    public void setCardBackBimg(String cardBackBimg) {
        this.cardBackBimg = cardBackBimg;
    }

    /**
     * 获取：二维码地址
     */
    public String getQrUrl() {
        return qrUrl;
    }

    /**
     * 设置：二维码地址
     */
    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getCardNumberUrl() {
        return cardNumberUrl;
    }

    public void setCardNumberUrl(String cardNumberUrl) {
        this.cardNumberUrl = cardNumberUrl;
    }

    @Override
    public String toString() {
        return "UsCardEntity{" +
                "id='" + id + '\'' +
                ", appid='" + appid + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", cardName='" + cardName + '\'' +
                ", cardLogo='" + cardLogo + '\'' +
                ", cardType='" + cardType + '\'' +
                ", status='" + status + '\'' +
                ", cardOrder=" + cardOrder +
                ", cardAlias='" + cardAlias + '\'' +
                ", cardBackSimg='" + cardBackSimg + '\'' +
                ", cardBackBimg='" + cardBackBimg + '\'' +
                ", qrUrl='" + qrUrl + '\'' +
                '}';
    }
}
