package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Demo3_Synchronized demo = new Demo3_Synchronized();
        int threadNum = 10;
        int loop = 10000;

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < loop; j++) {
                    demo.add();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();   //主线程等待，直到线程结束
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("zjz3", "最终count值：" + demo.getCount());
    }
}

class Demo3_Synchronized {
    private int count = 0;

    //锁实例方法，相当于是锁this，当前对象
    public synchronized void add() {
        count++;
    }

    public void addBlock() {
        //手动指定锁对象，效果同上
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}