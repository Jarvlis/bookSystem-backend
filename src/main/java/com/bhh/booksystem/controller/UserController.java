package com.bhh.booksystem.controller;

import com.bhh.booksystem.common.BaseResponse;
import com.bhh.booksystem.common.ErrorCode;
import com.bhh.booksystem.common.ResultUtils;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.UserMapper;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.request.*;
import com.bhh.booksystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.bhh.booksystem.constant.UserConstant.ADMIN_ROLE;
import static com.bhh.booksystem.constant.UserConstant.USER_LOGIN_STATE;

/**
 * Author:Jarvlis
 * Date:2023-11-16
 * Time:18:38
 */
@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://book.jarvlis.top", "http://book.jarvlis.top:8000","http://localhost:8000"}, allowCredentials = "true")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值: 混淆密码
     */
    final String SALT = "jarvlis";

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public BaseResponse<UserWithRole> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        UserWithRole user = userService.userLogin(userName, userPassword, request);
        return ResultUtils.success(user);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        int res = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(res);
    }

    @ApiOperation("用户详情注册接口")
    @PostMapping("/detailRegister")
    public BaseResponse<Integer> userDetailRegister(@RequestBody UserDetailRegisterRequest userDetailRegisterRequest) {
        if (userDetailRegisterRequest == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userDetailRegisterRequest.getUsername();
        String password = userDetailRegisterRequest.getPassword();
        String email = userDetailRegisterRequest.getEmail();
        String contactphone = userDetailRegisterRequest.getContactPhone();
        String address = userDetailRegisterRequest.getAddress();
        String realname = userDetailRegisterRequest.getRealName();

        if (StringUtils.isAnyBlank(username, password , email, contactphone, address, realname)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        boolean matchesPhone = Pattern.matches("^1[3,4,5,6,7,8,9][0-9]{9}$", contactphone);

        if(!matchesPhone) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "手机号格式错误");
        }

        boolean matchesPattern = Pattern.matches("[\\w\\.-]+@([\\w\\.-]+\\.)+[\\w\\.-]+", email);
        if(!matchesPattern){
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "邮箱格式错误");
        }


        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        userDetailRegisterRequest.setPassword(encryptPassword);

        int res = userMapper.detailRegister(userDetailRegisterRequest);
        return ResultUtils.success(res);
    }

    @ApiOperation("获取当前用户接口")
    @GetMapping("/current")
    public BaseResponse<UserWithRole> getCurrentUser(HttpServletRequest request){
        Object userobj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserWithRole currentUser = (UserWithRole) userobj;
        if(currentUser == null){
            throw new BussinessException(ErrorCode.NOT_LOGIN);
        }
        int userId = currentUser.getUserid();

        UserWithRole user = userMapper.getUserById(userId);
        UserWithRole safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @ApiOperation("用户搜索接口")
    @PostMapping("/search")
    public BaseResponse<List<UserWithRole>> searchUsers(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        List<UserWithRole> userList = userMapper.selectList(userSearchRequest);
        List<UserWithRole> collect = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }

    @ApiOperation("用户退出登录接口")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserWithRole user = (UserWithRole) userObj;
        user.setLastlogintime(new Date());
        userMapper.updateUser(user);
        int res = userService.userLogout(request);
        return ResultUtils.success(res);
    }

    @ApiOperation("删除用户接口")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request){
        int id = userDeleteRequest.getId();
        if(!isAdmin(request)){
            throw new BussinessException(ErrorCode.NO_AUTH);
        }
        if(id <= 0){
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "该用户不存在");
        }

        boolean res = userService.removeById(id);

        return ResultUtils.success(res);
    }

    @ApiOperation("用户保存接口")
    @PostMapping("/save")
    public BaseResponse<Boolean> saveUser(@RequestBody UserWithRole user, HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BussinessException(ErrorCode.NO_AUTH);
        }
        UserWithRole newUser = new UserWithRole();
        int userid = user.getUserid();
        String realname = user.getRealname();
        String contactphone = user.getContactphone();
        String email = user.getEmail();
        String address = user.getAddress();
        String workid = user.getWorkid();
        String gender = user.getGender();
        String roleName = user.getRoleName();
        String avatarpath = user.getAvatarpath();
        int accountstatus = user.getAccountstatus();
        int deptid = user.getDeptid();

        newUser.setUserid(userid);
        newUser.setRealname(realname);
        newUser.setContactphone(contactphone);
        newUser.setEmail(email);
        newUser.setAddress(address);
        newUser.setWorkid(workid);
        newUser.setRoleName(roleName);
        newUser.setGender(gender);
        newUser.setAvatarpath(avatarpath);
        newUser.setAccountstatus(accountstatus);
        newUser.setDeptid(deptid);

        int num = userMapper.updateUser(newUser);

        boolean res = num == 1;

        return ResultUtils.success(res);
    }

    /**
     * 发送邮箱验证码
     */
    @ApiOperation("发送邮箱验证码接口")
    @RequestMapping("/sendCode")
    public void sendEmailCode(HttpServletRequest request) {
        String email = request.getParameter("email");
        userService.sendEmail(email, request);
    }

    /**
     * 处理重置密码的请求
     * @param request
     * @return
     */
    @ApiOperation("重置密码接口")
    @RequestMapping("/handleReset")
    public BaseResponse<Integer> handleReset(HttpServletRequest request) {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String email = request.getParameter("email");
        String checkCode = request.getParameter("checkCode");
        String checkPassword = request.getParameter("checkPassword");

        // 校验参数非空
        if (StringUtils.isAnyBlank(username, email, checkCode, newPassword, checkPassword, email)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        // 校验密码和密码一致
        if (!newPassword.equals(checkPassword)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        // 邮箱校验码小于6位
        if (checkCode.length() > 6) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        int resetRes = userService.userReset(username, newPassword, checkPassword ,email, checkCode, request);

        // 判断用户提交的校验码是否正确
        if (resetRes == -1) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(resetRes);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserWithRole user = (UserWithRole) userObj;
        return user != null && user.getRoleName().equals(ADMIN_ROLE);
    }
}
