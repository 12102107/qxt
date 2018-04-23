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
 * @date 2018-04-23 13:47:28
 */
@TableName("us_notice")
public class UsNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private String id;
	/**
	 * 通知标题
	 */
	private String noticeTitle;
	/**
	 * 通知公告内容
	 */
	private String noticeContent;
	/**
	 * 通知公告类型（1：消息，2:公告）
	 */
	private String noticeType;
	/**
	 * 通告授权级别（1:全员，2：用户）
	 */
	private String noticeLevel;
	/**
	 * 阅读期限
	 */
	private Date noticeTerm;
	/**
	 * 创建者
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 发布状态（1:发布，2:未发布）
	 */
	private String noticeStatus;

	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：通知标题
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	/**
	 * 获取：通知标题
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}
	/**
	 * 设置：通知公告内容
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	/**
	 * 获取：通知公告内容
	 */
	public String getNoticeContent() {
		return noticeContent;
	}
	/**
	 * 设置：通知公告类型（1：消息，2:公告）
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	/**
	 * 获取：通知公告类型（1：消息，2:公告）
	 */
	public String getNoticeType() {
		return noticeType;
	}
	/**
	 * 设置：通告授权级别（1:全员，2：用户）
	 */
	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}
	/**
	 * 获取：通告授权级别（1:全员，2：用户）
	 */
	public String getNoticeLevel() {
		return noticeLevel;
	}
	/**
	 * 设置：阅读期限
	 */
	public void setNoticeTerm(Date noticeTerm) {
		this.noticeTerm = noticeTerm;
	}
	/**
	 * 获取：阅读期限
	 */
	public Date getNoticeTerm() {
		return noticeTerm;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：发布状态（1:发布，2:未发布）
	 */
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	/**
	 * 获取：发布状态（1:发布，2:未发布）
	 */
	public String getNoticeStatus() {
		return noticeStatus;
	}
}
