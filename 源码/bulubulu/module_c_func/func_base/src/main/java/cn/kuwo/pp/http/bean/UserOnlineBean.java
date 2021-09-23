package cn.kuwo.pp.http.bean;

public class UserOnlineBean {
    private String Status;
    private String State;
    private String To_Account;

    public boolean isOnline(){
        if(Status != null){
            if(Status.equalsIgnoreCase("Online")){
                return true;
            }
        }

        return false;
    }

    public String getStatus() {
        return Status;
    }

    public String getState() {
        return State;
    }

    public String getTo_Account() {
        return To_Account;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setState(String state) {
        State = state;
    }

    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }
}
