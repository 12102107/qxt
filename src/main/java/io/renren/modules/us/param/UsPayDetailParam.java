package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

public class UsPayDetailParam extends UsSessionParam {

    @NotBlank(message = "orderId不能为空")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}