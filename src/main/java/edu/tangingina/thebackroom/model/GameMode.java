package edu.tangingina.thebackroom.model;

public class GameMode {
    private int id;
    private String gameModelName;

    public GameMode(int id, String name){
        this.id = id;
        this.gameModelName = name;
    }

    public Integer getGameModeID(){
        return id;
    }

    public String getGameModeName(){
        return gameModelName;
    }
}
