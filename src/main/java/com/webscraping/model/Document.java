package com.webscraping.model;

public class Document {

    private String url;
    private String fileType;
    private String fileName;

    public Document(String url, String fileType){
        this.url = url;
        this.fileType = fileType;
        this.fileName = extractFileName(url);
    }

    private String extractFileName(String url) {
        return url.substring(url.lastIndexOf('/')+ 1);
    }

    public String getUrl() {
        return url;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }
}
