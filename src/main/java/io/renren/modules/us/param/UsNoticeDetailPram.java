package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 *
 * @author sys
 * @email
 * @date 2018-04-18 13:42:29
 */
public class UsNoticeDetailPram extends UsSessionParam{

	@ApiModelProperty(value = "主键ID")
	@NotBlank(message="主键ID不能为空")
	private String noticeId;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
}
