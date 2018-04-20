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
 * @date 2018-04-20 09:55:04
 */
@TableName("t_s_typegroup")
public class TSTypegroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 字典分组编码
	 */
	private String typegroupcode;
	/**
	 * 字典分组名称
	 */
	private String typegroupname;
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
	 * 设置：字典分组编码
	 */
	public void setTypegroupcode(String typegroupcode) {
		this.typegroupcode = typegroupcode;
	}
	/**
	 * 获取：字典分组编码
	 */
	public String getTypegroupcode() {
		return typegroupcode;
	}
	/**
	 * 设置：字典分组名称
	 */
	public void setTypegroupname(String typegroupname) {
		this.typegroupname = typegroupname;
	}
	/**
	 * 获取：字典分组名称
	 */
	public String getTypegroupname() {
		return typegroupname;
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
