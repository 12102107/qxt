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
 * @date 2018-04-18 16:06:54
 */
@TableName("t_info")
public class TInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
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
	 * 更新人名称
	 */
	private String updateName;
	/**
	 * 更新人登录名称
	 */
	private String updateBy;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 所属部门
	 */
	private String sysOrgCode;
	/**
	 * 所属公司
	 */
	private String sysCompanyCode;
	/**
	 * 流程状态
	 */
	private String bpmStatus;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 正文
	 */
	private String content;

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
	 * 设置：更新人登录名称
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新人登录名称
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
	 * 设置：所属部门
	 */
	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 * 获取：所属部门
	 */
	public String getSysOrgCode() {
		return sysOrgCode;
	}
	/**
	 * 设置：所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 * 获取：所属公司
	 */
	public String getSysCompanyCode() {
		return sysCompanyCode;
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
	 * 设置：代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：代码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：正文
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：正文
	 */
	public String getContent() {
		return content;
	}
}
