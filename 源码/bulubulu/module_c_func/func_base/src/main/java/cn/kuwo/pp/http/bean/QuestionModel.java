package cn.kuwo.pp.http.bean;

import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.pp.http.bean.user.UserInfo;

public class QuestionModel {
    private String question;
    private String id;
    private String sourceType;  //1 动态 2 管理员创建题目
    private String type;        //1文字 2图片
    private String optionOne;
    private String optionTwo;
    private int liked;
    private UserInfo user;
    private QuestionAnswerModel answerModel;
    private boolean showComment;
    private boolean hadShowDanMu;
    private CommentBean commentBean;
    private int colorIndex = -1;
    private boolean enableClick = true;

    private String optionOneBgColor; // 颜色
    private String optionTwoBgColor; // 颜色
    private String optionOneTxtBgColor; // 颜色
    private String optionTwoTxtBgColor; // 颜色

    public void setAnswerModel(QuestionAnswerModel answerModel) {
        this.answerModel = answerModel;
    }

    public QuestionAnswerModel getAnswerModel() {
        return answerModel;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isPicQuestion() {
        if (type == null) {
            return false;
        }

        return type.equalsIgnoreCase("2");
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public CommentBean getCommentBean() {
        return commentBean;
    }

    public void setCommentBean(CommentBean commentBean) {
        this.commentBean = commentBean;
    }

    // 是否有了评论
    public boolean isHadComment() {
        return commentBean != null
                && commentBean.getComments() != null
                && commentBean.getComments().size() > 0;
    }

    public boolean isHadShowDanMu() {
        return hadShowDanMu;
    }

    public void setHadShowDanMu(boolean hadShowDanMu) {
        this.hadShowDanMu = hadShowDanMu;
    }

    public boolean isEnableClick() {
        return enableClick;
    }

    public void setEnableClick(boolean enableClick) {
        this.enableClick = enableClick;
    }

    public String getOptionOneBgColor() {
        return optionOneBgColor;
    }

    public void setOptionOneBgColor(String optionOneBgColor) {
        this.optionOneBgColor = optionOneBgColor;
    }

    public String getOptionTwoBgColor() {
        return optionTwoBgColor;
    }

    public void setOptionTwoBgColor(String optionTwoBgColor) {
        this.optionTwoBgColor = optionTwoBgColor;
    }

    public String getOptionOneTxtBgColor() {
        return optionOneTxtBgColor;
    }

    public void setOptionOneTxtBgColor(String optionOneTxtBgColor) {
        this.optionOneTxtBgColor = optionOneTxtBgColor;
    }

    public String getOptionTwoTxtBgColor() {
        return optionTwoTxtBgColor;
    }

    public void setOptionTwoTxtBgColor(String optionTwoTxtBgColor) {
        this.optionTwoTxtBgColor = optionTwoTxtBgColor;
    }
}
