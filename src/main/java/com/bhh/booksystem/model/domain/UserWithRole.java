package com.bhh.booksystem.model.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserWithRole", description="权限用户实体")
public class UserWithRole extends User implements Serializable {

    private String roleName;

    private static final long serialVersionUID = 1L;
}