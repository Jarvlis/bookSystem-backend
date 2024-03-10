package com.bhh.booksystem.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-04-20
 * Time:22:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="图书寻找请求类")
public class BookSearchRequest implements Serializable {

    private static final long serialVersionUID = 7284044343178356992L;

    @ApiModelProperty(value = "书名")
    private String bookname;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "出版社")
    private String publisher;

    @ApiModelProperty(value = "图书ID", dataType = "Integer")
    private int bookid;

}
