package com.teamnamo.Youtube;

public class VideoModal {
    String videoId;
    String thumbnail;
    String title;
    public VideoModal(String videoId,String thumbnail,String title){
        this.videoId= videoId;
        this.thumbnail = thumbnail;
        this.title = title;

    }

    public String getVideoId() {
        return videoId;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
