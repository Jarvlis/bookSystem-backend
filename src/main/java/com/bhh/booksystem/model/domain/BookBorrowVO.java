package com.bhh.booksystem.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-11-23
 * Time:16:44
 */
@Data
@ApiModel(value="图书借阅信息VO", description="返回给前端的VO")
public class BookBorrowVO extends Bookborrow implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value ="部门ID")
    private int deptid;
}
