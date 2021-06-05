package cn.kuwo.pp.http.bean.voice;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import cn.kuwo.pp.R;

/**
 * @author shihc
 * 用户信息
 */
public class VoiceInfo implements Parcelable {

    /**
     * card :
     * vid : 68
     * url : http://buluaudiocdn.kuwo.cn/resource/video/Fqbatv56lYAFVzSFv-szNQt-_gak1557297213004.mp3
     * age : 18
     * sex : 女
     * city :
     * uid : 123
     * name : 小仙女
     * headImg : http://123.125.240.7:16010/8a916a8bbff7f91b249b2d9be966a730
     * img : http://image.kuwo.cn/test/shanhu/20190507/gril_1.png
     * constellation : 巨蟹
     * likenum : 20
     * text : 啦啦啦
     * score : 93
     * matching : 正太，少年
     * alikeNoScore : 65
     * alikeScore : 85
     */

    private String card;
    private String vid;
    private String url;
    private String age;
    private String sex;
    private String city;
    private String uid;
    private String name;
    private String img; //用户声音识别卡通头像
    private String constellation;
    private String likenum;
    private String text;
    private int score;
    private String matching;
    private int alikeNoScore;
    private int alikeScore;
    private String headImg;     //用户上传头像
    private int integral;       // 积分
    private int matchValue;     //匹配度
    private String inviteCodeUser; //是否是被邀请的，1 是， 0不是

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public boolean isGirl() {
        return getSex().equals("2");
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getLikenum() {
        return likenum;
    }

    public void setLikenum(String likenum) {
        this.likenum = likenum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMatching() {
        return matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public VoiceInfo() {
    }

    public int getScoreValue() {
        return 0;
    }

    public int getAlikeNoScore() {
        return alikeNoScore;
    }

    public void setAlikeNoScore(int alikeNoScore) {
        this.alikeNoScore = alikeNoScore;
    }

    public int getAlikeScore() {
        return alikeScore;
    }

    public void setAlikeScore(int alikeScore) {
        this.alikeScore = alikeScore;
    }

    public void setIntegral(int i) {
        this.integral = i;
    }

    public int getIntegral() {
        return integral;
    }

    public String getInviteCodeUser() {
        return inviteCodeUser;
    }

    public void setInviteCodeUser(String inviteCodeUser) {
        this.inviteCodeUser = inviteCodeUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.card);
        dest.writeString(this.vid);
        dest.writeString(this.url);
        dest.writeString(this.age);
        dest.writeString(this.sex);
        dest.writeString(this.city);
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.constellation);
        dest.writeString(this.likenum);
        dest.writeString(this.text);
        dest.writeInt(this.score);
        dest.writeString(this.matching);
        dest.writeInt(this.alikeNoScore);
        dest.writeInt(this.alikeScore);
        dest.writeInt(this.integral);
        dest.writeString(this.inviteCodeUser);
    }

    protected VoiceInfo(Parcel in) {
        this.card = in.readString();
        this.vid = in.readString();
        this.url = in.readString();
        this.age = in.readString();
        this.sex = in.readString();
        this.city = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.img = in.readString();
        this.constellation = in.readString();
        this.likenum = in.readString();
        this.text = in.readString();
        this.score = in.readInt();
        this.matching = in.readString();
        this.alikeNoScore = in.readInt();
        this.alikeScore = in.readInt();
        this.integral = in.readInt();
        this.inviteCodeUser = in.readString();
    }

    public static final Creator<VoiceInfo> CREATOR = new Creator<VoiceInfo>() {
        @Override
        public VoiceInfo createFromParcel(Parcel source) {
            return new VoiceInfo(source);
        }

        @Override
        public VoiceInfo[] newArray(int size) {
            return new VoiceInfo[size];
        }
    };

    public static VoiceInfo fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, VoiceInfo.class);
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(int matchValue) {
        this.matchValue = matchValue;
    }

    public int getNewDefaultHeadImage() {
        if (sex.equalsIgnoreCase("2"))
            return R.drawable.default_header_girl;
        else
            return R.drawable.default_header_boy;
    }


    @Override
    public String toString() {
        return "VoiceInfo{" +
                "card='" + card + '\'' +
                ", vid='" + vid + '\'' +
                ", url='" + url + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", constellation='" + constellation + '\'' +
                ", likenum='" + likenum + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", matching='" + matching + '\'' +
                ", alikeNoScore=" + alikeNoScore +
                ", alikeScore=" + alikeScore +
                ", headImg='" + headImg + '\'' +
                ", integral=" + integral +
                ", matchValue=" + matchValue +
                ", inviteCodeUser=" + inviteCodeUser +

                '}';
    }
}
