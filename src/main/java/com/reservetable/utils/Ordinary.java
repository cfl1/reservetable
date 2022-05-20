package com.reservetable.utils;/**
 * @author chenfl
 * @create 2022-05-17-14:00
 */

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenfl
 * @description
 * @date 2022/5/17 14:00
 */
@EnableAsync
@Component
public class Ordinary {

    ExecutorService executorService = Executors.newCachedThreadPool();
    private static boolean isSuccess = true;

    @Scheduled(cron = "59 29 22 * * ?")
    @Async
    public void test1() {
        exec("060");
    }

    @Scheduled(cron = "56 29 22 * * ?")
    @Async
    public void test2() {
        exec("060");
    }

    public void exec(String seat) {
        for (int i = 0; i < 15; i++) {
            if (!isSuccess) {
                executorService.shutdownNow();
                return;
            }
            executorService.execute(() -> {
                System.out.println("开启了一个线程：" + Thread.currentThread().getName());
                while (isSuccess) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "   正在抢座");
                    boolean msg = false;
                    try {
                        msg = get(seat);
                    } catch (Exception e) {
                        System.out.println("异常了！！！！！！");
                        e.printStackTrace();
                    } finally {

                    }

                    if (msg) {
                        System.out.println("15970147017预约成功后关闭！");
                        sendInfo("15970147017：抢桌成功" + "时间：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
                        executorService.shutdown();
                        executorService.shutdownNow();
                        isSuccess = false;
                        return;
                    } else if (DateTime.now().getField(Calendar.MINUTE) == 30 && DateTime.now().getField(Calendar.SECOND) > 30) {
                        System.out.println("抢桌时间结束停止！");
                        sendInfo("15970147017抢桌失败");
                        executorService.shutdown();
                        executorService.shutdownNow();
                        isSuccess = false;
                        return;
                    }
                }
                // Timer timer = new Timer();
                // timer.schedule(new TimerTask() {
                //     public void run() {
                //         boolean msg = get("060");
                //         System.out.println("线程："+Thread.currentThread().getName()+"   正在抢座:" + msg);
                //         if (msg) {
                //             System.out.println("15970147017预约成功后关闭！");
                //             sendInfo("15970147017：抢桌成功" + "时间：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
                //             executorService.shutdownNow();
                //             timer.cancel();
                //         } else if (DateTime.now().getMinutes() == 31) {
                //             System.out.println("抢桌时间结束停止！");
                //             sendInfo("15970147017抢桌失败");
                //             executorService.shutdownNow();
                //             timer.cancel();
                //         }
                //     }
                // }, 0, 100);
            });
        }
    }

    public boolean get(String seat) {
        //获取登录信息
        DateTime now = DateTime.now();
        now.offset(DateField.DAY_OF_MONTH, 1);
        String day = now.toString("yyyy-MM-dd");
        String signCookies = SeatReservation.sign("15970147017", "cfl611520.");
        String oaCookie = SeatReservation.getOaCookie("cb783d1327681137", signCookies);
        String pageToken = SeatReservation.getPageToken("cb783d1327681137", oaCookie);
        String token = SeatReservation.getToken("6151", day, pageToken, "cb783d1327681137", oaCookie);
        //抢
        Boolean msg = SeatReservation.seatReservation("6151", "9", "21", day, seat, pageToken, token, "cb783d1327681137", oaCookie);
        return msg;
    }

    /**
     * @Author chenfl
     * @Description //推送消息
     * @Date 10:11 2022/5/7
     * @Param []
     * @return void
     **/
    public static void sendInfo(String info) {
        String url = "https://api.day.app/3d3zQdeGpwpqcqMnf6CP3H/" + info;
        HttpResponse execute = HttpRequest.get(url).execute();
    }
}
