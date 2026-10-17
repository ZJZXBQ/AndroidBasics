package com.example.moduleone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main01);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Animal01Manager animal01Manager = new Animal01Manager();
        animal01Manager.setAnimalCallback01(new AnimalCallback01() {
            @Override
            public void printAction(String msg) {
                Log.d("zjz01", "回调在执行任务，本质是一个类");
            }
        });
        animal01Manager.doCallback01();

        Qingtuan qingtuan = new Qingtuan("团团",10);
        animal01Manager.doCallback02(qingtuan);

        animal01Manager.doCallback02(new Animal01("黄警官",3) {
            @Override
            public void action() {
                Log.d("zjz01", "强制方法的匿名内内部类");
            }
        });
    }
}

interface AnimalCallback01 {//回调接口，方法就是传入的逻辑，别人适当时机调用

    void printAction(String msg);
}

abstract class Animal01 {
    String name;
    int age;

    public Animal01(String name, int age) {
        this.name = name;
        this.age = age;
    }

    abstract public void action();
}

class Qingtuan extends Animal01 {
    public Qingtuan(String name, int age) {
        super(name, age);
    }

    @Override
    public void action() {
        Log.d("zjz01", "这是一只青团");
    }
}

class Animal01Manager {
    private AnimalCallback01 animalCallback01;

    public void setAnimalCallback01(AnimalCallback01 animalCallback01) {
        this.animalCallback01 = animalCallback01;
    }

    public void doCallback01() {
        animalCallback01.printAction("这里的是回调消息");
    }
    public void doCallback02(Animal01 animal01) {
        animal01.action();
    }
}
