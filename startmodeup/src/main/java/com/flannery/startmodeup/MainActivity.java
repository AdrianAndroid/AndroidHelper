package com.flannery.startmodeup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "hellow", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }
}

/*
一、类别
1.standard
标准模式，是系统的默认模式。每次启动一个Activity都会重新创建一个新的实例，不管这个实例是否已经存在。被创建的实例的生命周期符合典型情况下Activity的生命周期，onCreate()、onStart()、onResume()都会被调用。

2.singleTop
栈顶复用模式。在此模式下，如果想要跳转的Activity已经位于任务栈的栈顶，那么此Activity不会被重新创建。如果此Activity的实例已经存在但是不在栈顶，那么此Activity会被创建。同时onNewIntent()会被调用，而onCreate()、onStart()、onResume()不会被调用。

3.singleTask
栈内复用模式。是一种单实例模式。在此模式下，只要想要跳转的Activity在任务栈中存在，那么启动此Activity都不需要创建新的实例，和singleTop一样，会回调onNewIntent()，onCreate()、onStart()、onResume()不会被调用。

4.singleInstance
单实例模式，是一种加强的singleTask模式。除了具有singleTask所有的特性，还有在此模式下的Activity只能单独的在一个任务栈中。

二、使用场景
1.standard
该模式为默认模式，适用于大多数应用场景。

2.singleTop
a.通知消息打开的页面；
b.耗时操作返回页面；
c.登录页面

3.singleTask
浏览器、微博等页面。
大多数 App 的主页。对于大部分应用，当我们在主界面点击回退按钮的时候都是退出应用，那么当我们第一次进入主界面之后，主界面位于栈底，以后不管我们打开了多少个 Activity，只要我们再次回到主界面，都应该使用将主界面Activity 上所有的 Activity 移除的方式来让主界面 Activity 处于栈顶，而不是往栈顶新加一个主界面 Activity 的实例，通过这种方式能够保证退出应用时所有的 Activity 都能被销毁。

4.singleInstance
呼叫来电界面。
 */
