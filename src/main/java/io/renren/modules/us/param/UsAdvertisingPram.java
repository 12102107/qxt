package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author sys
 * @email
 * @date 2018-04-18 13:42:29
 */
public class UsAdvertisingPram extends UsBaseParam {


    @ApiModelProperty(value = "页码")
    private String pageNo;

    @ApiModelProperty(value = "每页显示条数")
    private String pageSize;


    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
