package com.bhh.booksystem.model.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName department
 */
@ApiModel(value="部门信息", description="部门信息实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    @ApiModelProperty(name="deptId", value="部门ID")
    private Integer deptid;

    @ApiModelProperty(name="deptName", value="部门名称")
    private String deptname;

    @ApiModelProperty(name="contact", value="联系人姓名")
    private String contact;

    @ApiModelProperty(name="contactPhone", value="联系电话")
    private String contactphone;

    @ApiModelProperty(name="email", value="邮箱地址")
    private String email;

    @ApiModelProperty(name="address", value="地址信息")
    private String address;

    @ApiModelProperty(name="property", value="属性字段")
    private String property;


    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Department other = (Department) that;
        return (this.getDeptid() == null ? other.getDeptid() == null : this.getDeptid().equals(other.getDeptid()))
            && (this.getDeptname() == null ? other.getDeptname() == null : this.getDeptname().equals(other.getDeptname()))
            && (this.getContact() == null ? other.getContact() == null : this.getContact().equals(other.getContact()))
            && (this.getContactphone() == null ? other.getContactphone() == null : this.getContactphone().equals(other.getContactphone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getProperty() == null ? other.getProperty() == null : this.getProperty().equals(other.getProperty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeptid() == null) ? 0 : getDeptid().hashCode());
        result = prime * result + ((getDeptname() == null) ? 0 : getDeptname().hashCode());
        result = prime * result + ((getContact() == null) ? 0 : getContact().hashCode());
        result = prime * result + ((getContactphone() == null) ? 0 : getContactphone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deptid=").append(deptid);
        sb.append(", deptname=").append(deptname);
        sb.append(", contact=").append(contact);
        sb.append(", contactphone=").append(contactphone);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", property=").append(property);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}