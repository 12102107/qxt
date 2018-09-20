package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author fss
 * @date 2018-09-07 15:11:36
 */
@TableName("us_trip_location")
public class UsTripLocationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 地点名称
	 */
	private String name;
	/**
	 * 类型 0:默认 1:收藏出发地 2:收藏目的地 3:家 4:公司 5:收藏
	 */
	private String type;
	/**
	 * 已收藏 0:false 1:true
	 */
	private String isFavourite;
	/**
	 * 已常用 0:false 1:true
	 */
	private String isFrequent;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 高德经度
	 */
	private String amapLongitude;
	/**
	 * 高德纬度
	 */
	private String amapLatitude;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 已删除 0:false 1:true
	 */
	private String isDeleted;

	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：地点名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：地点名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型 0:默认 1:收藏出发地 2:收藏目的地 3:家 4:公司 5:收藏
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型 0:默认 1:收藏出发地 2:收藏目的地 3:家 4:公司 5:收藏
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：已收藏 0:false 1:true
	 */
	public void setIsFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}
	/**
	 * 获取：已收藏 0:false 1:true
	 */
	public String getIsFavourite() {
		return isFavourite;
	}
	/**
	 * 设置：已常用 0:false 1:true
	 */
	public void setIsFrequent(String isFrequent) {
		this.isFrequent = isFrequent;
	}
	/**
	 * 获取：已常用 0:false 1:true
	 */
	public String getIsFrequent() {
		return isFrequent;
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
	 * 设置：高德经度
	 */
	public void setAmapLongitude(String amapLongitude) {
		this.amapLongitude = amapLongitude;
	}
	/**
	 * 获取：高德经度
	 */
	public String getAmapLongitude() {
		return amapLongitude;
	}
	/**
	 * 设置：高德纬度
	 */
	public void setAmapLatitude(String amapLatitude) {
		this.amapLatitude = amapLatitude;
	}
	/**
	 * 获取：高德纬度
	 */
	public String getAmapLatitude() {
		return amapLatitude;
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
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：已删除 0:false 1:true
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：已删除 0:false 1:true
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
}
