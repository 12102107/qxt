package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

public class UsCardUpdateParam extends UsSessionParam {

    @NotBlank(message = "partner不能为空")
    private String partner;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
