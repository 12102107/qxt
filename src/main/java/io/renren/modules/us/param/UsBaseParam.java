package io.renren.modules.us.param;



import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Li
 */
public class UsBaseParam implements Serializable {

    @NotBlank(message = "appid不能为空")
    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
    
}
