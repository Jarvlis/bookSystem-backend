package com.bhh.booksystem.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-04-17
 * Time:11:38
 */

@Data
@ApiModel(value="用户注册请求类")
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 7284044343178356992L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "确认密码")
    private String checkPassword;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "联系电话")
    private String contactphone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "真实姓名")
    private String realname;
}
