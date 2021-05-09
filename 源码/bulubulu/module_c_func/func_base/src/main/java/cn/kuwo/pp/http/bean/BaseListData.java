package cn.kuwo.pp.http.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理返回的data中只有list的基类
 * @param <T>
 */
public class BaseListData<T> {
    private List<T> list;

    public List<T> getList() {
        if (list == null){
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
