// Category.java
package com.example.finex_mobile.entities.category;

public class Category {
    private String id;
    private String name;
    private String language;

    public Category(String id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}