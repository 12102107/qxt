package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

/**
 * @author Li
 */
public class UsSmsParam extends UsBaseParam{

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
