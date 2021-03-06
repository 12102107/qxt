package io.renren.modules.us.param;

import javax.validation.constraints.NotBlank;

public class UsSessionParam extends UsBaseParam {

    @NotBlank(message = "session不能为空")
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}
