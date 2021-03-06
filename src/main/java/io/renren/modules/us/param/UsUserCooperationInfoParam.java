package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

/**
 * @author Li
 */
public class UsUserCooperationInfoParam extends UsBaseParam {

    @NotBlank(message = "code不能为空")
    private String code;

    @NotBlank(message = "type不能为空")
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UsUserCooperationInfoParam{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
