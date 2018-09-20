package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-08-29 13:09:39
 */
@TableName("t_push")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsPushEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 个推用户id
	 */
	private String clientId;
	/**
	 * 创建日期
	 */
	private String createTime;
	/**
	 * 推送标题
	 */
	private String pushTitle;
	/**
	 * 推送内容
	 */
	private String pushContent;
	/**
	 * 推送图片
	 */
	private String pushImg;
	/**
	 * 推送状态
	 */
	private String pushSts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public String getPushImg() {
		return pushImg;
	}

	public void setPushImg(String pushImg) {
		this.pushImg = pushImg;
	}

	public String getPushSts() {
		return pushSts;
	}

	public void setPushSts(String pushSts) {
		this.pushSts = pushSts;
	}
}
