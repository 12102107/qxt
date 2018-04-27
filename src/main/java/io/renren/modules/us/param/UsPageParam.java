package io.renren.modules.us.param;

import javax.validation.constraints.NotNull;

/**
 * @author Li
 */
public class UsPageParam extends UsBaseParam {

    @NotNull(message = "pageNo不能为空")
    private Integer pageNo;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
