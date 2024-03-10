package com.bhh.booksystem.utils;

import java.util.Random;

/**
 * Author:Jarvlis
 * Date:2023-11-17
 * Time:19:13
 */
public class RandomGenerate {

    public static String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        int length = 6;

        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // 使用nextInt(10)生成[0,9]范围内的整数
        }

        return code.toString();
    }
}
