package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * @author Li
 */
public class UsIsRegParam extends UsBaseParam{

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message="手机号码不能为空")
    @Length(min = 1, max = 30)
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
