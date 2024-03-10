package com.bhh.booksystem.service;

import com.bhh.booksystem.model.domain.User;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.request.UserDetailRegisterRequest;
import com.bhh.booksystem.model.domain.request.UserRegisterRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
* @author 十肆
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-04-13 20:45:50
*/
public interface UserService {

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param request 设置session相关的功能
     * @return 用户信息(脱敏后)
     */
    UserWithRole userLogin(String userName, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏
     * @param originalUser
     * @return
     */
    UserWithRole getSafetyUser(UserWithRole originalUser);

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    int userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户重置密码
     * @param username 用户名
     * @param password 密码
     * @param checkPassword 确认密码
     * @param email 邮箱
     * @param code 验证码
     * @return
     */
    int userReset(String username, String password, String checkPassword, String email, String code, HttpServletRequest request);

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @param request 请求
     * @return
     */
    boolean sendEmail(String email, HttpServletRequest request);

                           /**
     * 用户注销
     *
     * @param request 请求对象
     * @return
     */
    int userLogout(HttpServletRequest request);

    boolean removeById(int id);


}
