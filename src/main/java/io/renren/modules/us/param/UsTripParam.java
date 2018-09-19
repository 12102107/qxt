package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class UsTripParam extends UsSessionParam {

	 @ApiModelProperty(value = "分页页数")
	 @Length(max = 10)
	 private String pageNo;

	 @ApiModelProperty(value = "每页总条数")
	 @Length(max = 10)
	 private String pageSize;
	
    @ApiModelProperty(value = "地址类型")
    @Length(max = 10)
    private String type;

	@ApiModelProperty(value = "城市")
    @Length(max = 50)
    private String city;

    @ApiModelProperty(value = "地址名称")
    @Length(max = 50)
    private String name;

    @ApiModelProperty(value = "id")
    @Length(max = 50)
    private  String id;
    
    @ApiModelProperty(value = "经度")
    @Length(max = 50)
    private  String amap_longitude;
    
    @ApiModelProperty(value = "纬度")
    @Length(max = 50)
    private  String amap_latitude;

    @ApiModelProperty(value = "备注")
    @Length(max = 50)
    private  String remark;
     
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmap_longitude() {
		return amap_longitude;
	}

	public void setAmap_longitude(String amap_longitude) {
		this.amap_longitude = amap_longitude;
	}

	public String getAmap_latitude() {
		return amap_latitude;
	}

	public void setAmap_latitude(String amap_latitude) {
		this.amap_latitude = amap_latitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
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
