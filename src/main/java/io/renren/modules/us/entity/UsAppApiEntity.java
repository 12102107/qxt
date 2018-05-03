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
 * @date 2018-05-03 10:30:53
 */
@TableName("us_app_api")
public class UsAppApiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * name
	 */
	private String appid;
	/**
	 * 
	 */
	private String apiid;

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
	 * 设置：name
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：name
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * 设置：
	 */
	public void setApiid(String apiid) {
		this.apiid = apiid;
	}
	/**
	 * 获取：
	 */
	public String getApiid() {
		return apiid;
	}
}
