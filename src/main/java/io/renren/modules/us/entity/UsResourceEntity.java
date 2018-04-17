package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-04-12 16:21:17
 */
@TableName("us_resource")
public class UsResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String icon;
	/**
	 * 
	 */
	private String iosUrl;
	/**
	 * 
	 */
	private String androidUrl;
	/**
	 * 
	 */
	private String webCurl;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private String createDate;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置：
	 */
	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}
	/**
	 * 获取：
	 */
	public String getIosUrl() {
		return iosUrl;
	}
	/**
	 * 设置：
	 */
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	/**
	 * 获取：
	 */
	public String getAndroidUrl() {
		return androidUrl;
	}
	/**
	 * 设置：
	 */
	public void setWebCurl(String webCurl) {
		this.webCurl = webCurl;
	}
	/**
	 * 获取：
	 */
	public String getWebCurl() {
		return webCurl;
	}
	/**
	 * 设置：
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public String getCreateDate() {
		return createDate;
	}
}
