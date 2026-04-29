package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.ArrayList;
import java.util.List;

public class GameMedia extends BaseMedia{

    private final String gameEngine, mode, systemReqs;
    private final List<String> platform, devs;

    public GameMedia(int id, String title, String synopsis, int releaseYear, String filePath,
                     List<String> categories, List<String> accessLink, String gameEngine, String mode,
                     String systemReqs, List<String> platform, List<String> devs) {

        super (id, title, MediaType.GAME, synopsis, releaseYear, filePath, categories, accessLink);
        this.gameEngine = gameEngine;
        this.mode = mode;
        this.systemReqs = systemReqs;
        this.platform = platform == null ? new ArrayList<>() : new ArrayList<>(platform);
        this.devs = devs == null ? new ArrayList<>() : new ArrayList<>(devs);
    }

    public String getGameEngine() {return gameEngine;}

    public String getMode() {return mode;}
    public String getSystemReqs() {return systemReqs;}
    public List<String> getPlatform() {return platform;}
    public List<String> getDevs() {return devs;}

    @Override
    public String getMainCreator() {
        if (devs.isEmpty()) {
            return "Unknown Developer";
        }

        return devs.get(0);
    }

    @Override
    public List<DetailField> getDetailFields() {
        List<DetailField> fields = new ArrayList<>();

        fields.add(new DetailField("Type", getTypeName()));
        fields.add(new DetailField("Developer", getMainCreator()));
        fields.add(new DetailField("Release Year", String.valueOf(getReleaseYear())));
        fields.add(new DetailField("Category", getGenre()));
        fields.add(new DetailField("Game Engine", gameEngine));
        fields.add(new DetailField("Mode", mode));
        fields.add(new DetailField("Platforms", String.join(", ", platform)));
        fields.add(new DetailField("System Requirements", systemReqs));

        return fields;
    }
}
