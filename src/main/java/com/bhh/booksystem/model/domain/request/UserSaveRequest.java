package com.bhh.booksystem.model.domain.request;

import com.bhh.booksystem.model.domain.UserWithRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:Jarvlis
 * Date:2023-09-28
 * Time:12:44
 */
@Data
@ApiModel(value="用户保存请求类")
public class UserSaveRequest extends UserWithRole implements Serializable {

    private static final long serialVersionUID = 6296681764602726781L;

}
