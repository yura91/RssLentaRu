package com.plaps.retrofitnewsfeed.models;

import org.simpleframework.xml.Attribute;

public class Enclosure {
    @Attribute
    private String length;
    @Attribute
    private String type;
    @Attribute
    private String url;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Enclosure [length = " + length + ", type = " + type + ", url = " + url + "]";
    }
}
