package cn.kuwo.pp.http.bean.user;

/**
 * @author shihc
 */
public class UpdateProfileParam {
    private UpdateProfileParam userInfo;
    private String address;
    private String birthday;//生日（yyyy-MM-dd）
    private String city;//市
    private String headImg;
    private String name;
    private String prov;//省
    private String extendData;//扩展字段，传json字符串，key以prop_开头，如"{\"prop_key1\":\"value5\",\"prop_key2\":\"value6\"}"
    private Integer sex;

    public UpdateProfileParam() {
    }

    public UpdateProfileParam(UpdateProfileParam userInfo) {
        this.userInfo = userInfo;
    }

    public UpdateProfileParam getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UpdateProfileParam userInfo) {
        this.userInfo = userInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }
}
