// package com.reservetable.utils;
//
// import cn.hutool.core.date.DateField;
// import cn.hutool.core.date.DateTime;
//
// import java.util.*;
// import java.util.concurrent.Executor;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.TimeUnit;
//
// import com.github.benmanes.caffeine.cache.Cache;
// import com.github.benmanes.caffeine.cache.Caffeine;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSenderImpl;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.scheduling.annotation.EnableAsync;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
//
// @EnableAsync
// @Component
// public class RunPy {
//
//     private static Cache<String, Map<String, Map<String, String>>> caffeine = Caffeine.newBuilder().maximumSize(1).expireAfterWrite(1, TimeUnit.HOURS).build();
//     private final static String KEY = "key";
//
//     ExecutorService executorService = Executors.newCachedThreadPool();
//
//     @Autowired
//     JavaMailSenderImpl javaMailSender;
//
//     @Scheduled(cron = "58 29 22 * * ?")
//     @Async
//     public void test1() {
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(()->{
//                 timer1("057", "18707905415");
//                 System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：057");
//             });
//         }
//     }
//
//     @Scheduled(cron = "58 29 22 * * ?")
//     @Async
//     public void test2() {
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(()->{
//                 timer1("058", "18707905415");
//                 System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：058");
//             });
//         }
//     }
//
//     @Scheduled(cron = "58 29 22 * * ?")
//     @Async
//     public void test3() {
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(()->{
//                 timer1("059", "18707905415");
//                 System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：059");
//             });
//         }
//     }
//
//     @Scheduled(cron = "58 29 22 * * ?")
//     @Async
//     public void test4() {
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(()->{
//                 timer1("060", "18707905415");
//                 System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：060");
//             });
//         }
//     }
//
//     @Scheduled(cron = "58 29 22 * * ?")
//     @Async
//     public void test5() {
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(()->{
//                 timer1("071", "18707905415");
//                 System.out.println("当前抢的线程为:" + Thread.currentThread().getName() + "抢的桌位号为：071");
//             });
//         }
//     }
//
//     @Scheduled(cron = "1 29 22 * * ?")
//     @Async
//     public void getLogin() {
//         Map<String, Map<String, String>> map = new HashMap<>();
//         DateTime now = DateTime.now();
//         now.offset(DateField.DAY_OF_MONTH, 1);
//         String day = now.toString("yyyy-MM-dd");
//         List<User> users = new ArrayList<>();
//         users.add(new User("18707905415", "zxl18707905415"));
//         users.add(new User("17770421130", "991124cwt"));
//         users.add(new User("15770547792", "liaolekun1026"));
//         users.add(new User("14796857791", "zxc1479685"));
//         users.add(new User("18070516157", "cfl611520."));
//
//         Map<String, Map<String, String>> stringStringMap = caffeine.get(KEY, (key) -> {
//             for (User user : users) {
//                 String signCookies = SeatReservation.sign(user.getUserName(), user.getUserPassword());
//                 String oaCookie = SeatReservation.getOaCookie("cb783d1327681137", signCookies);
//                 String pageToken = SeatReservation.getPageToken("cb783d1327681137", oaCookie);
//                 String token = SeatReservation.getToken("6151", day, pageToken, "cb783d1327681137", oaCookie);
//                 Map<String, String> userMap = new HashMap<>();
//                 userMap.put("oaCookie", oaCookie);
//                 userMap.put("pageToken", pageToken);
//                 userMap.put("token", token);
//                 map.put(user.getUserName(), userMap);
//             }
//             return map;
//         });
//
//         for (String s : stringStringMap.keySet()) {
//             Map<String, String> stringStringMap1 = stringStringMap.get(s);
//             for (String key : stringStringMap1.keySet()) {
//                 System.out.println("key= " + s + " and key= " + key + "value" + stringStringMap1.get(key));
//             }
//         }
//     }
//
//
//     public void timer1(String seat, String userKey) {
//         DateTime now = DateTime.now();
//         now.offset(DateField.DAY_OF_MONTH, 1);
//         String day = now.toString("yyyy-MM-dd");
//         Map<String, Map<String, String>> stringStringMap = caffeine.get(KEY, (key) -> {
//
//             return null;
//         });
//         Map<String, String> stringStringMap1 = stringStringMap.get(userKey);
//         String oaCookie = stringStringMap1.get("oaCookie");
//         String pageToken = stringStringMap1.get("pageToken");
//         String token = stringStringMap1.get("token");
//         Timer timer = new Timer();
//         timer.schedule(new TimerTask() {
//             public void run() {
//                 Boolean msg = SeatReservation.seatReservation("6151", "9", "21", day, seat, pageToken, token, "cb783d1327681137", oaCookie);
//                 if (msg) {
//                     System.out.println(userKey + "预约成功后关闭！");
//                     email(userKey + "：抢桌成功" + "时间：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
//                     timer.cancel();
//                 } else if (DateTime.now().getMinutes() == 31) {
//                     System.out.println("抢桌时间结束停止！");
//                     email(userKey + "：抢桌失败");
//                     timer.cancel();
//                 }
//             }
//         }, 0, 10);
//     }
//
//     public void email(String userString){
//         String to = "1571539116@qq.com";
//         SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//         simpleMailMessage.setSubject("抢桌提示");
//         simpleMailMessage.setText(userString);
//         simpleMailMessage.setTo(to);
//         simpleMailMessage.setFrom("1571539116@qq.com");
//         javaMailSender.send(simpleMailMessage);
//     }
//
// }
