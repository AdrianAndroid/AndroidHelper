package cn.kuwo.pp.http.bean;

import static cn.kuwo.pp.http.bean.UserTrendBean.TrendType.TREND_EMPTY;

public class EmptyTrendBean extends UserTrendBean {
    @Override
    public TrendType getTrendType(){
        return TREND_EMPTY;
    }
}
