package io.renren.modules.us.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类管理
 *
 * @author sys
 * @email
 * @date 2018-04-25 14:29:10
 */
@TableName("t_s_category")
public class TSCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 图标ID
     */
    private String iconId;
    /**
     * 类型编码
     */
    private String code;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 上级ID
     */
    private String parentId;
    /**
     * 机构
     */
    private String sysOrgCode;
    /**
     * 公司
     */
    private String sysCompanyCode;
    /**
     * 父邮编
     */
    private String parentCode;
    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 获取：ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：图标ID
     */
    public String getIconId() {
        return iconId;
    }

    /**
     * 设置：图标ID
     */
    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    /**
     * 获取：类型编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置：类型编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：创建人名称
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置：创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取：创建人登录名称
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置：创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置：创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：更新人名称
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * 设置：更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 获取：更新人登录名称
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置：更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取：更新日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置：更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取：上级ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置：上级ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：机构
     */
    public String getSysOrgCode() {
        return sysOrgCode;
    }

    /**
     * 设置：机构
     */
    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    /**
     * 获取：公司
     */
    public String getSysCompanyCode() {
        return sysCompanyCode;
    }

    /**
     * 设置：公司
     */
    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }

    /**
     * 获取：父邮编
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置：父邮编
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "TSCategoryEntity{" +
                "id='" + id + '\'' +
                ", iconId='" + iconId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", createName='" + createName + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateName='" + updateName + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", parentId='" + parentId + '\'' +
                ", sysOrgCode='" + sysOrgCode + '\'' +
                ", sysCompanyCode='" + sysCompanyCode + '\'' +
                ", parentCode='" + parentCode + '\'' +
                '}';
    }
}
