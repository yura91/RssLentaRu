package com.plaps.retrofitnewsfeed.models;
/**
 * Created by subodhnijsure on 5/4/16.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "item", strict = false)
public class RssFeedItem implements Serializable {


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getLink() {
        return link;
    }


    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public Enclosure getEnclosure() {

        return enclosure;
    }
    @Element(name = "enclosure", required = false)
    private Enclosure enclosure;
    @Element(name = "title", required = true )
    private String title;

    @Element(name = "pubDate", required = true )
    private String publicationDate;

    @Element(name = "description", required = true )
    private  String description;

    @Element(name = "link", required = true )
    private String link;

    public RssFeedItem(String title, String description, String publicationDate, String link) {
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.link = link;
    }
    public RssFeedItem() {}

    public boolean isEqualTo(RssFeedItem o) {
        if (o.getTitle().equals(title) &&
                o.getDescription().equals(description) &&
                o.getPublicationDate().equals(publicationDate)) {
            return true;
        }
        else
            return false;
    }

}
