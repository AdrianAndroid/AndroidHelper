package cn.kuwo.pp.event;

public class UserPublishEvent {
    private int page;
    public UserPublishEvent(int page){
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
