package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Platform;

import java.util.HashMap;

public interface PlatformDao {
    Integer addPlatform(Platform platform);
    HashMap<String, Platform> getAllPlatform();
    void getMediaPlatform();
}
