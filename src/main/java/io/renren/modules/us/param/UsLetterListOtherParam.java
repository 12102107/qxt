package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

public class UsLetterListOtherParam extends UsSessionParam {

    @ApiModelProperty(value = "信件编号")
    private String letterCode;

    @ApiModelProperty(value = "身份证号码")
    private String personIdCard;


    @ApiModelProperty(value = "页码")
    private String pageNo;

    @ApiModelProperty(value = "每页显示条数")
    private String pageSize;




    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard;
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
