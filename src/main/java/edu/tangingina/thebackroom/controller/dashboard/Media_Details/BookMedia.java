package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.*;

public class BookMedia extends BaseMedia{
    /*
        book-specific detail page
     */

    private final List<String> author;
    private final int pageCount;
    private final String edition;
    private final String isbn;

    public BookMedia(int id, String title, String synopsis, int releaseYear, String imagePath, List<String> categories,
                     List<String> accessLinks, String isbn, int pageCount, String edition, List<String> authors) {

        super(id, title, MediaType.BOOK, synopsis, releaseYear, imagePath, categories, accessLinks);
        this.pageCount = pageCount;
        this.edition = edition;
        this.isbn = isbn;
        this.author = authors == null ? new ArrayList<>() : new ArrayList<>(authors);
    }

    public String getIsbn() { return isbn;}

    public int getPageCount() {return pageCount;}

    public String getEdition() {return edition;}

    public List<String> getAuthor() {return author;}

    @Override
    public String getMainCreator() {
        if (author.isEmpty()) {
            return "Unknown Author";
        }

        return author.get(0);
    }

    @Override
    public List<DetailField> getDetailFields() {
        List<DetailField> fields = new ArrayList<>();

        fields.add(new DetailField("Type", getTypeName()));
        fields.add(new DetailField("Author", getMainCreator()));
        fields.add(new DetailField("Release Year", String.valueOf(getReleaseYear())));
        fields.add(new DetailField("Category", getGenre()));
        fields.add(new DetailField("ISBN", isbn));
        fields.add(new DetailField("Page Count", String.valueOf(pageCount)));
        fields.add(new DetailField("Edition", edition));

        return fields;
    }
}
