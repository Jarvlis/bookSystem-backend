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
@ApiModel(value="图书删除请求类")
public class BookDeleteRequest implements Serializable {
    private static final long serialVersionUID = 2994116927375906118L;

    @ApiModelProperty(value = "图书ID", dataType = "Integer")
    private int id;

}
