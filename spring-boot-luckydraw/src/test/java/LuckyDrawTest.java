import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LuckyDrawTest {

    private static JSONObject queryResult = new JSONObject();

    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;
        private String userId;

        public Soldier(CyclicBarrier cyclic, String soldier,String userId) {
            this.soldier = soldier;
            this.cyclic = cyclic;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                //等待所有士兵到齐
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException | BrokenBarrierException e) {//在等待过程中,线程被中断
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                long startTime = System.currentTimeMillis();
                //Thread.sleep(Math.abs(new Random().nextInt() % 10000));
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("userId",userId);
                HttpResponse httpResponse = HttpUtil.createPost("http://127.0.0.1:8002/lucky/draw").form(paramMap).execute();
                JSONObject jsonObject = JSON.parseObject(httpResponse.body());
                JSONObject data = jsonObject.getJSONObject("data");
                queryResult.put(data.getString("queryId"),data.getString("queryId"));
                System.out.println(httpResponse.body());
                System.out.println((System.currentTimeMillis() - startTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(soldier + ":任务完成");
        }

    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + N + "个,任务完成!]");
            } else {
                System.out.println("司令:[士兵" + N + "个,集合完毕!]");
                flag = true;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int N = 500;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        //设置屏障点,主要为了执行这个方法
        System.out.println("集合队伍! ");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道! ");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + i,i+""));
            allSoldier[i].start();
        }


        Thread.sleep(10000);
        queryResult.keySet().forEach( v -> {
            String resultJson = HttpUtil.createGet("http://127.0.0.1:8002/lucky/draw/query/"+v).execute().body();
            System.out.println("查询结果："+resultJson);
        });
    }

}
