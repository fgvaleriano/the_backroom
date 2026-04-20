package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.GameMode;

import java.util.HashMap;

public interface GameModeDao {
    Integer addGameMode(GameMode gameModel);
    HashMap<String, GameMode> getAllGameMode();
    void getMediaGameMode();
}
