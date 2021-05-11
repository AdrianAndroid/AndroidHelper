package com.flannery.leak;


import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 继承自WeakReference，并且加入一个key，用来通过可以key可以查找到对应的KeyWeakReference
 * @param <T>
 */
public class KeyWeakReference<T> extends WeakReference<T> {

    private String key;
    private String name;

    public KeyWeakReference(T referent, String key, String name) {
        super(referent);
        this.key = key;
        this.name = name;
    }

    public KeyWeakReference(T referent, ReferenceQueue<? super T> q, String key, String name) {
        super(referent, q);
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KeyWeakReference{");
        sb.append("key='").append(key).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
