package com.bhh.booksystem.constant;

/**
 * Author:Jarvlis
 * Date:2023-11-22
 * Time:22:40
 */
public interface BookFlowConstant {
    // 待审核
    int WAIT_APPROVE = 0;

    // 已流入
    int BORROWED = 1;

    // 已拒绝
    int REJECTED = 2;

    // 已归还
    int RETURNED = 3;
}
