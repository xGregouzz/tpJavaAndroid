package edu.esiea.androidcourse.models;

public class NewsModel {
    private String title;
    private String description;
    private String imageUrl;
    private String publishedAt;

    public NewsModel(String title, String description, String imageUrl, String publishedAt) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
