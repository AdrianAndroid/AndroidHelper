package com.flannery.app2.apt_create_test;

import com.flannery.arouter_annotation.bean.RouterBean;
import com.flannery.arouter_api.ARouterPath;
import com.flannery.order.Order_MainActivity;
import com.flannery.personal.Personal_Main2Activity;

import java.util.HashMap;
import java.util.Map;

// TODO 模板 最终动态生成的效果
// TODO  这就是要用 APT 动态生成的代码
public class ARouter$$Path$$personal implements ARouterPath {

    @Override
    public Map<String, RouterBean> getPathMap() {
        Map<String, RouterBean> pathMap = new HashMap<>();

        pathMap.put("/personal/Personal_MainActivity",
                RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
                                  Order_MainActivity.class,
                           "/personal/Personal_MainActivity",
                          "personal"));

        pathMap.put("/personal/Order_Main2Activity",
                RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
                        Personal_Main2Activity.class,
                        "/personal/Personal_Main2Activity",
                        "personal"));
        return pathMap;
    }
}
