package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * @author Li
 */
public class UsRegisterParam extends UsBaseParam{

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message="手机号码不能为空")
    @Length(min = 1, max = 30)
    private String mobilePhone;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    @Length(min = 1, max = 100)
    private String password;

    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message="短信验证码不能为空")
    @Length(min = 1, max = 45)
    private String smsCode;
    
    @ApiModelProperty(value = "个推clientID")
    @NotBlank(message="个推clientID不能为空")
    @Length(min = 1, max = 50)
    private String client_id;

    @ApiModelProperty(value = "设备型号")
    @Length(max = 30)
    private String unitType;

    @ApiModelProperty(value = "设备厂商")
    @Length(max = 30)
    private String equipmentManufacturer;

    @ApiModelProperty(value = "屏幕分辨率")
    @Length(max = 30)
    private String screenResolution;

    @ApiModelProperty(value = "DPI")
    @Length(max = 30)
    private String dpi;

    @ApiModelProperty(value = "系统名称")
    @Length(max = 30)
    private String systemName;

    @ApiModelProperty(value = "系统版本")
    @Length(max = 30)
    private String systemVersion;

    @ApiModelProperty(value = "网络类型")
    @Length(max = 30)
    private String networkType;



    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getEquipmentManufacturer() {
        return equipmentManufacturer;
    }

    public void setEquipmentManufacturer(String equipmentManufacturer) {
        this.equipmentManufacturer = equipmentManufacturer;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

}
