package com.bhh.booksystem.mapper;

import com.bhh.booksystem.model.domain.BookBorrowVO;
import com.bhh.booksystem.model.domain.Bookborrow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 十肆
* @description 针对表【bookborrow】的数据库操作Mapper
* @createDate 2023-11-20 08:53:10
* @Entity com.bhh.booksystem.model.domain.Bookborrow
*/
@Mapper
@Repository
public interface BookborrowMapper {
    int deleteByPrimaryKey(int id);

    int insert(Bookborrow record);

    List<BookBorrowVO> selectByDeptid(int deptid);

    int insertSelective(Bookborrow record);

    Bookborrow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BookBorrowVO record);

    int updateByPrimaryKey(Bookborrow record);

    List<BookBorrowVO> selectAllinfo();

}
