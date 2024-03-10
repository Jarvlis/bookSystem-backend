package com.bhh.booksystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Author:Jarvlis
 * Date:2023-11-16
 * Time:13:47
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class mailTest {

    @Resource
    private JavaMailSender javaMailSender;

    @Test
    public void send(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("986653802@qq.com");
        //邮件主题
        mailMessage.setSubject("这是一封测试邮件");
        //邮件内容：普通文件无法解析html标签
        mailMessage.setText("你的验证码是: xxxxx");
        //收件人
        mailMessage.setTo("BHHbhh14@bjfu.edu.cn");
        //发送邮件
        javaMailSender.send(mailMessage);
    }
}