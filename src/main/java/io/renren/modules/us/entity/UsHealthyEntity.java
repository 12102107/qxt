package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author fss
 * @email
 * @date 2018-10-15 10:29:11
 */
@TableName("us_healthy")
public class UsHealthyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 内容
     */
    private String content;
    /**
     * 简介
     */
    private String summary;

    public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：id
     */
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置：id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 获取：图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }


    /**
     * 获取：状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置：状态
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


}
