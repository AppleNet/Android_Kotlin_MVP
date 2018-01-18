package com.example.llcgs.android_kotlin.material.douya.main.fragment.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
 *
 * @author liulongchao
 * @since 2017/12/15
 */
public class BroadListContent implements Parcelable {

    private String avatar;

    private String name;

    private String time;

    private String content;

    private String attachmentImage;

    private String attachmentTitle;

    private String attachmentDes;

    private String desUrl;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentImage() {
        return attachmentImage;
    }

    public void setAttachmentImage(String attachmentImage) {
        this.attachmentImage = attachmentImage;
    }

    public String getAttachmentTitle() {
        return attachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.attachmentTitle = attachmentTitle;
    }

    public String getAttachmentDes() {
        return attachmentDes;
    }

    public void setAttachmentDes(String attachmentDes) {
        this.attachmentDes = attachmentDes;
    }

    public String getDesUrl() {
        return desUrl;
    }

    public void setDesUrl(String desUrl) {
        this.desUrl = desUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeString(this.content);
        dest.writeString(this.attachmentImage);
        dest.writeString(this.attachmentTitle);
        dest.writeString(this.attachmentDes);
        dest.writeString(this.desUrl);
    }

    public BroadListContent() {
    }

    protected BroadListContent(Parcel in) {
        this.avatar = in.readString();
        this.name = in.readString();
        this.time = in.readString();
        this.content = in.readString();
        this.attachmentImage = in.readString();
        this.attachmentTitle = in.readString();
        this.attachmentDes = in.readString();
        this.desUrl = in.readString();
    }

    public static final Creator<BroadListContent> CREATOR = new Creator<BroadListContent>() {
        @Override
        public BroadListContent createFromParcel(Parcel source) {
            return new BroadListContent(source);
        }

        @Override
        public BroadListContent[] newArray(int size) {
            return new BroadListContent[size];
        }
    };
}
