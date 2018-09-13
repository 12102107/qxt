package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@TableName("us_trip_history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsHistoryEntity  implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 类型 0:公交 1:驾车 2:步行
     */
    private String type;
    /**
     * 出行策略 0:最快捷模式 1:最经济模式 2:最少换乘模式 3:最少步行模式 5:不乘地铁模式
     */
    private String strategy;
    /**
     * 城市
     */
    private String city;
    /**
     * 出发地
     */
    private String origin;
    /**
     * 出发地高德经度
     */
    private String originAmapLongitude;
    /**
     * 出发地高德纬度
     */
    private String originAmapLatitude ;
    /**
     * 目的地
     */
    private String destination;
    /**
     * 目的地高德经度
     */
    private String destinationAmapLongitude;
    /**
     * 目的地高德纬度
     */
    private String destinationAmapLatitude;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 搜索方式 1 路线 2地点
     */
    private String findMethod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginAmapLongitude() {
        return originAmapLongitude;
    }

    public void setOriginAmapLongitude(String originAmapLongitude) {
        this.originAmapLongitude = originAmapLongitude;
    }

    public String getOriginAmapLatitude() {
        return originAmapLatitude;
    }

    public void setOriginAmapLatitude(String originAmapLatitude) {
        this.originAmapLatitude = originAmapLatitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationAmapLongitude() {
        return destinationAmapLongitude;
    }

    public void setDestinationAmapLongitude(String destinationAmapLongitude) {
        this.destinationAmapLongitude = destinationAmapLongitude;
    }

    public String getDestinationAmapLatitude() {
        return destinationAmapLatitude;
    }

    public void setDestinationAmapLatitude(String destinationAmapLatitude) {
        this.destinationAmapLatitude = destinationAmapLatitude;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFindMethod() {
        return findMethod;
    }

    public void setFindMethod(String findMethod) {
        this.findMethod = findMethod;
    }
}
