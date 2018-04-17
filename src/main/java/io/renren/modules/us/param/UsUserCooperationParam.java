package io.renren.modules.us.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsUserCooperationParam extends UsSessionParam {

    @NotBlank(message = "openid不能为空")
    private String openid;

    @NotBlank(message = "accessToken不能为空")
    private String accessToken;

    @NotBlank(message = "type不能为空")
    private String type;

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
}
