package com.teamnamo;

public class PhotosModal {
    String url;
    String title;
    public PhotosModal(String url, String title){
        this.url= url;
        this.title = title;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
