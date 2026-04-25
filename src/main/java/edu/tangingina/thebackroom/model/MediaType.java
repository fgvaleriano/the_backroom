package edu.tangingina.thebackroom.model;

public enum MediaType{
    Book,
    Movie,
    TvShow,
    Game;

    public static MediaType getType(String value){
        switch(value){
            case "Book":
                return MediaType.Book;

            case "Movie":
                return MediaType.Movie;

            case "TvShow":
                return MediaType.TvShow;

            case "Game":
                return MediaType.Game;
        }

        return null;
    }

}
