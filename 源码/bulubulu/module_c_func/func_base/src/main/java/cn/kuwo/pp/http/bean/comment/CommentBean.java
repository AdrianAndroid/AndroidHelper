package cn.kuwo.pp.http.bean.comment;

import java.util.ArrayList;

public class CommentBean {
    private ArrayList<CommentItem> comments;
    private boolean more;

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public boolean isMore() {
        return more;
    }
}
