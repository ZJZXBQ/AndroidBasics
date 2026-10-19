package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityTo3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_to3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int threadNums = 10;
        int loop = 10000;

        Thread[] arr = new Thread[threadNums];

        Syncnized tts = new Syncnized();
        for (int i = 0; i < threadNums; i++) {
            arr[i] = new Thread(() -> {
                for (int j = 0; j < loop; j++) {
                    tts.add();
                }
            });
            arr[i].start();
        }
        for (Thread t : arr) {
            try {
                t.join();   //主线程等待，直到线程结束
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("zjzTo3", "最终count值：" + tts.getCount());
    }
}

//---------------------------- PROCESS STARTED (13503) for package com.example.moduleone ----------------------------
//        2026-10-19 10:13:34.673 13503-13503 zjzTo3                  com.example.moduleone D  最终count值：0
/*
- 第一个 `13503`：**PID = Process ID，进程号**
你的 App 整个进程编号是 13503，一个 App 就是一个进程。
- 第二个 `13503`：**TID = Thread ID，线程号**
TID 等于 PID，代表**主线程（UI 线程）**。
//如果是子线程打印日志，会看到类似 `13503-13510`，PID 不变，TID 不一样。
- `com.example.moduleone`：Android 应用 / 模块的包名（moduleone 模块）
作用：

1. 区分不同代码类，防止类名冲突（多个项目都有 MainActivity，靠包名区分）
2. Android 系统识别 App 的标识之一；moduleone 代表这是你工程里叫 moduleone 的模块。
*/
class Syncnized {
    private int count = 0;

    public void add() {
        count++;
    }

    public int getCount() {
        return count;
    }
}