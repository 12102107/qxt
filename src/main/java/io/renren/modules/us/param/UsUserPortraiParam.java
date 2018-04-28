package io.renren.modules.us.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsUserPortraiParam extends UsSessionParam{

    @NotBlank(message = "请上传图片")
    private String portraitData;

    public String getPortraitData() {
        return portraitData;
    }

    public void setPortraitData(String portraitData) {
        this.portraitData = portraitData;
    }
}
