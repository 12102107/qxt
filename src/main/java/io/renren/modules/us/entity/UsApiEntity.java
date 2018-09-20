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
 * @date 2018-08-31 10:29:49
 */
@TableName("us_api")
public class UsApiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * name
	 */
	private String apiname;
	/**
	 * url
	 */
	private String url;
	/**
	 * type
	 */
	private String apitype;
	/**
	 * state
	 */
	private String apistate;
	/**
	 * createDate
	 */
	private Date createDate;

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：name
	 */
	public void setApiname(String apiname) {
		this.apiname = apiname;
	}
	/**
	 * 获取：name
	 */
	public String getApiname() {
		return apiname;
	}
	/**
	 * 设置：url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：type
	 */
	public void setApitype(String apitype) {
		this.apitype = apitype;
	}
	/**
	 * 获取：type
	 */
	public String getApitype() {
		return apitype;
	}
	/**
	 * 设置：state
	 */
	public void setApistate(String apistate) {
		this.apistate = apistate;
	}
	/**
	 * 获取：state
	 */
	public String getApistate() {
		return apistate;
	}
	/**
	 * 设置：createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public String toString() {
		return "UsApiEntity{" +
				"id='" + id + '\'' +
				", apiname='" + apiname + '\'' +
				", url='" + url + '\'' +
				", apitype='" + apitype + '\'' +
				", apistate='" + apistate + '\'' +
				", createDate=" + createDate +
				'}';
	}
}
