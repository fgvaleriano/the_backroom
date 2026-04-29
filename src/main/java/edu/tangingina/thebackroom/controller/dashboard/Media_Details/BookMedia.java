package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.*;

public class BookMedia extends BaseMedia{
    /*
        book-specific detail page
     */

    private final String author;
    private final List<String> genres;
    private final int pageCount;
    private final String edition;
    private final String isbn;

    public BookMedia(int id, String title, String author, String synopsis, int releaseYear, String imagePath,
                     List<String> accessLinks, List<String> genres, int pageCount, String edition, String isbn) {

        super(id, title, author, synopsis, releaseYear, imagePath, genres, accessLinks);
        this.author = author;
        this.genres = genres;
        this.pageCount = pageCount;
        this.edition = edition;
        this.isbn = isbn;
    }

    @Override
    public String getType() {
        return "Books";
    }

    public String getAuthor() {return author;}

    public List<String> getGenres() {return genres;}

    public int getPageCount() {return pageCount;}

    public String getEdition() {return edition;}

    public String getIsbn() {return isbn;}

    @Override
    public String getMainCreator() {
        return author;
    }

    @Override
    public List<DetailField> getDetailFields() {
        List<DetailField> fields = new ArrayList<>();

        fields.add(new DetailField("Genre", String.join(", ", genres)));
        fields.add(new DetailField("", pageCount + " pages, " + edition));
        fields.add(new DetailField("ISBN ", isbn));
        return fields;
    }
}
