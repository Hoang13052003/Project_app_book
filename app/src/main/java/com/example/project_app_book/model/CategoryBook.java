package com.example.project_app_book.model;

public class CategoryBook {
    private String name, img, categoryId;
    public CategoryBook(){}

    public CategoryBook(String name, String img, String categoryId) {
        this.name = name;
        this.img = img;
        this.categoryId = categoryId;
    }

    public CategoryBook(String name, String categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
