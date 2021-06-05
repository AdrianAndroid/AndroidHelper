package cn.kuwo.pp.http.bean;

public class MatchCountBean {
    private int num;    //已有机会数
    private int cnt;    //已答题次数

    public int getNum() {
        return num;
    }

    public int getCnt() {
        return cnt;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
