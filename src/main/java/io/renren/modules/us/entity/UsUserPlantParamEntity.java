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
 * @date 2018-04-12 16:21:17
 */
@TableName("us_user_plant_param")
public class UsUserPlantParamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 设备型号
	 */
	private String unitType;
	/**
	 * 设备厂商
	 */
	private String equipmentManufacturer;
	/**
	 * 屏幕分辨率
	 */
	private String screenResolution;
	/**
	 * DPI
	 */
	private String dpi;
	/**
	 * 系统名称
	 */
	private String systemName;
	/**
	 * 系统版本
	 */
	private String systemVersion;
	/**
	 * 网络类型
	 */
	private String networkType;
	/**
	 * 创建时间
	 */
	private Date createDate;

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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：设备型号
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	/**
	 * 获取：设备型号
	 */
	public String getUnitType() {
		return unitType;
	}
	/**
	 * 设置：设备厂商
	 */
	public void setEquipmentManufacturer(String equipmentManufacturer) {
		this.equipmentManufacturer = equipmentManufacturer;
	}
	/**
	 * 获取：设备厂商
	 */
	public String getEquipmentManufacturer() {
		return equipmentManufacturer;
	}
	/**
	 * 设置：屏幕分辨率
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}
	/**
	 * 获取：屏幕分辨率
	 */
	public String getScreenResolution() {
		return screenResolution;
	}
	/**
	 * 设置：DPI
	 */
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	/**
	 * 获取：DPI
	 */
	public String getDpi() {
		return dpi;
	}
	/**
	 * 设置：系统名称
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	/**
	 * 获取：系统名称
	 */
	public String getSystemName() {
		return systemName;
	}
	/**
	 * 设置：系统版本
	 */
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	/**
	 * 获取：系统版本
	 */
	public String getSystemVersion() {
		return systemVersion;
	}
	/**
	 * 设置：网络类型
	 */
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	/**
	 * 获取：网络类型
	 */
	public String getNetworkType() {
		return networkType;
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
}
