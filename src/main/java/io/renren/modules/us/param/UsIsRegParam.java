package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsIsRegParam extends UsBaseParam{

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
