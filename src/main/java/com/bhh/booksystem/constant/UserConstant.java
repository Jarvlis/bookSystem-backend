package com.bhh.booksystem.constant;

/**
 * 用户常量
 *
 * Author:Jarvlis
 * Date:2023-05-03
 * Time:14:09
 */
public interface UserConstant {
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    // ---权限---
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "1";

    /**
     * 工作人员权限
     */
    String STAFF_ROLE = "2";

    /**
     *  读者权限
     */
    String READER_ROLE = "3";
}
