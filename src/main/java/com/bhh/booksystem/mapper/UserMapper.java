package com.bhh.booksystem.mapper;

import com.bhh.booksystem.model.domain.Role;
import com.bhh.booksystem.model.domain.User;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.Userrole;
import com.bhh.booksystem.model.domain.request.UserDetailRegisterRequest;
import com.bhh.booksystem.model.domain.request.UserSearchRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 十肆
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-11-14 15:30:23
* @Entity com.bhh.booksystem.model.domain.User
*/

@Mapper
@Repository
public interface UserMapper {
    List<UserWithRole> selectUserList();

    /**
     * 删除用户
     */
    int deleteUserById(Integer id);

    /**
     * 用户是否存在
     * @param username  用户名
     * @param password 用户密码
     * @return 是否存在
     */
    UserWithRole userisExist(String username , String password);

    /**
     * 用ID查找用户
     */
    UserWithRole getUserById(int userId);

    /**
     * 查找用户数量
     */
    User getUserByUsername(String Username);

    /**
     * 保存用户
     */
    int saveUser(UserWithRole userWithRole);

    /**
     * 重置密码
     * @param Username 用户名
     * @param Password 新密码
     * @return
     */
    int resetPassword(String Username, String Password);

    /**
     * 搜索所有用户信息
     * @param userSearchRequest
     * @return
     */
    List<UserWithRole> selectList(UserSearchRequest userSearchRequest);

    /**
     * 用户详情信息注册
     * @param userDetailRegisterRequest
     * @return
     */
    int detailRegister(UserDetailRegisterRequest userDetailRegisterRequest);

    /**
     * 用户权限表注册
     */
    int userRoleRegister(Userrole userRole);

    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    @Delete("delete from `user` where UserID = #{id}")
    int removeById(int id);

    int updateUser(UserWithRole user);

}




