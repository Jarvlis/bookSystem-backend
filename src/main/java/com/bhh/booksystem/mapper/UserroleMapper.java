package com.bhh.booksystem.mapper;


import com.bhh.booksystem.model.domain.Userrole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author 十肆
* @description 针对表【userrole】的数据库操作Mapper
* @createDate 2023-11-18 16:22:18
* @Entity com.bhh.booksystem.model.domain.Userrole
*/
@Mapper
@Repository
public interface UserroleMapper {

    int insert(Userrole record);

    @Delete("delete from `userrole` where UserID = #{id}")
    int removeById(int id);

    @Select("select RoleID from `userrole` where UserID = #{userid}")
    int findRoleById(int userid);
}
