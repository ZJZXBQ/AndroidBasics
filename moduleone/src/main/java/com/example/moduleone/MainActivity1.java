package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main1);

        AnimalManager manager = new AnimalManager();
        manager.setCallback(new AnimalCallback() {
            @Override
            public void onAnimalEvent(String msg) {
                Log.d("zjz1","匿名回调收到消息：" + msg);
            }
        });

        // ========== 案例1：普通子类Cat（继承抽象类）==========
        Animal cat = new Cat("小花猫");
        manager.startAnimalWork(cat);

        // ========== 案例2：匿名内部类直接new抽象类Animal【重点练习！】==========
        // 没有单独写类，直接new抽象类，重写抽象方法，一次性使用
        Animal dog = new Animal("大黄狗") {
            @Override
            public void action() {
                Log.d("zjz1",name + "：汪汪汪，摇尾巴");
            }
        };
        manager.startAnimalWork(dog);

        manager.setCallback(new AnimalCallback() {
            @Override
            public void onAnimalEvent(String msg) {
                Log.d("zjz1","新版回调收到消息：" + msg);
            }
        });
        Animal bird = new Animal("小鸟") {
            @Override
            public void action() {
                Log.d("zjz1",name + "：叽叽喳喳在飞");
            }
        };
        manager.startAnimalWork(bird);
    }
}

interface AnimalCallback {
    void onAnimalEvent(String msg);
}

abstract class Animal {
    String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void action();  //抽象方法，子类必须重写
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void action() {
        Log.d("zjz1",name + "：喵喵喵，跑来跑去");
    }
}

class AnimalManager {
    private AnimalCallback callback;

    public void setCallback(AnimalCallback callback) {
        this.callback = callback;
    }

    public void startAnimalWork(Animal animal) {
        Log.d("zjz1","==== 动物开始活动 ====");
        animal.action();

        if (callback != null){
            callback.onAnimalEvent("【回调通知】" + animal.name + "执行完动作了");
        }
    }
}