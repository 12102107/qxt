package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-04-19 14:43:50
 */
@TableName("us_regions_pro")
public class UsRegionsProEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer regionId;
	/**
	 * 
	 */
	private String localName;
	/**
	 * 
	 */
	private Long childnum;
	/**
	 * 
	 */
	private Integer pRegionId;
	/**
	 * 
	 */
	private String regionPath;
	/**
	 * 
	 */
	private Integer regionGrade;

	/**
	 * 设置：
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * 获取：
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * 设置：
	 */
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	/**
	 * 获取：
	 */
	public String getLocalName() {
		return localName;
	}
	/**
	 * 设置：
	 */
	public void setChildnum(Long childnum) {
		this.childnum = childnum;
	}
	/**
	 * 获取：
	 */
	public Long getChildnum() {
		return childnum;
	}
	/**
	 * 设置：
	 */
	public void setPRegionId(Integer pRegionId) {
		this.pRegionId = pRegionId;
	}
	/**
	 * 获取：
	 */
	public Integer getPRegionId() {
		return pRegionId;
	}
	/**
	 * 设置：
	 */
	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}
	/**
	 * 获取：
	 */
	public String getRegionPath() {
		return regionPath;
	}
	/**
	 * 设置：
	 */
	public void setRegionGrade(Integer regionGrade) {
		this.regionGrade = regionGrade;
	}
	/**
	 * 获取：
	 */
	public Integer getRegionGrade() {
		return regionGrade;
	}
}
