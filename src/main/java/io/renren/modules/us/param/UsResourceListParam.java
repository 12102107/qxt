package io.renren.modules.us.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Li
 */
public class UsResourceListParam extends UsBaseParam {
	
	
	@NotNull(message = "名称不能为空")
	private String name;
	
 

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
    public String getName() {
 		return name;
 	}

 	public void setName(String name) {
 		this.name = name;
 	}
}
