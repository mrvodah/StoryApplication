package com.example.vietvan.storyapplication;

/**
 * Created by VietVan on 23/03/2018.
 */

public class Story_type {
    public int id;
    public String name, description, color;

    public Story_type(int id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Story_type{" +
                "name='" + name + '\'' +
                '}';
    }
}
