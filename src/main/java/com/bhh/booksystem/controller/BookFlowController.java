package com.bhh.booksystem.controller;

import com.bhh.booksystem.common.BaseResponse;
import com.bhh.booksystem.common.ResultUtils;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.BookflowMapper;
import com.bhh.booksystem.mapper.BookinfoMapper;
import com.bhh.booksystem.mapper.DepartmentMapper;
import com.bhh.booksystem.model.domain.Bookflow;
import com.bhh.booksystem.model.domain.Bookinfo;
import com.bhh.booksystem.model.domain.Department;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.request.DepartmentDeleteRequest;
import com.bhh.booksystem.model.domain.request.DepartmentSearchRequest;
import com.bhh.booksystem.model.domain.request.FlowReturnRequest;
import com.bhh.booksystem.model.domain.request.FlowSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.bhh.booksystem.common.ErrorCode.NO_AUTH;
import static com.bhh.booksystem.common.ErrorCode.PARAMS_ERROR;
import static com.bhh.booksystem.constant.BookFlowConstant.*;
import static com.bhh.booksystem.constant.BookInfoConstant.BOOK_OUT;
import static com.bhh.booksystem.constant.BookInfoConstant.IN_LIBRARY;
import static com.bhh.booksystem.constant.UserConstant.*;

/**
 * Author:Jarvlis
 * Date:2023-11-16
 * Time:18:38
 */
@Api(tags = "图书流通接口")
@RestController
@RequestMapping("/flow")
@CrossOrigin(origins = {"http://localhost:8000"}, allowCredentials = "true")
public class BookFlowController {

    @Resource
    private BookflowMapper bookflowMapper;

    @Resource
    private BookinfoMapper bookinfoMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @ApiOperation("获取图书流通列表接口")
    @PostMapping("/list")
    private BaseResponse<List<Bookflow>> getBookflowList(HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }

        List<Bookflow> bookflows = bookflowMapper.selectDeptFlow();
        return ResultUtils.success(bookflows);
    }

    @ApiOperation("流通申请接口")
    @PostMapping("/apply")
    private BaseResponse<Boolean> saveBook(@RequestBody Bookflow bookflow, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookflow == null) {
            throw new BussinessException(PARAMS_ERROR, "提交的表单不完整");
        }
        int bookid = bookflow.getBookid();

        Bookinfo book
                = bookinfoMapper.selectByPrimaryKey(bookflow.getBookid());
        if (book.getBookstatus() != IN_LIBRARY) {
            throw new BussinessException(PARAMS_ERROR, "图书需在馆才能流通");
        }

        book.setBookstatus(BOOK_OUT);
        bookinfoMapper.updateByPrimaryKeySelective(book);

        int ownerunit = bookflow.getOwnerunit();
        int flowtounit = bookflow.getFlowtounit();
        String reason = bookflow.getReason();
        String applicant = bookflow.getApplicant();
        String contact = bookflow.getContact();
        String remarks = bookflow.getRemarks();

        if (StringUtils.isAnyBlank(reason,applicant,contact,remarks)) {
            throw new BussinessException(PARAMS_ERROR, "提交的表单不完整");
        }

        Bookinfo bookinfo = bookinfoMapper.selectByPrimaryKey(bookid);
        if (bookinfo == null) {
            throw new BussinessException(PARAMS_ERROR, "要流入的图书不存在");
        }

        Department department = departmentMapper.selectByPrimaryKey(flowtounit);
        Department department1 = departmentMapper.selectByPrimaryKey(ownerunit);
        if (department == null || department1 == null) {
            throw new BussinessException(PARAMS_ERROR, "要申请的单位或原单位不存在");
        }

        bookflow.setFlowtime(new Date());
        bookflow.setFlowstatus(0);

        int res = bookflowMapper.insertSelective(bookflow);

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("流通归还接口")
    @PostMapping("/return")
    private BaseResponse<Boolean> returnBook(@RequestBody Bookflow bookflow, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookflow == null) {
            throw new BussinessException(PARAMS_ERROR, "归还失败");
        }

        Bookinfo book
                = bookinfoMapper.selectByPrimaryKey(bookflow.getBookid());
        if (book.getBookstatus() != IN_LIBRARY) {
            throw new BussinessException(PARAMS_ERROR, "图书需在馆在进行归还");
        }

        int bookRes = 1;
        if (bookflow.getReturntime() != null) {
            bookflow.setFlowstatus(RETURNED);
            Bookinfo bookinfo = new Bookinfo();
            bookinfo.setBookstatus(IN_LIBRARY);
            bookinfo.setBookid(bookflow.getBookid());
            bookinfo.setDeptid(bookflow.getOwnerunit());
            bookRes = bookinfoMapper.updateByPrimaryKeySelective(bookinfo);
        }

        bookflow.setReturntime(new Date());

        int res = bookflowMapper.updateByPrimaryKeySelective(bookflow);

        return ResultUtils.success(res == 1 && bookRes == 1);
    }

    @ApiOperation("拒绝流通接口")
    @PostMapping("/refuse")
    private BaseResponse<Boolean> refuseBook(@RequestBody Bookflow bookflow, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookflow == null) {
            throw new BussinessException(PARAMS_ERROR, "拒绝失败");
        }

        bookflow.setReturntime(new Date());

        bookflow.setFlowstatus(REJECTED);

        int res = bookflowMapper.updateByPrimaryKeySelective(bookflow);

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("允许流通接口")
    @PostMapping("/allow")
    private BaseResponse<Boolean> allowBook(@RequestBody Bookflow bookflow, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookflow == null) {
            throw new BussinessException(PARAMS_ERROR, "同意失败");
        }

        bookflow.setFlowstatus(BORROWED);
        Bookinfo bookinfo = new Bookinfo();
        bookinfo.setBookid(bookflow.getBookid());
        bookinfo.setDeptid(bookflow.getFlowtounit());
        int bookRes = bookinfoMapper.updateByPrimaryKeySelective(bookinfo);

        int res = bookflowMapper.updateByPrimaryKeySelective(bookflow);

        return ResultUtils.success(res == 1 && bookRes == 1);
    }


    /**
     * 是否为工作人员
     * @param request
     * @return
     */
    private boolean isStaff(HttpServletRequest request){
        // 仅工作人员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserWithRole user = (UserWithRole) userObj;
        return user != null && user.getRoleName().equals(STAFF_ROLE);
    }
}
