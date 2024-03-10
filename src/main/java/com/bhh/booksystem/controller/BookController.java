package com.bhh.booksystem.controller;

import com.bhh.booksystem.common.BaseResponse;
import com.bhh.booksystem.common.ResultUtils;
import com.bhh.booksystem.exception.BussinessException;
import com.bhh.booksystem.mapper.BookinfoMapper;
import com.bhh.booksystem.model.domain.Bookinfo;
import com.bhh.booksystem.model.domain.UserWithRole;
import com.bhh.booksystem.model.domain.request.BookDeleteRequest;
import com.bhh.booksystem.model.domain.request.BookSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import static com.bhh.booksystem.constant.UserConstant.*;

/**
 * Author:Jarvlis
 * Date:2023-11-16
 * Time:18:38
 */
@Api(tags = "图书信息模块")
@RestController
@RequestMapping("/book")
@CrossOrigin(origins = {"http://localhost:8000"}, allowCredentials = "true")
public class BookController {

    @Resource
    private BookinfoMapper bookinfoMapper;

    @ApiOperation("获取图书列表接口")
    @PostMapping("/list")
    public BaseResponse<List<Bookinfo>> getBookList(@RequestBody BookSearchRequest bookSearchRequest) {
        List<Bookinfo> bookinfos = bookinfoMapper.selectAllInfo(bookSearchRequest);

        return ResultUtils.success(bookinfos);
    }

    @ApiOperation("图书删除接口")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBook(@RequestBody BookDeleteRequest bookDeleteRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        Bookinfo bookinfo = bookinfoMapper.selectByPrimaryKey(bookDeleteRequest.getId());
        if (bookinfo == null || bookinfo.getBookstatus() == 1) {
            return ResultUtils.error(PARAMS_ERROR, false, "图书不存在或被借阅");
        }

        int res = bookinfoMapper.deleteByPrimaryKey(bookDeleteRequest.getId());

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("图书保存接口")
    @PostMapping("/save")
    public BaseResponse<Boolean> saveBook(@RequestBody Bookinfo bookinfo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }
        Bookinfo book = bookinfoMapper.selectByPrimaryKey(bookinfo.getBookid());
        if (book == null) {
            return ResultUtils.error(PARAMS_ERROR, false, "图书不存在");
        }

        int res = bookinfoMapper.updateByPrimaryKeySelective(bookinfo);

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("图书注册接口")
    @PostMapping("/register")
    public BaseResponse<Boolean> bookRegister(@RequestBody Bookinfo bookinfo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BussinessException(NO_AUTH, "无权限");
        }

        int res = bookinfoMapper.insertSelective(bookinfo);

        return ResultUtils.success(res == 1);
    }

    @ApiOperation("图书上传接口")
    @PostMapping("/upload")
    public BaseResponse<Boolean> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            List<Bookinfo> bookinfos = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Bookinfo bookinfo = new Bookinfo();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    if (cellIndex == 0) {
                        bookinfo.setBookname(currentCell.getStringCellValue());
                    } else if (cellIndex == 1) {
                        bookinfo.setAuthor(currentCell.getStringCellValue());
                    } else if (cellIndex == 2) {
                        bookinfo.setCategory(currentCell.getStringCellValue());
                    } else if (cellIndex == 3) {
                        bookinfo.setPages((int) currentCell.getNumericCellValue());
                    } else if (cellIndex == 4) {
                        bookinfo.setPicturepath(currentCell.getStringCellValue());
                    } else if (cellIndex == 5) {
                        bookinfo.setPrice((float) currentCell.getNumericCellValue());
                    } else if (cellIndex == 6) {
                        bookinfo.setPublisher(currentCell.getStringCellValue());
                    } else if (cellIndex == 7) {
                        bookinfo.setDeptid((int)currentCell.getNumericCellValue());
                    } else if (cellIndex == 8) {
                        try {
                            Date date = formatter.parse(currentCell.getStringCellValue());
                            bookinfo.setPublishtime(date);
                        } catch (ParseException e) {
                            throw new BussinessException(PARAMS_ERROR, "传入的日期参数格式有误");
                        }
                    }
                    cellIndex++;
                }
                bookinfos.add(bookinfo);
            }
            workbook.close();
            // Your code here (e.g. save to database).

            for (Bookinfo bookinfo : bookinfos) {
                bookinfoMapper.insertSelective(bookinfo);
            }
            return ResultUtils.success(true);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            return ResultUtils.error(PARAMS_ERROR, "上传文件格式错误");
        }
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserWithRole user = (UserWithRole) userObj;
        return user != null && (user.getRoleName().equals(ADMIN_ROLE) || user.getRoleName().equals(STAFF_ROLE));
    }
}
