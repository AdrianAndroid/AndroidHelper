package cn.kuwo.pp.http.bean.systemmessage;
// 系统消息的结果
public class SystemMessageResult {
    private SystemMessageBean body;
    private int newCnt;

    public SystemMessageBean getBody() {
        return body;
    }

    public int getNewCnt() {
        return newCnt;
    }

    public void setBody(SystemMessageBean body) {
        this.body = body;
    }

    public void setNewCnt(int newCnt) {
        this.newCnt = newCnt;
    }


    @Override
    public String toString() {
        return "SystemMessageResult{" +
                "body=" + body +
                ", newCnt=" + newCnt +
                '}';
    }
}
