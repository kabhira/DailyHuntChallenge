package com.akhare.dailyhuntchallenge.Network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akhare on 10/18/15.
 */
public class NewsData implements Parcelable {

    private String title;
    private String source;
    private String category;
    private String image;
    private String content;
    private String url;

    public NewsData(){

    }

    public NewsData(Parcel source){
        readFromParcel(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(source);
        dest.writeString(category);
        dest.writeString(image);
        dest.writeString(content);
        dest.writeString(url);
    }

    private void readFromParcel(Parcel msource){
        title = msource.readString();
        source = msource.readString();
        category = msource.readString();
        image = msource.readString();
        content = msource.readString();
        url = msource.readString();
    }

    public int describeContents(){
        return 0;
    }

    public static final Creator CREATOR = new Creator<NewsData>(){

        public NewsData createFromParcel(Parcel source){
            return new NewsData(source);
        }

        public NewsData[] newArray(int size){
            return new NewsData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
