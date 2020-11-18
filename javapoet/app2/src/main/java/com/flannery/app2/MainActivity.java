package com.flannery.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flannery.app2.apt_create_test.ARouter$$Group$$personal;
import com.flannery.app2.apt_create_test.ARouter$$Path$$personal;
import com.flannery.arouter_annotation.ARouter;
import com.flannery.arouter_annotation.bean.RouterBean;
import com.flannery.arouter_api.ARouterPath;
import com.flannery.order.Order_MainActivity;

import java.util.Map;


@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void jumpOrder(View view) {
        Intent intent = new Intent(this, Order_MainActivity.class);
        intent.putExtra("name", "derry");
        startActivity(intent);
    }

    public void jumpPersonal(View view) {
        // 以前是这样跳转
        /*Intent intent = new Intent(this, Personal_MainActivity.class);
        intent.putExtra("name", "derry");
        startActivity(intent);*/
        
        // 现在是这样跳转  目前还要写这么多代码，是不是非常累

        // TODO 最终的成效：用户 一行代码搞定，同时还可以传递参数，同时还可以懒加载

        ARouter$$Group$$personal group$$personal = new ARouter$$Group$$personal();
        Map<String, Class<? extends ARouterPath>> groupMap = group$$personal.getGroupMap();
        Class<? extends ARouterPath> myClass = groupMap.get("personal");

        try {
            ARouter$$Path$$personal path = (ARouter$$Path$$personal) myClass.newInstance();
            Map<String, RouterBean> pathMap = path.getPathMap();
            RouterBean bean = pathMap.get("/personal/Personal_MainActivity");

            if (bean != null) {
                Intent intent = new Intent(this, bean.getMyClass());
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
