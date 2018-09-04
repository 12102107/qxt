package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class UsSendLetterParam extends UsSessionParam {

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1, max = 32)
    private String personName;

    @ApiModelProperty(value = "性别")
    @Length(max = 5)
    private String personSex;

    @ApiModelProperty(value = "职业")
    @Length(max = 32)
    private String uJobid;

    @ApiModelProperty(value = "工作单位")
    @Length(max = 32)
    private String uDepartid;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "身份证号码不能为空")
    @Length(min = 1, max = 20)
    private String personIdCard;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Length(min = 1, max = 32)
    private String personMobile;

    @ApiModelProperty(value = "提出者电子邮箱")
    @Length(max = 32)
    private String personEmail;

    @ApiModelProperty(value = "问题一级属地")
    @NotBlank(message = "问题一级属地不能为空")
    @Length(min = 1, max = 32)
    private String questionProvince;

    @ApiModelProperty(value = "问题二级属地")
    @NotBlank(message = "问题二级属地不能为空")
    @Length(min = 1, max = 32)
    private String questionCity;

    @ApiModelProperty(value = "问题标题")
    @NotBlank(message = "问题标题不能为空")
    @Length(min = 1, max = 60)
    private String questionTitle;

    @ApiModelProperty(value = "问题内容")
    @NotBlank(message = "问题内容不能为空")
    @Length(min = 1, max = 10000)
    private String questionContent;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSex() {
        return personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
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

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard;
    }

    public String getPersonMobile() {
        return personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getQuestionProvince() {
        return questionProvince;
    }

    public void setQuestionProvince(String questionProvince) {
        this.questionProvince = questionProvince;
    }

    public String getQuestionCity() {
        return questionCity;
    }

    public void setQuestionCity(String questionCity) {
        this.questionCity = questionCity;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
