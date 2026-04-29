package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.*;

public class FilmMedia extends BaseMedia{
    private final String duration, language;
    private final List<String> creators;

    public FilmMedia(int id, String title, String synopsis, int releaseYear, String filePath,
                     List<String> categories, List<String> accessLink, String duration, String language,
                     List<String> creators) {
        super(id, title, MediaType.MOVIE, synopsis, releaseYear, filePath,categories, accessLink);
        this.duration = duration;
        this.language = language;
        this.creators = creators == null ? new ArrayList<>() : new ArrayList<>(creators);
    }

    public String getDuration() {return duration;}
    public String getLanguage() {return language;}

    @Override
    public String getMainCreator() {
        if (creators.isEmpty()) {
            return "Unknown Creator";
        }

        return creators.get(0);
    }

    @Override
    public List<DetailField> getDetailFields() {
        List<DetailField> fields = new ArrayList<>();

        fields.add(new DetailField("Type", getTypeName()));
        fields.add(new DetailField("Creator", getMainCreator()));
        fields.add(new DetailField("Release Year", String.valueOf(getReleaseYear())));
        fields.add(new DetailField("Category", getGenre()));
        fields.add(new DetailField("Duration", duration));
        fields.add(new DetailField("Language", language));

        return fields;
    }
}
