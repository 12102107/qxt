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
 * @date 2018-04-18 16:06:20
 */
@TableName("t_mayor_letter")
public class TMayorLetterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;

	private String appid;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 创建人登录名称
	 */
	private String createBy;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 流程状态
	 */
	private String bpmStatus;
	/**
	 * 提出者姓名
	 */
	private String personName;
	/**
	 * 提出者性别
	 */
	private String personSex;
	/**
	 * 发信人职业id
	 */
	private String uJobid;
	/**
	 * 提出者职业
	 */
	private String personJob;
	/**
	 * 发信人工作单位id
	 */
	private String uDepartid;
	/**
	 * 发信人工作单位
	 */
	private String personDepartname;
	/**
	 * 提出者身份证号码
	 */
	private String personIdCard;
	/**
	 * 提出者手机号
	 */
	private String personMobile;
	/**
	 * 提出者电子邮箱
	 */
	private String personEmail;
	/**
	 * 问题一级属地
	 */
	private String questionProvince;
	/**
	 * 问题二级属地
	 */
	private String questionCity;
	/**
	 * 问题标题
	 */
	private String questionTitle;
	/**
	 * 问题内容
	 */
	private String questionContent;
	/**
	 * 发送人id
	 */
	private String senderId;
	/**
	 *
	 */
	private String senderSession;
	/**
	 * 信件编号
	 */
	private String letterCode;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 回复人id
	 */
	private String replyId;
	/**
	 * 回复时间
	 */
	private Date replyTime;
	/**
	 * 回复人姓名
	 */
	private String replyName;
	/**
	 * 回复人部门id
	 */
	private String replyDepartid;
	/**
	 * 回复人部门
	 */
	private String replyDepartname;

	//删除标识
	private String deleteFlag;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：创建人名称
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * 获取：创建人名称
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * 设置：创建人登录名称
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人登录名称
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：流程状态
	 */
	public void setBpmStatus(String bpmStatus) {
		this.bpmStatus = bpmStatus;
	}
	/**
	 * 获取：流程状态
	 */
	public String getBpmStatus() {
		return bpmStatus;
	}
	/**
	 * 设置：提出者姓名
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * 获取：提出者姓名
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * 设置：提出者性别
	 */
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	/**
	 * 获取：提出者性别
	 */
	public String getPersonSex() {
		return personSex;
	}

	/**
	 * 设置：提出者职业
	 */
	public void setPersonJob(String personJob) {
		this.personJob = personJob;
	}
	/**
	 * 获取：提出者职业
	 */
	public String getPersonJob() {
		return personJob;
	}

	/**
	 * 设置：发信人工作单位
	 */
	public void setPersonDepartname(String personDepartname) {
		this.personDepartname = personDepartname;
	}
	/**
	 * 获取：发信人工作单位
	 */
	public String getPersonDepartname() {
		return personDepartname;
	}
	/**
	 * 设置：提出者身份证号码
	 */
	public void setPersonIdCard(String personIdCard) {
		this.personIdCard = personIdCard;
	}
	/**
	 * 获取：提出者身份证号码
	 */
	public String getPersonIdCard() {
		return personIdCard;
	}
	/**
	 * 设置：提出者手机号
	 */
	public void setPersonMobile(String personMobile) {
		this.personMobile = personMobile;
	}
	/**
	 * 获取：提出者手机号
	 */
	public String getPersonMobile() {
		return personMobile;
	}
	/**
	 * 设置：提出者电子邮箱
	 */
	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	/**
	 * 获取：提出者电子邮箱
	 */
	public String getPersonEmail() {
		return personEmail;
	}
	/**
	 * 设置：问题一级属地
	 */
	public void setQuestionProvince(String questionProvince) {
		this.questionProvince = questionProvince;
	}
	/**
	 * 获取：问题一级属地
	 */
	public String getQuestionProvince() {
		return questionProvince;
	}
	/**
	 * 设置：问题二级属地
	 */
	public void setQuestionCity(String questionCity) {
		this.questionCity = questionCity;
	}
	/**
	 * 获取：问题二级属地
	 */
	public String getQuestionCity() {
		return questionCity;
	}
	/**
	 * 设置：问题标题
	 */
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	/**
	 * 获取：问题标题
	 */
	public String getQuestionTitle() {
		return questionTitle;
	}
	/**
	 * 设置：问题内容
	 */
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	/**
	 * 获取：问题内容
	 */
	public String getQuestionContent() {
		return questionContent;
	}
	/**
	 * 设置：发送人id
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * 获取：发送人id
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * 设置：
	 */
	public void setSenderSession(String senderSession) {
		this.senderSession = senderSession;
	}
	/**
	 * 获取：
	 */
	public String getSenderSession() {
		return senderSession;
	}
	/**
	 * 设置：信件编号
	 */
	public void setLetterCode(String letterCode) {
		this.letterCode = letterCode;
	}
	/**
	 * 获取：信件编号
	 */
	public String getLetterCode() {
		return letterCode;
	}
	/**
	 * 设置：回复内容
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * 获取：回复内容
	 */
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * 设置：回复人id
	 */
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	/**
	 * 获取：回复人id
	 */
	public String getReplyId() {
		return replyId;
	}
	/**
	 * 设置：回复时间
	 */
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	/**
	 * 获取：回复时间
	 */
	public Date getReplyTime() {
		return replyTime;
	}
	/**
	 * 设置：回复人姓名
	 */
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	/**
	 * 获取：回复人姓名
	 */
	public String getReplyName() {
		return replyName;
	}

	/**
	 * 设置：回复人部门
	 */
	public void setReplyDepartname(String replyDepartname) {
		this.replyDepartname = replyDepartname;
	}
	/**
	 * 获取：回复人部门
	 */
	public String getReplyDepartname() {
		return replyDepartname;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getuJobid() {
		return uJobid;
	}

	public void setuJobid(String uJobid) {
		this.uJobid = uJobid;
	}

	public String getuDepartid() {
		return uDepartid;
	}

	public void setuDepartid(String uDepartid) {
		this.uDepartid = uDepartid;
	}

	public String getReplyDepartid() {
		return replyDepartid;
	}

	public void setReplyDepartid(String replyDepartid) {
		this.replyDepartid = replyDepartid;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
