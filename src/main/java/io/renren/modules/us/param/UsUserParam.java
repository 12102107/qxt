package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;

public class UsUserParam extends UsSessionParam {

    @ApiModelProperty(value = "真实姓名")
    private String realname;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "身份证号码")
    private String citizenNo;
    @ApiModelProperty(value = "性别")
    private  String sex;
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    @ApiModelProperty(value = "地址")
    private  String address;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "职业id")
    private String uJobid;

    @ApiModelProperty(value = "工作单位id")
    private String uDepartid;




    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCitizenNo() {
        return citizenNo;
    }

    public void setCitizenNo(String citizenNo) {
        this.citizenNo = citizenNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getuJobid() {
        return uJobid;
    }

    public void setuJobid(String uJobid) {
        this.uJobid = uJobid;
    }

    public String getuDepartid() {
        return uDepartid;
    }

    public void setuDepartid(String uDepartid) {
        this.uDepartid = uDepartid;
    }
}
