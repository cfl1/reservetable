package com.reservetable.utils;/**
 * @author chenfl
 * @create 2022-05-17-14:49
 */

import cn.hutool.core.date.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenfl
 * @description
 * @date 2022/5/17 14:49
 */
@Controller
@ResponseBody
public class Test {
    @RequestMapping("/test")
    public String test() {
        System.out.println("这是一个测试接口!");
        return "测试接口,当前时间为：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS");
    }
}
