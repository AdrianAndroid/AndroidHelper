package cn.kuwo.pp.ui.world;

public class FavorListFragment extends HotListFragment {

    public static FavorListFragment newInstance(){return new FavorListFragment();}

    @Override
    protected void queryData(){
        mPresenter.requestFavorTrendList(startPage, pageCount);
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }
}
