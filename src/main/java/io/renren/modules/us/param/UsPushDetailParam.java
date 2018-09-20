package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class UsPushDetailParam extends UsSessionParam {

    @NotBlank(message = "id不能为空")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
