package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

public class UsUserAuthParam extends UsBaseParam {

    @NotBlank(message = "mobile不能为空")
    private String mobile;

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "citizenNo不能为空")
    private String citizenNo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitizenNo() {
        return citizenNo;
    }

    public void setCitizenNo(String citizenNo) {
        this.citizenNo = citizenNo;
    }
}
