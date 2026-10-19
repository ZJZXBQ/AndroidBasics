package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Demo4_CustomThreadPool demo4CustomThreadPool = new Demo4_CustomThreadPool();
        demo4CustomThreadPool.poolTest();
    }
}

class Demo4_CustomThreadPool {
    //自定义7大参数线程池
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2,  //核心线程数
            5,  //最大线程5个
            2L, // keepAliveTime 空闲存活时间
            TimeUnit.SECONDS,   // 时间单位：秒
            new ArrayBlockingQueue<>(3),    // 阻塞队列，容量3
            Executors.defaultThreadFactory(),   // 线程工厂
            new ThreadPoolExecutor.AbortPolicy()    // 拒绝策略：抛异常（默认）
    );

    public void poolTest() {
        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            try {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zjz4", "任务:" + taskId + " 执行线程:" + Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000); // 模拟业务耗时
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Log.d("zjz4", "提交任务:" + taskId + "成功");
            } catch (RejectedExecutionException e){
                Log.d("zjz4", "任务:" + taskId + " 被拒绝！触发拒绝策略");
            }
        }
        threadPoolExecutor.shutdown();
    }
}