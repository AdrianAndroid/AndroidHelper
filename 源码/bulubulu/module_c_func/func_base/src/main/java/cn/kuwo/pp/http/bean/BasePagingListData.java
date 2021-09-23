package cn.kuwo.pp.http.bean;

import java.util.List;

/**
 * 处理返回的data中有list的，并且有分页参数的基类
 *
 * @param <T>
 */
public class BasePagingListData<T> {
    private List<T> list;
    private List<T> searchRcmList;
    private int total;
    private int pn;
    private int rn;
    private int result;  //在话题搜索中表示话题是否已经存在

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getRn() {
        return rn;
    }

    public void setRn(int rn) {
        this.rn = rn;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<T> getSearchRcmList() {
        return searchRcmList;
    }

    public void setSearchRcmList(List<T> searchRcmList) {
        this.searchRcmList = searchRcmList;
    }
}
