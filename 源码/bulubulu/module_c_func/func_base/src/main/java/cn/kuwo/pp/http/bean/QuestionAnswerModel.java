package cn.kuwo.pp.http.bean;

import java.util.List;

import cn.kuwo.pp.http.bean.user.UserInfo;

public class QuestionAnswerModel {
    private int optionOnePer;
    private int scoreOne;
    private int optionTwoPer;
    private int scoreTwo;
    private int matchTimes;
    private int cnt;
    private List<UserInfo> optionOneUser;
    private List<UserInfo> optionTwoUser;
    private String userAnswer;


    public QuestionAnswerModel() {
    }

    public QuestionAnswerModel(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isAnswerOne() {
        return userAnswer.equals("optionOne");
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getOptionOnePer() {
        return optionOnePer;
    }

    public int getOptionTwoPer() {
        return optionTwoPer;
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

    public List<UserInfo> getOptionOneUser() {
        return optionOneUser;
    }

    public List<UserInfo> getOptionTwoUser() {
        return optionTwoUser;
    }

    public void setOptionOnePer(int optionOnePer) {
        this.optionOnePer = optionOnePer;
    }

    public void setOptionTwoPer(int optionTwoPer) {
        this.optionTwoPer = optionTwoPer;
    }

    public void setOptionOneUser(List<UserInfo> optionOneUser) {
        this.optionOneUser = optionOneUser;
    }

    public void setOptionTwoUser(List<UserInfo> optionTwoUser) {
        this.optionTwoUser = optionTwoUser;
    }

    public int getMatchTimes() {
        return matchTimes;
    }

    public void setMatchTimes(int matchTimes) {
        this.matchTimes = matchTimes;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
