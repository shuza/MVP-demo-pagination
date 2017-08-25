package me.shuza.demo_pagination.ui;

import com.eclipsesource.json.JsonObject;

/**
 * Created by Boka on 23-Aug-17.
 */

public class ViewModel {
    private String contentType;
    private String url;
    private String title;

    public ViewModel() {
        contentType = "";
        url = "";
        title = "";
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.add("contentType", getContentType());
        obj.add("url", getUrl());
        obj.add("title", getTitle());
        return obj;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}