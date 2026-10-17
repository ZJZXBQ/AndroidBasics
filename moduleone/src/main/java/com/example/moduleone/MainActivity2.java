package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ThreadUnsafeDemo tt = new ThreadUnsafeDemo();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30000; i++) {
                    // count++ 不是原子操作！
                    // 底层分为3步：
                    // 1. 读取count的值
                    // 2. 执行 +1
                    // 3. 把新值写回count
                    synchronized (tt){//对象锁，同一个对象用一把
                        count++;
                    }
                }
            }
        };

        Thread t1 = new Thread(task, "线程A");
        Thread t2 = new Thread(task, "线程B");

        t1.start();//启动线程
        t2.start();

        try {
            t1.join();//主线程等待，t1线程执行完过后再继续
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Log.d("zjz2", "最终count值 = " + count);
    }
}

class ThreadUnsafeDemo {

}