package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-08-29 13:09:39
 */
@TableName("us_card_menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsCardMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 卡id
	 */
	private String cardId;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 卡菜单对应的url地址
	 */
	private String cardUrl;
	/**
	 * 父节点
	 */
	private String parentId;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 启用状态 1启用 0未启用
	 */
	private String status;
	/**
	 * 所属公司
	 */
	private Integer cardOrder;
	/**
	 * 菜单图标
	 */
	private String menuIcon;

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
	 * 设置：卡id
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	/**
	 * 获取：卡id
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * 设置：菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getMenuName() {
		return menuName;
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
	 * 设置：卡菜单对应的url地址
	 */
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	/**
	 * 获取：卡菜单对应的url地址
	 */
	public String getCardUrl() {
		return cardUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * 设置：启用状态 1启用 0未启用
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：启用状态 1启用 0未启用
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：所属公司
	 */
	public void setCardOrder(Integer cardOrder) {
		this.cardOrder = cardOrder;
	}
	/**
	 * 获取：所属公司
	 */
	public Integer getCardOrder() {
		return cardOrder;
	}
	/**
	 * 设置：菜单图标
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	/**
	 * 获取：菜单图标
	 */
	public String getMenuIcon() {
		return menuIcon;
	}
}
