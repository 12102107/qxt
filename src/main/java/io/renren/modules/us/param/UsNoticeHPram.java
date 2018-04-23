package io.renren.modules.us.param;

import java.util.Date;

/**
 *
 *
 * @author sys
 * @email
 * @date 2018-04-18 13:42:29
 */
public class UsNoticeHPram{

	private String id;
	/**
	 * 通知标题
	 */
	private String noticeTitle;
	/**
	 * 通知公告类型（1：消息，2:公告）
	 */
	private String noticeType;
	/**
	 * 创建时间
	 */
	private Date createTime;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
