package com.example.vietvan.storyapplication;

import java.io.Serializable;

/**
 * Created by VietVan on 23/03/2018.
 */

public class Story implements Serializable{
    public int id;
    public String title, description, content, image, author;
    public int bookmark, id_type;

    public Story(int id, String title, String description, String content, String image, String author, int bookmark, int id_type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.image = image;
        this.author = author;
        this.bookmark = bookmark;
        this.id_type = id_type;
    }

    @Override
    public String toString() {
        return title;
    }
}
