package edu.tangingina.thebackroom.controller.dashboard;

public class MediaItem {

    private final String title;
    private final String imagePath;
    private final String genre;
    private final String mediaType;

    public MediaItem(String title, String imagePath, String genre, String mediaType){
        this.title = title;
        this.imagePath = imagePath;
        this.genre = genre;
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getGenre() {
        return genre;
    }

    public String getMediaType() {
        return mediaType;
    }
}
