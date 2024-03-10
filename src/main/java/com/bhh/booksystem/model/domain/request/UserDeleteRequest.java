package com.bhh.booksystem.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-09-27
 * Time:10:31
 */
@Data
@ApiModel(value="用户删除请求类")
public class UserDeleteRequest implements Serializable {
    private static final long serialVersionUID = 2994116927375906118L;

    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private int id;

}
