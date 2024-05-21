package com.example.project_app_book.model;

import java.util.Map;

public class Book {
    private int authorId;
    private int categoryId;
    private Map<String, String> content;
    private String description;
    private String image;
    private int publishedYear;
    private int publisherId;
    private String title;

    // Constructor không tham số
    public Book() {}

    // Constructor có tham số
    public Book(int authorId, int categoryId, Map<String, String> content, String description, String image, int publishedYear, int publisherId, String title) {
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.content = content;
        this.description = description;
        this.image = image;
        this.publishedYear = publishedYear;
        this.publisherId = publisherId;
        this.title = title;
    }

    // Getter và Setter
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
