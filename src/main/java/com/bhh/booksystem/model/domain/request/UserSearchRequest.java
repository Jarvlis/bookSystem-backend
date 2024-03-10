package com.bhh.booksystem.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-04-20
 * Time:22:22
 */
@Data
@ApiModel(value="图书搜索请求类")
public class UserSearchRequest implements Serializable {

    private static final long serialVersionUID = 7284044343178356992L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户ID")
    private String userid;

}
