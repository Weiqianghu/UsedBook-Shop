package com.weiqianghu.usedbook_shop.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public class ImgModel implements Parcelable{
    private String imgUrl;

    public ImgModel(){}

    protected ImgModel(Parcel in) {
        imgUrl = in.readString();
    }

    public static final Creator<ImgModel> CREATOR = new Creator<ImgModel>() {
        @Override
        public ImgModel createFromParcel(Parcel in) {
            return new ImgModel(in);
        }

        @Override
        public ImgModel[] newArray(int size) {
            return new ImgModel[size];
        }
    };

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
    }
}
