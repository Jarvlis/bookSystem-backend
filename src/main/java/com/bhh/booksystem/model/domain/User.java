package com.bhh.booksystem.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@ApiModel(value="User", description="用户信息实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @ApiModelProperty(name="userId", value="用户ID")
    private Integer userid;

    @ApiModelProperty(name="username", value="用户名")
    private String username;

    @ApiModelProperty(name="password", value="密码")
    private String password;

    @ApiModelProperty(name="realname", value="真实姓名")
    private String realname;

    @ApiModelProperty(name="contactPhone", value="联系方式")
    private String contactphone;

    @ApiModelProperty(name="email", value="电子邮箱")
    private String email;

    @ApiModelProperty(name="address", value="地址")
    private String address;

    @ApiModelProperty(name="workId", value="工号")
    private String workid;

    @ApiModelProperty(name="isRegistered", value="是否注册")
    private Integer isregistered;

    @ApiModelProperty(name="createTime", value="创建时间")
    private Date createtime;

    @ApiModelProperty(name="gender", value="性别")
    private String gender;

    @ApiModelProperty(name="avatarPath", value="头像路径")
    private String avatarpath;

    @ApiModelProperty(name="accountStatus", value="账户状态")
    private Integer accountstatus;

    @ApiModelProperty(name="lastLoginTime", value="最后登录时间")
    private Date lastlogintime;

    @ApiModelProperty(name="deptId", value="部门ID")
    private int deptid;

    @ApiModelProperty(name="isDeleted", value="是否已删除")
    private Integer isdeleted;

    private static final long serialVersionUID = 1L;
}