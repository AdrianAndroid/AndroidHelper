package cn.kuwo.pp.http.bean.systemmessage;

public class SystemMessageSource {
    private String opUser;
    private String fid;
    private String createTime;
    private String createUser;
    private String updateTime;
    private String id;
    private String pic;
    private String message;
    private int type;        //1 图片 2 文字 3 链接 4 文字+链接
    private String title;
    private String url;
    private String status;

    public String getOpUser() {
        return opUser;
    }

    public String getFid() {
        return fid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getId() {
        return id;
    }

    public String getPic() {
        return pic;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
