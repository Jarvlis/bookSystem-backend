package com.bhh.booksystem.mapper;

import com.bhh.booksystem.model.domain.Department;
import com.bhh.booksystem.model.domain.request.DepartmentSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {

    List<Department> selectDepartments(DepartmentSearchRequest departmentSearchRequest);

    Department selectByPrimaryKey(int deptid);

    int deleteByPrimaryKey(int deptid);

    int insertSelective(Department department);

    int updateByPrimaryKeySelective(Department department);
}