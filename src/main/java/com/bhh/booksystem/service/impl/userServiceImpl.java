package com.bhh.booksystem.service.impl;

import com.bhh.booksystem.common.ErrorCode;
import com.bhh.booksystem.constant.UserConstant;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.RoleMapper;
import com.bhh.booksystem.mapper.UserMapper;
import com.bhh.booksystem.mapper.UserroleMapper;
import com.bhh.booksystem.model.domain.User;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.Userrole;
import com.bhh.booksystem.model.domain.request.UserRegisterRequest;
import com.bhh.booksystem.service.UserService;
import com.bhh.booksystem.utils.RandomGenerate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bhh.booksystem.constant.UserConstant.READER_ROLE;
import static com.bhh.booksystem.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户服务实现类
 *
 * @author 十肆
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-04-13 20:45:50
 */
@Service
@Slf4j
public class userServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JavaMailSender emailSender;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserroleMapper userRoleMapper;

    /**
     * 盐值: 混淆密码
     */
    final String SALT = "jarvlis";

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return 注册结果
     */
    @Override
    public int userRegister(UserRegisterRequest userRegisterRequest) {

        String username = userRegisterRequest.getUsername();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String password = userRegisterRequest.getPassword();
        String email = userRegisterRequest.getEmail();
        String contactphone = userRegisterRequest.getContactphone();
        String address = userRegisterRequest.getAddress();
        String realname = userRegisterRequest.getRealname();

        // 1.校验
        if (StringUtils.isAnyBlank(username, password, checkPassword, email, contactphone, address, realname)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "账号过短");
        }
        if (password.length() < 3){
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }

        boolean matchesPhone = Pattern.matches("^1[3,4,5,6,7,8,9][0-9]{9}$", contactphone);

        if(!matchesPhone) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "手机号格式错误");
        }

        boolean matchesPattern = Pattern.matches("[\\w\\.-]+@([\\w\\.-]+\\.)+[\\w\\.-]+", email);
        if(!matchesPattern){
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "邮箱格式错误");
        }

        if (!password.equals(checkPassword)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "密码和校验密码不相等");
        }

        // 账户不包含特殊字符
        String vaildPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(vaildPattern).matcher(username);
        if (matcher.find()) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符");
        }

        // 账户不重复
        User resUser = userMapper.getUserByUsername(username);
        if (resUser != null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "账户重复");
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        // 3.插入用户表数据
        UserWithRole user = new UserWithRole();
        user.setUsername(username);
        user.setPassword(encryptPassword);
        user.setEmail(email);
        user.setContactphone(contactphone);
        user.setAddress(address);
        user.setRealname(realname);

        int saveResult = userMapper.saveUser(user);
        if (saveResult != 1) {
            return -1;
        }

        // 5. 插入用户权限表数据
        Userrole userrole = new Userrole();
        userrole.setRoleid(Integer.parseInt(UserConstant.READER_ROLE));
        userrole.setUserid(user.getUserid());

        int userRoleResult = userRoleMapper.insert(userrole);

        if (userRoleResult <= 0) {
            return -1;
        }
        return user.getUserid();
    }

    public void sendPasswordResetEmail(String to, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("986653802@qq.com");
        //邮件主题
        mailMessage.setSubject("图书馆信息平台重置密码请求");
        //邮件内容：普通文件无法解析html标签
        mailMessage.setText("请在五分钟内输入验证码, 超时请重发。验证码为: " + code);
        //收件人
        mailMessage.setTo(to);
        //发送邮件
        emailSender.send(mailMessage);
    }

    @Override
    public boolean sendEmail(String email, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(5 * 60);
        String randomCode = RandomGenerate.generateRandomCode();
        session.setAttribute("checkCode", randomCode);
        sendPasswordResetEmail(email, randomCode);
        return true;
    }

    @Override
    public int userReset(String username, String password, String checkPassword, String email, String code, HttpServletRequest request) {
        // 判断该用户名的用户是否存在
        User user = userMapper.getUserByUsername(username);
        if (user == null || !user.getEmail().equals(email)) {
            return -1;
        }

        // 校验验证码是否过期,是否一样
        HttpSession session = request.getSession(false);

        if(session == null) {
            return -1;
        }

        String checkCode = (String) session.getAttribute("checkCode");

        if (StringUtils.isBlank(checkCode) || !checkCode.equals(code)) {
            return -1;
        }

        // 如果验证成功，可以在此处移除会话中的验证码
        session.removeAttribute("checkCode");

        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        int resetResult = userMapper.resetPassword(username, encryptPassword);
        return resetResult;
    }

    @Override
    public UserWithRole userLogin(String username, String password, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "选项为空");
        }
        if (username.length() < 3) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户名小于3位");
        }
        if (password.length() < 3) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "密码小于3位");
        }

        // 账户不包含特殊字符
        String vaildPattern = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(vaildPattern).matcher(password);
        if (matcher.find()) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符");
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在

        UserWithRole user = userMapper.userisExist(username, encryptPassword);

        // 用户不存在
        if(user == null) {
            log.info("user login failed,account can't match password");
            throw new BussinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        // 3.用户脱敏
        UserWithRole safetyUser = getSafetyUser(user);
        // 4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originalUser
     * @return
     */
    public UserWithRole getSafetyUser(UserWithRole originalUser) {
        if (originalUser == null) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        UserWithRole safetyUser = new UserWithRole();
        safetyUser.setUserid(originalUser.getUserid());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setRealname(originalUser.getRealname());
        safetyUser.setContactphone(originalUser.getContactphone());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setAddress(originalUser.getAddress());
        safetyUser.setWorkid(originalUser.getWorkid());
        safetyUser.setIsregistered(originalUser.getIsregistered());
        safetyUser.setCreatetime(originalUser.getCreatetime());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setAvatarpath(originalUser.getAvatarpath());
        safetyUser.setAccountstatus(originalUser.getAccountstatus());
        safetyUser.setLastlogintime(originalUser.getLastlogintime());
        safetyUser.setRoleName(originalUser.getRoleName());
        safetyUser.setDeptid(originalUser.getDeptid());
        return safetyUser;
    }
    
    @Override
    public boolean removeById(int userId) {
        int roleId = userRoleMapper.findRoleById(userId);
        int res1 = userRoleMapper.removeById(userId);
        int res2 = userMapper.removeById(userId);
        int res3 = roleMapper.removeById(roleId);
        if (res1 != 1 || res2 != 1 || res3 != 1) {
            return false;
        }
        return true;
    }

    /**
     * 用户注销
     * @param request 请求对象
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




