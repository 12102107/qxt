package io.renren.modules.us.param;

/**
 *
 *
 * @author sys
 * @email
 * @date 2018-04-18 13:42:29
 */
public class UsUserHPram{
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 真实名字
	 */
	private String realname;
	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 身份证号
	 */
	private String citizenNo;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 头像
	 */
	private String portrait;

	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 联系地址
	 */
	private String address;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 有效状态
	 */
	private Integer status;

	/**
	 *
	 */
	private String session;
	/**
	 * 职业id
	 */
	private String uJobid;
	/**
	 * 职业
	 */
	private String personJob;
	/**
	 * 工作单位
	 */
	private String uDepartid;
	/**
	 * 工作单位
	 */
	private String personDepartname;

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
	 * 设置：手机号
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * 设置：真实名字
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}
	/**
	 * 获取：真实名字
	 */
	public String getRealname() {
		return realname;
	}
	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置：身份证号
	 */
	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo;
	}
	/**
	 * 获取：身份证号
	 */
	public String getCitizenNo() {
		return citizenNo;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：头像
	 */
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	/**
	 * 获取：头像
	 */
	public String getPortrait() {
		return portrait;
	}


	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：联系地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：联系地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：有效状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：有效状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：
	 */
	public void setSession(String session) {
		this.session = session;
	}
	/**
	 * 获取：
	 */
	public String getSession() {
		return session;
	}

	/**
	 * 设置：职业
	 */
	public void setPersonJob(String personJob) {
		this.personJob = personJob;
	}
	/**
	 * 获取：职业
	 */
	public String getPersonJob() {
		return personJob;
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

	/**
	 * 设置：工作单位
	 */
	public void setPersonDepartname(String personDepartname) {
		this.personDepartname = personDepartname;
	}
	/**
	 * 获取：工作单位
	 */
	public String getPersonDepartname() {
		return personDepartname;
	}
}
