package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

/**
 * @author Li
 */
public class UsEidParam extends UsBaseParam{

 
    private String mobile;
    
    @NotBlank(message = "session不能为空")
    private String session;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
    
}
