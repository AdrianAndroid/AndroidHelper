package cn.kuwo.pp.http.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.http.bean.user.UserInfo;

public class UserTrendBean implements  MultiItemEntity {
    private int type;       //type 动态类型 1 普通动态 2 答题动态
    private int qtype;      //qtype 题目类型 1 纯文字 2 纯图片
    private String  source;     //动态来源
    private int liked;      //是否已喜欢 0 未喜欢 1 已喜欢
    private int hasFollowed;//是否关注 0 未关注 1 已关注
    private String  optionOne;  //选项1
    private int uid;        //创建者uid
    private int optionTwoPer;//选项2百分比
    private String  picked;      //已选选项
    private List<String> picIds; //图片地址数组
    private List<TopicItemBean> topics;   //话题列表
    private String  id;          //tid
    private String  qid;         //对应题目id 答题时传此id
    private String  text;        //文本
    private long    timeMillis;  //创建时间戳
    private int visible;     //可见状态 1 仅自己可见 3 所有人可见
    private int optionOnePer;//选项1百分比
    private int scoreOne;
    private int scoreTwo;
    private int unliked;     //是否不喜欢 0 未不喜欢 1 已不喜欢
    private String  optionTwo;   //选项2
    private int truncated;   //是否截断 0 未截断 1 截断
    private int unlikedCnt;  //不喜欢次数
    private int commentsCount;//评论数
    private int likedCnt;    //喜欢次数
    private UserInfo user;       //用户信息
    private int colorIndex = -1;

    public static String ANSWER_ONE = "optionOne";
    public static String ANSWER_TWO = "optionTwo";

    public enum TrendType {
        TREND_NULL,
        TREND_EMPTY,
        TREND_IMAGE_QUESTION,
        TREND_TEXT_QUESTION,
        TREND_USER_PUBLISH
    }

    public enum TrendEntity{        //对应 type 字体
        TREND_UNDEFINE,
        TREND_NORMAL,
        TREND_QUESTION
    }

    public TrendType getTrendType(){
        if(type == 1){
            return TrendType.TREND_USER_PUBLISH;
        }
        if(type == 2){
            if(qtype == 1){
                return TrendType.TREND_TEXT_QUESTION;
            }else if(qtype == 2){
                return TrendType.TREND_IMAGE_QUESTION;
            }
        }

        return TrendType.TREND_NULL;
    }

    public QuestionModel toQuestionModel(){
        QuestionModel model = new QuestionModel();
        model.setQuestion(getText());
        model.setId(getId());
        model.setType(getQtype()+"");
        model.setOptionOne(getOptionOne());
        model.setOptionTwo(getOptionTwo());
        model.setUser(getUser());
        model.setColorIndex(getColorIndex());

        if(!getPicked().isEmpty()){
            QuestionAnswerModel answerModel = new QuestionAnswerModel();
            answerModel.setOptionOnePer(getOptionOnePer());
            answerModel.setOptionTwoPer(getOptionTwoPer());
            answerModel.setScoreOne(getScoreOne());
            answerModel.setScoreTwo(getScoreTwo());
            answerModel.setUserAnswer(getPicked());
            answerModel.setOptionOneUser(new ArrayList());
            answerModel.setOptionTwoUser(new ArrayList());
            model.setAnswerModel(answerModel);
        }

        return model;
    }

    @Override
    public int getItemType(){
        return getTrendType().ordinal();
    }

    public int getType() {
        return type;
    }

    public int getQtype() {
        return qtype;
    }

    public String getSource() {
        return source;
    }

    public int getLiked() {
        return liked;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public int getUid() {
        return uid;
    }

    public int getOptionTwoPer() {
        return optionTwoPer;
    }

    public String getPicked() {
        return picked;
    }

    public List<String> getPicIds() {
        return picIds;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public int getVisible() {
        return visible;
    }

    public int getOptionOnePer() {
        return optionOnePer;
    }

    public int getUnliked() {
        return unliked;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public int getTruncated() {
        return truncated;
    }

    public int getUnlikedCnt() {
        return unlikedCnt;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikedCnt() {
        return likedCnt;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setOptionTwoPer(int optionTwoPer) {
        this.optionTwoPer = optionTwoPer;
    }

    public void setPicked(String picked) {
        this.picked = picked;
    }

    public void setPicIds(List<String> picIds) {
        this.picIds = picIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public void setOptionOnePer(int optionOnePer) {
        this.optionOnePer = optionOnePer;
    }

    public void setUnliked(int unliked) {
        this.unliked = unliked;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public void setTruncated(int truncated) {
        this.truncated = truncated;
    }

    public void setUnlikedCnt(int unlikedCnt) {
        this.unlikedCnt = unlikedCnt;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setLikedCnt(int likedCnt) {
        this.likedCnt = likedCnt;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getHasFollowed() {
        return hasFollowed;
    }

    public void setHasFollowed(int hasFollowed) {
        this.hasFollowed = hasFollowed;
    }

    public void setScoreOne(int scoreOne) {
        this.scoreOne = scoreOne;
    }

    public void setScoreTwo(int scoreTwo) {
        this.scoreTwo = scoreTwo;
    }

    public int getScoreOne() {
        return scoreOne;
    }

    public int getScoreTwo() {
        return scoreTwo;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public List<TopicItemBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicItemBean> topics) {
        this.topics = topics;
    }


    @Override
    public String toString() {
        return "UserTrendBean{" +
                "type=" + type +
                ", qtype=" + qtype +
                ", source='" + source + '\'' +
                ", liked=" + liked +
                ", hasFollowed=" + hasFollowed +
                ", optionOne='" + optionOne + '\'' +
                ", uid=" + uid +
                ", optionTwoPer=" + optionTwoPer +
                ", picked='" + picked + '\'' +
                ", picIds=" + picIds +
                ", topics=" + topics +
                ", id='" + id + '\'' +
                ", qid='" + qid + '\'' +
                ", text='" + text + '\'' +
                ", timeMillis=" + timeMillis +
                ", visible=" + visible +
                ", optionOnePer=" + optionOnePer +
                ", scoreOne=" + scoreOne +
                ", scoreTwo=" + scoreTwo +
                ", unliked=" + unliked +
                ", optionTwo='" + optionTwo + '\'' +
                ", truncated=" + truncated +
                ", unlikedCnt=" + unlikedCnt +
                ", commentsCount=" + commentsCount +
                ", likedCnt=" + likedCnt +
                ", user=" + user +
                ", colorIndex=" + colorIndex +
                '}';
    }
}
