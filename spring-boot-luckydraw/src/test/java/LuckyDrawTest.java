import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import event.EventListener;
import event.EventObject;
import event.EventSource;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class LuckyDrawTest {

    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;
        private String userId;
        private EventSource eventSource;

        public Soldier(CyclicBarrier cyclic, String soldier,String userId,EventSource eventSource) {
            this.soldier = soldier;
            this.cyclic = cyclic;
            this.userId = userId;
            this.eventSource = eventSource;
        }

        @Override
        public void run() {
            try {
                //等待所有士兵到齐
                cyclic.await();
                doWork(eventSource);
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException | BrokenBarrierException e) {//在等待过程中,线程被中断
                e.printStackTrace();
            }
        }

        void doWork(EventSource eventSource) {
            try {
                HttpResponse httpResponse = HttpUtil.createGet("http://127.0.0.1:8002/lucky/draw/"+userId).execute();
                JSONObject jsonObject = JSON.parseObject(httpResponse.body());
                JSONObject data = jsonObject.getJSONObject("data");

                eventSource.addListener(new EventListener() {
                    @Override
                    public void handleEvent(EventObject dm) {
                        String resultJson = HttpUtil.createGet("http://127.0.0.1:8002/lucky/draw/query/"+data.getString("queryId")).execute().body();
                        System.out.println("查询结果："+resultJson);
                    }
                });

                eventSource.notifyEvent();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info(soldier + ":任务完成");
        }

    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int number;

        public BarrierRun(boolean flag, int number) {
            this.flag = flag;
            this.number = number;
        }

        @Override
        public void run() {
            if (flag) {
                log.info("司令:[士兵" + number + "个,任务完成!]");
            } else {
                log.info("司令:[士兵" + number + "个,集合完毕!]");
                flag = true;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        //定义事件源
        EventSource eventSource = new EventSource();

        final int number = 1000;
        Thread[] allSoldier = new Thread[number];
        CyclicBarrier cyclic = new CyclicBarrier(number, new BarrierRun(false, number));
        //设置屏障点,主要为了执行这个方法
        log.info("集合队伍! ");
        for (int i = 0; i < number; i++) {
            log.info("士兵" + i + "报道! ");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + i,i+"",eventSource));
            allSoldier[i].start();
        }

        Thread.sleep(100000);

    }

}
