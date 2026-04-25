package edu.tangingina.thebackroom.model;

public class GameMode {
    private int id;
    private String gameModeName;

    public GameMode(int id, String name){
        this.id = id;
        this.gameModeName = name;
    }

    public Integer getGameModeID(){
        return id;
    }

    public String getGameModeName(){
        return gameModeName;
    }
    public void setGameModeID(int id){this.id = id;}
}
