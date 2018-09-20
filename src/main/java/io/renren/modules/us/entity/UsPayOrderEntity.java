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
 * @date 2018-09-17 14:43:06
 */
@TableName("us_pay_order")
public class UsPayOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String id;
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 卡ID
	 */
	private String cardId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 标题
	 */
	private String subject;
	/**
	 * 内容
	 */
	private String body;
	/**
	 * 支付方式 0:微信APP支付 1:支付宝APP支付
	 */
	private String channel;
	/**
	 * 类型 0:收入 1:支出
	 */
	private String type;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 货币代码  cny:人民币
	 */
	private String currency;
	/**
	 * 状态 0:生成订单 1:支付中 2:支付成功 3:业务处理完成
	 */
	private String status;
	/**
	 * 当前余额
	 */
	private Double balance;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;
	/**
	 * 备注
	 */
	private String remark;

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
	 * 设置：应用ID
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：应用ID
	 */
	public String getAppid() {
		return appid;
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
	 * 设置：卡ID
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	/**
	 * 获取：卡ID
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置：标题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取：标题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置：内容
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * 获取：内容
	 */
	public String getBody() {
		return body;
	}
	/**
	 * 设置：支付方式 0:微信APP支付 1:支付宝APP支付
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * 获取：支付方式 0:微信APP支付 1:支付宝APP支付
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * 设置：类型 0:收入 1:支出
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型 0:收入 1:支出
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * 获取：金额
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * 设置：货币代码  cny:人民币
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * 获取：货币代码  cny:人民币
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * 设置：状态 0:生成订单 1:支付中 2:支付成功 3:业务处理完成
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0:生成订单 1:支付中 2:支付成功 3:业务处理完成
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：当前余额
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	/**
	 * 获取：当前余额
	 */
	public Double getBalance() {
		return balance;
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
	 * 设置：
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateDate() {
		return updateDate;
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
}
