package cn.kuwo.pp.event;

public class MainTabSelectChangeEvent {
    private int mCurrentTabIndex = 0;

    public enum MainTabIndex {
        TAB_CHAT,
        TAB_FRIEND,
        TAB_WORLD
    }

    public MainTabSelectChangeEvent(int index){
        mCurrentTabIndex = index;
    }

    public int getCurrentTabIndex(){
        return mCurrentTabIndex;
    }
}
