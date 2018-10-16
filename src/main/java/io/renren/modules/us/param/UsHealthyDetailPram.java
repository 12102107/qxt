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
public class UsHealthyDetailPram extends UsBaseParam{

	@ApiModelProperty(value = "主键ID")
	@NotBlank(message="主键ID不能为空")
	private String healthyId;

	public String getHealthyId() {
		return healthyId;
	}

	public void setHealthyId(String healthyId) {
		this.healthyId = healthyId;
	}
}
