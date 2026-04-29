package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.*;

public class TVShowMedia extends BaseMedia{
    private final int seasonCnt, epCount;
    private final String status;
    private final List<String> creators;

    public TVShowMedia(int id, String title, String synopsis, int releaseYear, String filePath,
                       List<String> categories, List<String>accessLink, int seasonCnt, String status,
                       int epCount, List<String> creators) {

        super (id, title, MediaType.TV_SHOW, synopsis, releaseYear, filePath, categories, accessLink);
        this.seasonCnt = seasonCnt;
        this.epCount = epCount;
        this.creators = creators = creators == null ? new ArrayList<>() : new ArrayList<>(creators);
        this.status = status;
    }

    public int getSeasonCnt() {return seasonCnt;}
    public int getEpCount() {return epCount;}
    public String getStatus() {return status;}

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
        fields.add(new DetailField("Seasons", String.valueOf(seasonCnt)));
        fields.add(new DetailField("Episodes", String.valueOf(epCount)));
        fields.add(new DetailField("Status", status));

        return fields;
    }
}
