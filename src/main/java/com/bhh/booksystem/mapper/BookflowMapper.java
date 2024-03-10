package com.bhh.booksystem.mapper;


import com.bhh.booksystem.model.domain.Bookflow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 十肆
* @description 针对表【bookflow】的数据库操作Mapper
* @createDate 2023-11-20 08:53:10
* @Entity com.bhh.booksystem.model.domain.Bookflow
*/
@Mapper
@Repository
public interface BookflowMapper {

    int deleteByPrimaryKey(int id);

    int insert(Bookflow record);

    int insertSelective(Bookflow record);

    List<Bookflow> selectDeptFlow();

    Bookflow selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Bookflow record);

    int updateByPrimaryKey(Bookflow record);

}