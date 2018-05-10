package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsResetpwdParam extends UsBaseParam{

    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    @Length(min = 1, max = 45)
    private String smsCode;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message="新密码不能为空")
    @Length(min = 1, max = 100)
    private String newPassword;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message="手机号码不能为空")
    @Length(min = 1, max = 30)
    private String mobilePhone;


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
