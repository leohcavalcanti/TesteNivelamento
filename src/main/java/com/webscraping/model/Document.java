package com.webscraping.model;

public class Document {

    private String url;
    private String fileType;

    public Document(String url, String fileType){
        this.url = url;
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public String getFileType() {
        return fileType;
    }
}
