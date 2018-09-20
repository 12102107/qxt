package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;



/**
 * @author gaoxipeng
 * @Date 2018/9/7 14:34
 * @Description 线路查询接受的实体类
 */
public class UsBusRouteParam extends UsBaseParam {
    @ApiModelProperty(value = "出发点")
    @NotBlank(message="出发点不能为空")
    private String origin;

    @ApiModelProperty(value = "目的地")
    @NotBlank(message="出发点不能为空")
    private String destination;

    @ApiModelProperty(value = "公交换乘策略")
    private String strategy;

    @ApiModelProperty(value = "出发日期")
    private String date;

    @ApiModelProperty(value = "出发时间")
    private String time;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
