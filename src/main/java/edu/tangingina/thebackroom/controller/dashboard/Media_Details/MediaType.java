package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

public enum MediaType {
    BOOK("Book"),
    MOVIE("Movie"),
    TV_SHOW("TV Show"),
    GAME("Game");

    private final String displayName;

    MediaType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
