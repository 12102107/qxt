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
 * @date 2018-04-28 14:01:06
 */
@TableName("us_fund_info")
public class UsFundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String sfdj;
	/**
	 * 
	 */
	private String zgxm;
	/**
	 * 
	 */
	private String sfyc;
	/**
	 * 
	 */
	private String dwzh;
	/**
	 * 
	 */
	private String sfbzxdk;
	/**
	 * 
	 */
	private String dwmc;
	/**
	 * 
	 */
	private String dwyje;
	/**
	 * 
	 */
	private String khrq;
	/**
	 * 
	 */
	private String yjce;
	/**
	 * 
	 */
	private String dwjjbl;
	/**
	 * 
	 */
	private String grzhzt;
	/**
	 * 
	 */
	private String sfzh;
	/**
	 * 
	 */
	private String grzh;
	/**
	 * 
	 */
	private String gryje;
	/**
	 * 
	 */
	private String grjjbl;
	/**
	 * 
	 */
	private String jzny;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setSfdj(String sfdj) {
		this.sfdj = sfdj;
	}
	/**
	 * 获取：
	 */
	public String getSfdj() {
		return sfdj;
	}
	/**
	 * 设置：
	 */
	public void setZgxm(String zgxm) {
		this.zgxm = zgxm;
	}
	/**
	 * 获取：
	 */
	public String getZgxm() {
		return zgxm;
	}
	/**
	 * 设置：
	 */
	public void setSfyc(String sfyc) {
		this.sfyc = sfyc;
	}
	/**
	 * 获取：
	 */
	public String getSfyc() {
		return sfyc;
	}
	/**
	 * 设置：
	 */
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	/**
	 * 获取：
	 */
	public String getDwzh() {
		return dwzh;
	}
	/**
	 * 设置：
	 */
	public void setSfbzxdk(String sfbzxdk) {
		this.sfbzxdk = sfbzxdk;
	}
	/**
	 * 获取：
	 */
	public String getSfbzxdk() {
		return sfbzxdk;
	}
	/**
	 * 设置：
	 */
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	/**
	 * 获取：
	 */
	public String getDwmc() {
		return dwmc;
	}
	/**
	 * 设置：
	 */
	public void setDwyje(String dwyje) {
		this.dwyje = dwyje;
	}
	/**
	 * 获取：
	 */
	public String getDwyje() {
		return dwyje;
	}
	/**
	 * 设置：
	 */
	public void setKhrq(String khrq) {
		this.khrq = khrq;
	}
	/**
	 * 获取：
	 */
	public String getKhrq() {
		return khrq;
	}
	/**
	 * 设置：
	 */
	public void setYjce(String yjce) {
		this.yjce = yjce;
	}
	/**
	 * 获取：
	 */
	public String getYjce() {
		return yjce;
	}
	/**
	 * 设置：
	 */
	public void setDwjjbl(String dwjjbl) {
		this.dwjjbl = dwjjbl;
	}
	/**
	 * 获取：
	 */
	public String getDwjjbl() {
		return dwjjbl;
	}
	/**
	 * 设置：
	 */
	public void setGrzhzt(String grzhzt) {
		this.grzhzt = grzhzt;
	}
	/**
	 * 获取：
	 */
	public String getGrzhzt() {
		return grzhzt;
	}
	/**
	 * 设置：
	 */
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	/**
	 * 获取：
	 */
	public String getSfzh() {
		return sfzh;
	}
	/**
	 * 设置：
	 */
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	/**
	 * 获取：
	 */
	public String getGrzh() {
		return grzh;
	}
	/**
	 * 设置：
	 */
	public void setGryje(String gryje) {
		this.gryje = gryje;
	}
	/**
	 * 获取：
	 */
	public String getGryje() {
		return gryje;
	}
	/**
	 * 设置：
	 */
	public void setGrjjbl(String grjjbl) {
		this.grjjbl = grjjbl;
	}
	/**
	 * 获取：
	 */
	public String getGrjjbl() {
		return grjjbl;
	}
	/**
	 * 设置：
	 */
	public void setJzny(String jzny) {
		this.jzny = jzny;
	}
	/**
	 * 获取：
	 */
	public String getJzny() {
		return jzny;
	}
}
