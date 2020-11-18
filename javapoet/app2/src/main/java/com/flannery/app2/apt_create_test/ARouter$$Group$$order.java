package com.flannery.app2.apt_create_test;

import com.flannery.arouter_api.ARouterGroup;
import com.flannery.arouter_api.ARouterPath;

import java.util.HashMap;
import java.util.Map;

// TODO 模板 最终动态生成的效果

/**
 * 代表根据 组名“order” 对应---ARouterPath（ARouter$$Path$$order--(包含了很多的myClass)）
 */
public class ARouter$$Group$$order implements ARouterGroup {

    /**
     * @return key：组名 如：“order”  ---- ARouter$$Path$$order--(包含了很多的myClass)
     */
    @Override
    public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        groupMap.put("order", ARouter$$Path$$order.class);       // 寻找Path
        groupMap.put("personal", ARouter$$Path$$personal.class); // 寻找Path
        return groupMap;
    }
}
