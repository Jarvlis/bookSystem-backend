package com.bhh.booksystem.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName userrole
 */
@ApiModel(value="UserRole", description="用户角色关联表实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userrole implements Serializable {

    @ApiModelProperty(name="userId", value="用户ID")
    private int userid;

    @ApiModelProperty(name="roleId", value="角色ID")
    private int roleid;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", roleid=").append(roleid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}