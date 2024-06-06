package com.example.project_app_book.model;

public class Book {
    private String bookID;
    private String authorId;
    private String categoryId;
    private String content;
    private String description;
    private String image;
    private int publishedYear;
    private String publisherId;
    private String title;

    public Book() {}

    public Book(String bookID, String authorId, String categoryId, String content, String description, String image, int publishedYear, String publisherId, String title) {
        this.bookID = bookID;
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
    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}