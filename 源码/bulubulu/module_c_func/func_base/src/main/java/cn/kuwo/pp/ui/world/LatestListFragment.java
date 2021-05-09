package cn.kuwo.pp.ui.world;

public class LatestListFragment  extends HotListFragment {

    public static LatestListFragment newInstance(){return new LatestListFragment();}

    @Override
    protected void queryData(){
        mPresenter.requestLatestTrendList(startPage, pageCount);
    }


    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}