package io.renren.modules.us.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsFundInfoParam extends UsBaseParam {

    @NotBlank(message = "ZGXM不能为空")
    private String ZGXM;

    @NotBlank(message = "SFZH不能为空")
    private String SFZH;

    public String getZGXM() {
        return ZGXM;
    }

    public void setZGXM(String ZGXM) {
        this.ZGXM = ZGXM;
    }

    public String getSFZH() {
        return SFZH;
    }

    public void setSFZH(String SFZH) {
        this.SFZH = SFZH;
    }
}
