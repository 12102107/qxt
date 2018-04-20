package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

public class UsLetterListParam extends UsSessionParam {




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
