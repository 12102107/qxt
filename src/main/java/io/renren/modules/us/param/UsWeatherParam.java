package io.renren.modules.us.param;


import javax.validation.constraints.NotBlank;

public class UsWeatherParam extends UsBaseParam{

    @NotBlank(message = "城市不能为空")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
}