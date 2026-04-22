package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface MediaDao {
    void findMedia();
    HashMap<Integer, Media> getAllMedia();
    ArrayList<Category> getMediaGenre(int id);
    ArrayList<Website> getMediaAccess(int id);
    ArrayList<Company> getMediaCompany(int id);
    ArrayList<Person> getMediaPersonnel(int id);
    ArrayList<GameMode> getMediaGameMode(int id);
    ArrayList<Platform> getMediaGamePlatform(int id);
    void addMedia(Media media);
    void addMediaPersonnel(Media media);
    void addMediaCompany(Media media);
    void addMediaGenre(Media media);
    void addMediaAccess(Media media);
    void addMediaGameMode(Media media);
    void addMediaGamePlatform(Media media);
    void updateMedia(Media media, Media oldMedia);
    void removeMediaPersonnel(int mediaId, Person person) throws Exception;
    void removeMediaCompany(int mediaId, Company company) throws Exception;
    void removeMediaGenre(int mediaId, Category category) throws Exception;
    void removeMediaAccess(int mediaId, Website website) throws Exception;
    void removeMediaGameMode(int mediaId, GameMode mode) throws Exception;
    void removeMediaGamePlatform(int mediaId, Platform platform) throws Exception;

}
