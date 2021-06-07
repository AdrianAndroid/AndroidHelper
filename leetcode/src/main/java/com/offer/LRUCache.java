package com.offer;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer, Integer> {

    final int capcity;

    public LRUCache(int capcity) {
        super(capcity, 0.75f, true);
        this.capcity = capcity;
    }

    @Override
    public Integer get(Object key) {
        return super.getOrDefault(key, -1);
    }

    @Override
    public Integer put(Integer key, Integer value) {
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capcity;
    }
}
