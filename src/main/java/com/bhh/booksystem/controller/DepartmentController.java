package com.bhh.booksystem.controller;

import com.bhh.booksystem.common.BaseResponse;
import com.bhh.booksystem.common.ResultUtils;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.DepartmentMapper;
import com.bhh.booksystem.model.domain.Department;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.request.DepartmentDeleteRequest;
import com.bhh.booksystem.model.domain.request.DepartmentSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.bhh.booksystem.common.ErrorCode.NO_AUTH;
import static com.bhh.booksystem.common.ErrorCode.PARAMS_ERROR;
import static com.bhh.booksystem.constant.UserConstant.ADMIN_ROLE;
import static com.bhh.booksystem.constant.UserConstant.USER_LOGIN_STATE;

/**
 * Author:Jarvlis
 * Date:2023-11-16
 * Time:18:38
 */
@Api(tags = "部门管理模块")
@RestController
@RequestMapping("/department")
@CrossOrigin(origins = {"http://localhost:8000"}, allowCredentials = "true")
public class DepartmentController {

    @Resource
    private DepartmentMapper departmentMapper;

    @ApiOperation("获取部门列表接口")
    @PostMapping("/list")
    private BaseResponse<List<Department>> getDepartmentList(@RequestBody DepartmentSearchRequest departmentSearchRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        List<Department> departments = departmentMapper.selectDepartments(departmentSearchRequest);
        return ResultUtils.success(departments);
    }

    @ApiOperation("部门删除接口")
    @PostMapping("/delete")
    private BaseResponse<Boolean> deleteBook(@RequestBody DepartmentDeleteRequest departmentDeleteRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }

        Department department = departmentMapper.selectByPrimaryKey(departmentDeleteRequest.getDeptid());
        if (department == null) {
            return ResultUtils.error(PARAMS_ERROR, false, "单位不存在");
        }

        int res = departmentMapper.deleteByPrimaryKey(departmentDeleteRequest.getDeptid());

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("部门保存接口")
    @PostMapping("/save")
    private BaseResponse<Boolean> saveBook(@RequestBody Department department, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }

        Department dept = departmentMapper.selectByPrimaryKey(department.getDeptid());
        if (dept == null) {
            return ResultUtils.error(PARAMS_ERROR, false, "单位不存在");
        }

        int res = departmentMapper.updateByPrimaryKeySelective(department);

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("部门添加接口")
    @PostMapping("/register")
    private BaseResponse<Boolean> DepartmentRegister(@RequestBody Department department, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        int res  = departmentMapper.insertSelective(department);
        return ResultUtils.success(res == 1);
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
