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
 * @date 2018-04-18 14:21:15
 */
@TableName("t_s_depart")
public class TSDepartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private String id;
	/**
	 * 部门名称
	 */
	private String departname;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 父部门ID
	 */
	private String parentdepartid;
	/**
	 * 机构编码
	 */
	private String orgCode;
	/**
	 * 机构类型
	 */
	private String orgType;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 排序
	 */
	private String departOrder;
	/**
	 * 英文名
	 */
	private String departnameEn;
	/**
	 * 缩写
	 */
	private String departnameAbbr;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 创建人账号
	 */
	private String createBy;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新人名称
	 */
	private String updateName;
	/**
	 * 更新人账号
	 */
	private String updateBy;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 数据所属公司
	 */
	private String sysCompanyCode;
	/**
	 * 数据所属部门
	 */
	private String sysOrgCode;

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
	 * 设置：部门名称
	 */
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDepartname() {
		return departname;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：父部门ID
	 */
	public void setParentdepartid(String parentdepartid) {
		this.parentdepartid = parentdepartid;
	}
	/**
	 * 获取：父部门ID
	 */
	public String getParentdepartid() {
		return parentdepartid;
	}
	/**
	 * 设置：机构编码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 获取：机构编码
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * 设置：机构类型
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	/**
	 * 获取：机构类型
	 */
	public String getOrgType() {
		return orgType;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 获取：传真
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：排序
	 */
	public void setDepartOrder(String departOrder) {
		this.departOrder = departOrder;
	}
	/**
	 * 获取：排序
	 */
	public String getDepartOrder() {
		return departOrder;
	}
	/**
	 * 设置：英文名
	 */
	public void setDepartnameEn(String departnameEn) {
		this.departnameEn = departnameEn;
	}
	/**
	 * 获取：英文名
	 */
	public String getDepartnameEn() {
		return departnameEn;
	}
	/**
	 * 设置：缩写
	 */
	public void setDepartnameAbbr(String departnameAbbr) {
		this.departnameAbbr = departnameAbbr;
	}
	/**
	 * 获取：缩写
	 */
	public String getDepartnameAbbr() {
		return departnameAbbr;
	}
	/**
	 * 设置：备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取：备注
	 */
	public String getMemo() {
		return memo;
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
	 * 设置：创建人账号
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人账号
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
	 * 设置：更新人名称
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * 获取：更新人名称
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * 设置：更新人账号
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新人账号
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：更新日期
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新日期
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：数据所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 * 获取：数据所属公司
	 */
	public String getSysCompanyCode() {
		return sysCompanyCode;
	}
	/**
	 * 设置：数据所属部门
	 */
	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 * 获取：数据所属部门
	 */
	public String getSysOrgCode() {
		return sysOrgCode;
	}
}
