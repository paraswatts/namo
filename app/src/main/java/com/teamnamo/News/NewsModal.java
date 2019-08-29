package com.teamnamo.News;

public class NewsModal {
    String source;
    String url;
    String title;
    String publishedat;
    String urltoimage;
    public NewsModal(String source, String url, String title,String publishedat,String urltoimage){
        this.source= source;
        this.url = url;
        this.title = title;
        this.publishedat=publishedat;
        this.urltoimage=urltoimage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrltoimage() {
        return urltoimage;
    }

    public void setUrltoimage(String urltoimage) {
        this.urltoimage = urltoimage;
    }
}
