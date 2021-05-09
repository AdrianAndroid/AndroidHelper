package cn.kuwo.pp.http.bean;

import android.graphics.Color;

import com.google.gson.Gson;

public class WebQuestionModelBean {
    private WebQuestionBean a;
    private WebQuestionBean b;
    private String title;

    public WebQuestionBean getA() {
        return a;
    }

    public WebQuestionBean getB() {
        return b;
    }

    public String getTitle() {
        return title;
    }

    public void setA(WebQuestionBean a) {
        this.a = a;
    }

    public void setB(WebQuestionBean b) {
        this.b = b;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static WebQuestionModelBean fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, WebQuestionModelBean.class);
    }

    public WebQuestionModelBean fromQuestion(QuestionModel questionModel) {
        this.a = new WebQuestionBean();
        this.b = new WebQuestionBean();

        this.a.setBgColor(questionModel.getOptionOneBgColor());
        this.b.setBgColor(questionModel.getOptionTwoBgColor());
        this.a.setTxtColor(questionModel.getOptionOneTxtBgColor());
        this.b.setTxtColor(questionModel.getOptionTwoTxtBgColor());

        this.a.setPercent(questionModel.getAnswerModel().getOptionOnePer());
        if (questionModel.isPicQuestion()) {
            this.a.setImg(questionModel.getOptionOne());
        } else {
            this.a.setTitle(questionModel.getOptionOne());
        }
        this.a.setChoosed(questionModel.getAnswerModel().isAnswerOne());

        this.b.setPercent(questionModel.getAnswerModel().getOptionTwoPer());
        if (questionModel.isPicQuestion()) {
            this.b.setImg(questionModel.getOptionTwo());
        } else {
            this.b.setTitle(questionModel.getOptionTwo());
        }
        this.b.setChoosed(!questionModel.getAnswerModel().isAnswerOne());

        this.title = questionModel.getQuestion();
        return this;
    }
}
