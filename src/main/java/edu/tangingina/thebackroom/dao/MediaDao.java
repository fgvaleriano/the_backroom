package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Media;

public interface MediaDao {
    void findMedia();
    void getMedia();
    void addMedia(Media media);
    void addMediaPersonnel(Media media);
    void addMediaCompany(Media media);
    void addMediaGenre(Media media);
    void addMediaAccess(Media media);
    void addMediaGameMode(Media media);
    void addMediaGamePlatform(Media media);
    void updateMedia();

}
