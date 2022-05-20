//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.reservetable.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeatReservation {
    public static final String DEPTIDENC = "cb783d1327681137";
    public static final String ROOMID = "6151";
    public static final String START_TIME = "9";
    public static final String END_TIME = "21";

    public SeatReservation() {
    }

    public static String sign(String username, String password) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("fid", "-1");
        paramMap.put("uname", username);
        paramMap.put("password", new String(Base64.getEncoder().encode(password.getBytes())));
        paramMap.put("t", "true");
        paramMap.put("refer", "http%253A%252F%252Fi.chaoxing.com");
        String url = "https://passport2.chaoxing.com/fanyalogin";
        HttpResponse response = HttpRequest.post(url).header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.HOST, "passport2.chaoxing.com")
                .header(Header.ORIGIN, "https://passport2.chaoxing.com")
                .header(Header.REFERER, "https://passport2.chaoxing.com/login?fid=&newversion=true&refer=http%3A%2F%2Fi.chaoxing.com")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 Edg/86.0.622.68")
                .form(paramMap).execute();
        List<HttpCookie> cookies = response.getCookies();
        StringBuilder signCookies = new StringBuilder();
        Iterator var7 = cookies.iterator();

        while (var7.hasNext()) {
            HttpCookie cookie = (HttpCookie) var7.next();
            signCookies.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }

        return signCookies.toString();
    }

    public static String getOaCookie(String deptIdEnc, String signCookies) {
        String url = "https://office.chaoxing.com/front/third/apps/seat/list?deptIdEnc=" + deptIdEnc;
        HttpResponse response = HttpRequest.get(url).header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.COOKIE, signCookies)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.HOST, "office.chaoxing.com")
                .header(Header.REFERER, url)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 Edg/86.0.622.68")
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        StringBuilder oACookies = new StringBuilder();
        Iterator var6 = cookies.iterator();

        while (var6.hasNext()) {
            HttpCookie cookie = (HttpCookie) var6.next();
            oACookies.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }

        return oACookies.toString();
    }

    public static String getPageToken(String deptIdEnc, String oaCookies) {
        String url = "https://office.chaoxing.com/front/third/apps/seat/list?deptIdEnc=" + deptIdEnc;
        HttpResponse response = HttpRequest.get(url)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.COOKIE, oaCookies)
                .header(Header.HOST, "office.chaoxing.com")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 Edg/86.0.622.68")
                .execute();
        String content = response.body();
        int startIndex = content.indexOf("'&pageToken='") + 17;
        int endIndex = content.indexOf("'&fidEnc='") - 4;
        return content.substring(startIndex, endIndex);
    }

    public static String getToken(String roomId, String day, String pageToken, String deptIdEnc, String oaCookies) {
        String url = String.format("https://office.chaoxing.com/front/third/apps/seat/select?id=%s&day=%s&backLevel=2&pageToken=%s&fidEnc=%s", roomId, day, pageToken, deptIdEnc);
        HttpResponse response = HttpRequest.get(url)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br").header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.COOKIE, oaCookies)
                .header(Header.HOST, "office.chaoxing.com")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 Edg/86.0.622.68")
                .execute();
        String content = response.body();
        int startIndex = content.indexOf("token: ") + 8;
        int endIndex = startIndex + 32;
        return content.substring(startIndex, endIndex);
    }

    public static Boolean seatReservation(String roomId, String startTime, String endTime, String day, String seatNum, String pageToken, String token, String deptIdEnc, String oaCookies) {
        HashMap<String, Object> paramMap = new HashMap();
        String url = "https://office.chaoxing.com/data/apps/seat/submit";
        paramMap.put("roomId", roomId);
        paramMap.put("startTime", startTime + "%3A00");
        paramMap.put("endTime", endTime + "%3A00");
        paramMap.put("day", day);
        paramMap.put("seatNum", seatNum);
        paramMap.put("captcha", "");
        paramMap.put("token", token);
        HttpResponse response = HttpRequest.get(url).header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.COOKIE, oaCookies)
                .header(Header.HOST, "office.chaoxing.com")
                .header(Header.REFERER, String.format("https://office.chaoxing.com/front/third/apps/seat/select?id=%s&day=%s&backLevel=2&pageToken=%s&fidEnc=%s", roomId, day, pageToken, deptIdEnc))
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36 Edg/86.0.622.68")
                .form(paramMap)
                .execute();
        String content = response.body();
        JSONObject json = JSONUtil.parseObj(content);
        System.out.println("线程：" + Thread.currentThread().getName() + "结果：" + json);
        Boolean success = (Boolean) json.get("success");
        return success;
    }

    public static void main(String[] args) {
        // DateTime now = DateTime.now();
        // now.offset(DateField.DAY_OF_MONTH, 1);
        // String day = now.toString("yyyy-MM-dd");
        // String signCookies = SeatReservation.sign("18070516157", "cfl611520.");
        // System.out.println("signCookies" + signCookies);
        // String oaCookie = SeatReservation.getOaCookie("cb783d1327681137", signCookies);
        // System.out.println("oaCookie" + oaCookie);
        // String pageToken = SeatReservation.getPageToken("cb783d1327681137", oaCookie);
        // System.out.println("pageToken" + pageToken);
        // String token = SeatReservation.getToken("6151", day, pageToken, "cb783d1327681137", oaCookie);
        // System.out.println("token" + token);
        // System.out.println("时间：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
    //     ExecutorService executorService = Executors.newCachedThreadPool();
    //     for (int i = 0; i < 100; i++) {
    //         executorService.execute(() -> {
    //             Timer timer = new Timer();
    //             timer.schedule(new TimerTask() {
    //                 public void run() {
    //                     System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：071");
    //                 }
    //             }, 0, 10);
    //         });
    //     }
        Integer integer = new Integer(1);
        int i = 1;
        Integer j = 1;
        System.out.println(integer==i);
        System.out.println(integer==j);
        System.out.println(i==j);
    }
}
