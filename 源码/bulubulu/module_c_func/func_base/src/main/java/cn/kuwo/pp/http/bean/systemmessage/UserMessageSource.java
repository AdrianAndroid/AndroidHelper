package cn.kuwo.pp.http.bean.systemmessage;

public class UserMessageSource {
    private int type;       //1 文字 2 图片 3 url
    private String text;
    private String pic;
    private String url;

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getPic() {
        return pic;
    }

    public String getUrl() {
        return url;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
