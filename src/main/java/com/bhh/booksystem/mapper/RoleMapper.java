package com.bhh.booksystem.mapper;


import com.bhh.booksystem.model.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 十肆
* @description 针对表【role】的数据库操作Mapper
* @createDate 2023-11-18 16:22:18
* @Entity com.bhh.booksystem.model.domain.Role
*/
@Mapper
@Repository
public interface RoleMapper {

    int insert(Role record);

    @Delete("delete from `role` where RoleID = #{id}")
    int removeById(int id);
}
