package com.bhh.booksystem.controller;

import com.bhh.booksystem.common.BaseResponse;
import com.bhh.booksystem.common.ResultUtils;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.BookborrowMapper;
import com.bhh.booksystem.mapper.BookinfoMapper;
import com.bhh.booksystem.mapper.DepartmentMapper;
import com.bhh.booksystem.mapper.UserMapper;
import com.bhh.booksystem.model.domain.*;
import com.bhh.booksystem.model.domain.request.BookDeleteRequest;
import com.bhh.booksystem.model.domain.request.BookSearchRequest;
import com.bhh.booksystem.model.domain.request.FlowSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
@Api(tags = "图书借阅模块")
@RestController
@RequestMapping("/borrow")
@CrossOrigin(origins = {"http://book.jarvlis.top:8000","http://book.jarvlis.top", "http://localhost:8000"}, allowCredentials = "true")
public class BookBorrowController {

    @Resource
    private BookborrowMapper bookborrowMapper;

    @Resource
    private BookinfoMapper bookinfoMapper;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/list")
    @ApiOperation("获取指定部门借阅列表接口")
    private BaseResponse<List<BookBorrowVO>> getBookBorrowList(@RequestBody FlowSearchRequest flowSearchRequest, HttpServletRequest request) {

        List<BookBorrowVO> bookBorrows = bookborrowMapper.selectByDeptid(flowSearchRequest.getDeptid());
        return ResultUtils.success(bookBorrows);
    }



    @GetMapping("/readerlist")
    @ApiOperation("获取全部借阅列表接口")
    private BaseResponse<List<BookBorrowVO>> getAllBookList() {

        List<BookBorrowVO> bookBorrows = bookborrowMapper.selectAllinfo();
        return ResultUtils.success(bookBorrows);
    }

    @PostMapping("/apply")
    @ApiOperation("借阅申请接口")
    private BaseResponse<Boolean> applyBook(@RequestBody BookBorrowVO bookBorrowVO, HttpServletRequest request) {
        if (bookBorrowVO == null) {
            throw new BussinessException(PARAMS_ERROR, "提交的表单不完整");
        }

        int bookid = bookBorrowVO.getBookid();
        int userid = bookBorrowVO.getUserid();
        String reason = bookBorrowVO.getReason();
        String contact = bookBorrowVO.getContact();
        String remarks = bookBorrowVO.getRemarks();

        if (StringUtils.isAnyBlank(reason,contact,remarks)) {
            throw new BussinessException(PARAMS_ERROR, "提交的表单不完整");
        }

        UserWithRole user = userMapper.getUserById(userid);
        if (user == null) {
            throw new BussinessException(PARAMS_ERROR, "借阅用户未注册");
        }
        Bookinfo bookinfo = bookinfoMapper.selectByPrimaryKey(bookid);
        if (bookinfo == null) {
            throw new BussinessException(PARAMS_ERROR, "要借阅的图书不存在");
        }

        bookinfo.setBookstatus(BOOK_OUT);
        bookinfoMapper.updateByPrimaryKeySelective(bookinfo);

        bookBorrowVO.setBorrowtime(new Date());
        bookBorrowVO.setBorrowstatus(WAIT_APPROVE);
        int res = bookborrowMapper.insertSelective(bookBorrowVO);

        return ResultUtils.success(res == 1);
    }

    @PostMapping("/return")
    @ApiOperation("借阅归还接口")
    private BaseResponse<Boolean> returnBook(@RequestBody BookBorrowVO bookBorrowVO, HttpServletRequest request) {
        boolean isUser = !isStaff(request);

        if (bookBorrowVO == null) {
            throw new BussinessException(PARAMS_ERROR, "归还失败");
        }

        int bookRes = 1;
        if (!isUser && bookBorrowVO.getReturntime() != null) {
            bookBorrowVO.setBorrowstatus(RETURNED);
            Bookinfo bookinfo = new Bookinfo();
            bookinfo.setBookstatus(IN_LIBRARY);
            bookinfo.setBookid(bookBorrowVO.getBookid());
            bookRes = bookinfoMapper.updateByPrimaryKeySelective(bookinfo);
        }

        if (isUser) {
            bookBorrowVO.setReturntime(new Date());
        }

        int res = bookborrowMapper.updateByPrimaryKeySelective(bookBorrowVO);

        return ResultUtils.success(res == 1 && bookRes == 1);
    }

    @PostMapping("/refuse")
    @ApiOperation("拒绝借阅接口")
    private BaseResponse<Boolean> refuseBook(@RequestBody BookBorrowVO bookBorrowVO, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookBorrowVO == null) {
            throw new BussinessException(PARAMS_ERROR, "拒绝失败");
        }

        Bookinfo bookinfo = new Bookinfo();
        bookinfo.setBookstatus(IN_LIBRARY);
        bookinfo.setBookid(bookBorrowVO.getBookid());
        bookinfoMapper.updateByPrimaryKeySelective(bookinfo);

        bookBorrowVO.setReturntime(new Date());
        bookBorrowVO.setBorrowstatus(REJECTED);

        int res = bookborrowMapper.updateByPrimaryKeySelective(bookBorrowVO);

        return ResultUtils.success(res == 1);
    }

    @PostMapping("/allow")
    @ApiOperation("允许借阅接口")
    private BaseResponse<Boolean> allowBook(@RequestBody BookBorrowVO bookBorrowVO, HttpServletRequest request) {
        if (!isStaff(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        if (bookBorrowVO == null) {
            throw new BussinessException(PARAMS_ERROR, "同意失败");
        }

        bookBorrowVO.setBorrowstatus(BORROWED);
        Bookinfo bookinfo = new Bookinfo();
        bookinfo.setBookstatus(BOOK_OUT);
        bookinfo.setBookid(bookBorrowVO.getBookid());
        int bookRes = bookinfoMapper.updateByPrimaryKeySelective(bookinfo);
        int borrowRes = bookborrowMapper.updateByPrimaryKeySelective(bookBorrowVO);

        return ResultUtils.success(bookRes == 1 && borrowRes == 1);
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
