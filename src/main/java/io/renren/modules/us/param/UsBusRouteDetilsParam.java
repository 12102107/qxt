package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author gaoxipeng
 * @Date 2018/9/14 9:32
 * @Description TODO
 */
public class UsBusRouteDetilsParam extends UsBaseParam {


    @ApiModelProperty(value = "id标识")
    @NotBlank(message="id标识不能为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
