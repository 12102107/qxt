package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;


/**
 *
 *
 * @author sys
 * @email
 * @date 2018-04-18 13:42:29
 */
public class UsNoticePram extends UsBaseParam{

	@ApiModelProperty(value = "类型")
	@NotBlank(message="类型不能为空")
	private String noticeType;

	@ApiModelProperty(value = "页码")
	private String pageNo;

	@ApiModelProperty(value = "每页显示条数")
	private String pageSize;

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}
