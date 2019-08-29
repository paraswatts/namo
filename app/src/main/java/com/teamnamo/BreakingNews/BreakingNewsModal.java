package com.teamnamo.BreakingNews;

public class BreakingNewsModal {

    String publishedat;
    String description;
    public BreakingNewsModal( String description,String publishedat){
        this.description =description;
        this.publishedat=publishedat;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

}
