package cn.kuwo.pp.http.bean;

import java.io.File;

public class BuildQuestionBean {
    public BuildQuestionBean(String qtype, String text, String optionOne, String optionTwo, String visible, File file){
        this.qtype = qtype;
        this.text = text;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.visible = visible;
        this.file = file;
    }

    public String qtype;
    public String text;
    public String optionOne;
    public String optionTwo;
    public String visible;
    public File   file;
}
