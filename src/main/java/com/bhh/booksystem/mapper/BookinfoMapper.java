package com.bhh.booksystem.mapper;


import com.bhh.booksystem.model.domain.Bookinfo;
import com.bhh.booksystem.model.domain.request.BookSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 十肆
* @description 针对表【bookinfo】的数据库操作Mapper
* @createDate 2023-11-20 08:53:10
* @Entity generator.domain.Bookinfo
*/
@Mapper
@Repository
public interface BookinfoMapper {

    List<Bookinfo> selectAllInfo(BookSearchRequest bookSearchRequest);

    int deleteByPrimaryKey(Integer bookid);

    int updateByPrimaryKeySelective(Bookinfo record);

    Bookinfo selectByPrimaryKey(Integer bookid);

    int insertSelective(Bookinfo record);

    Bookinfo selectByName(String bookname);
}
