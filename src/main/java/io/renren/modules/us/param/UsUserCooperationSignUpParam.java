package io.renren.modules.us.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsUserCooperationSignUpParam extends UsBaseParam {
    @NotBlank(message = "openid不能为空")
    private String openid;

    @NotBlank(message = "accessToken不能为空")
    private String accessToken;

    @NotBlank(message = "type不能为空")
    private String type;

    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String code;

    private String password;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
