package io.renren.modules.us.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsUserCooperationBindParam extends UsBaseParam {
    @NotBlank(message = "openid不能为空")
    private String openid;

    @NotBlank(message = "accessToken不能为空")
    private String accessToken;

    @NotBlank(message = "type不能为空")
    private String type;

    @NotBlank(message = "手机号码不能为空")
    private String mobile;

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

}
