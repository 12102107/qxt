package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class UsEidLoginParam extends UsBaseParam {

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "个推clientID")
    @NotBlank(message = "个推clientID不能为空")
    @Length(min = 1, max = 50)
    private String client_id;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
