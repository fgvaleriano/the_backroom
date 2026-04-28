package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public interface MediaDao {
    void findMedia();
    HashMap<Integer, Media> getAllMedia() throws Exception;
    ArrayList<Integer> getMediaByCategory(String category) throws Exception;
    ArrayList<Integer> getMediaByCategory(String category1, String category2) throws Exception;
    ArrayList<String> getTopMediaCategory(String category) throws Exception;
    ArrayList<String> getTopMediaCategory(String category1, String category2) throws Exception;
    ArrayList<Category> getMediaGenre(int id) throws Exception;
    ArrayList<Website> getMediaAccess(int id) throws Exception;
    ArrayList<Company> getMediaCompany(int id) throws Exception;
    ArrayList<Person> getMediaPersonnel(int id) throws Exception;
    ArrayList<GameMode> getMediaGameMode(int id) throws Exception;
    ArrayList<Platform> getMediaGamePlatform(int id) throws Exception;
    void addMedia(Media media) throws Exception;
    void addMediaPersonnel(Media media) throws Exception;
    void addMediaCompany(Media media) throws Exception;
    void addMediaGenre(Media media) throws Exception;
    void addMediaAccess(Media media) throws Exception;
    void addMediaGameMode(Media media) throws Exception;
    void addMediaGamePlatform(Media media) throws Exception;
    void updateMedia(Media media, Media oldMedia) throws Exception;
    void removeMediaPersonnel(int mediaId, Person person) throws Exception;
    void removeMediaCompany(int mediaId, Company company) throws Exception;
    void removeMediaGenre(int mediaId, Category category) throws Exception;
    void removeMediaAccess(int mediaId, Website website) throws Exception;
    void removeMediaGameMode(int mediaId, GameMode mode) throws Exception;
    void removeMediaGamePlatform(int mediaId, Platform platform) throws Exception;

}
