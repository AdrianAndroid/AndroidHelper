package com.flannery.app2.apt_create_test;

import com.flannery.arouter_api.ARouterGroup;
import com.flannery.arouter_api.ARouterPath;

import java.util.HashMap;
import java.util.Map;

// TODO  这就是要用 APT 动态生成的代码
public class ARouter$$Group$$personal implements ARouterGroup {

    @Override
    public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        groupMap.put("personal", ARouter$$Path$$personal.class);
        return groupMap;
    }
}
