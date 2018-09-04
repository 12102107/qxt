package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

/**
 * @author Li
 */
public class UsResourceParam extends UsPageParam {

    @NotBlank(message = "category不能为空")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
