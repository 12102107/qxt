package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author sys
 * @email
 * @date 2018-04-25 14:29:11
 */
@TableName("us_resource")
public class UsResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 资源名
     */
    private String name;
    /**
     * 资源图标
     */
    private String icon;
    /**
     * IOS资源URL
     */
    private String iosUrl;
    /**
     * Android资源URL
     */
    private String androidUrl;
    /**
     * Web资源URL
     */
    private String webCurl;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 资源状态
     */
    private String status;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 获取：id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：资源名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：资源名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：资源图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：资源图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取：IOS资源URL
     */
    public String getIosUrl() {
        return iosUrl;
    }

    /**
     * 设置：IOS资源URL
     */
    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    /**
     * 获取：Android资源URL
     */
    public String getAndroidUrl() {
        return androidUrl;
    }

    /**
     * 设置：Android资源URL
     */
    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    /**
     * 获取：Web资源URL
     */
    public String getWebCurl() {
        return webCurl;
    }

    /**
     * 设置：Web资源URL
     */
    public void setWebCurl(String webCurl) {
        this.webCurl = webCurl;
    }

    /**
     * 获取：资源类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置：资源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：资源状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置：资源状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取：创建时间
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：分类id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 设置：分类id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
