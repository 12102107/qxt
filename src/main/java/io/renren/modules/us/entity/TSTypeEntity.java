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
 * @date 2018-04-18 15:30:38
 */
@TableName("t_s_type")
public class TSTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 字典编码
	 */
	private String typecode;
	/**
	 * 字典名称
	 */
	private String typename;
	/**
	 * 无用字段
	 */
	private String typepid;
	/**
	 * 字典组ID
	 */
	private String typegroupid;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建用户
	 */
	private String createName;

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
	 * 设置：字典编码
	 */
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	/**
	 * 获取：字典编码
	 */
	public String getTypecode() {
		return typecode;
	}
	/**
	 * 设置：字典名称
	 */
	public void setTypename(String typename) {
		this.typename = typename;
	}
	/**
	 * 获取：字典名称
	 */
	public String getTypename() {
		return typename;
	}
	/**
	 * 设置：无用字段
	 */
	public void setTypepid(String typepid) {
		this.typepid = typepid;
	}
	/**
	 * 获取：无用字段
	 */
	public String getTypepid() {
		return typepid;
	}
	/**
	 * 设置：字典组ID
	 */
	public void setTypegroupid(String typegroupid) {
		this.typegroupid = typegroupid;
	}
	/**
	 * 获取：字典组ID
	 */
	public String getTypegroupid() {
		return typegroupid;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：创建用户
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * 获取：创建用户
	 */
	public String getCreateName() {
		return createName;
	}
}
