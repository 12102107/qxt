package io.renren.modules.us.param;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 *
 * @author gaoxipeng
 * @email
 * @date 2018-07-17 13:42:29
 */
public class UsCompanyProfile extends UsBaseParam{

    @ApiModelProperty(value = "类型")
    @NotBlank(message="类型不能为空")
    private String noticeType;

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }
}
