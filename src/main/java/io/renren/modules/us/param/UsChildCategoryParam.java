package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

/**
 * @author Li
 */
public class UsChildCategoryParam extends UsPageParam {

    @NotBlank(message = "code不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
