package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

public class UsLetterDetailParam extends UsSessionParam {

    @ApiModelProperty(value = "信件编号")
    private String letterCode;

    @ApiModelProperty(value = "信件ID")
    private String letterId;


    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public String getLetterId() {
        return letterId;
    }

    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }
}
