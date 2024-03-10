package com.bhh.booksystem.model.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-04-20
 * Time:22:22
 */
@Data
@ApiModel(value="部门搜索请求类")
public class DepartmentSearchRequest implements Serializable {

    private static final long serialVersionUID = 7284044343178356992L;

    private String deptname;

}
