package cn.kuwo.common.dialog;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.DrawableRes;

/**
 * Created by shihc on 2017/7/18.
 */

public class OptionItem implements Parcelable {
    private String title;
    private int icon;
    private boolean isEnable = true;

    public OptionItem(String title) {
        this.title = title;
    }

    public OptionItem(String title, boolean isEnable){
        this.title = title;
        this.isEnable = isEnable;
    }

    public OptionItem(String title, @DrawableRes int icon) {
        this.title = title;
        this.icon = icon;
    }

    public OptionItem(String title, @DrawableRes int icon, boolean isEnable) {
        this.title = title;
        this.icon = icon;
        this.isEnable = isEnable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public boolean isEnable() {
        return isEnable;
    }
    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.icon);
        dest.writeByte(this.isEnable ? (byte) 1 : (byte) 0);
    }

    protected OptionItem(Parcel in) {
        this.title = in.readString();
        this.icon = in.readInt();
        this.isEnable = in.readByte() != 0;
    }

    public static final Creator<OptionItem> CREATOR = new Creator<OptionItem>() {
        @Override
        public OptionItem createFromParcel(Parcel source) {
            return new OptionItem(source);
        }

        @Override
        public OptionItem[] newArray(int size) {
            return new OptionItem[size];
        }
    };
}
