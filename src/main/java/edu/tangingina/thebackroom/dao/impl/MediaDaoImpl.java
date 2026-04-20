package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.dao.MediaDao;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MediaDaoImpl implements MediaDao {
    @Override
    public void findMedia() {

    }

    @Override
    public void getMedia() {

    }

    @Override
    public void addMedia(Media media) {
        String mediaType = media.getMediaType();
        String query = null;

        if(mediaType.equals("Book")){
            query = "Insert into media(name, release_year, synopsis, media_type, icon_path, isbn, page_count, edition) values(?,?,?,?,?,?,?,?)";

            try{
                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getISBN());
                stm.setString(7, media.getPageCount());
                stm.setString(8, media.getEdition());
                stm.executeUpdate();
                ResultSet rs = stm.getGeneratedKeys();

                if(rs.next()){
                    media.setID(rs.getInt(1));
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else if(mediaType.equals("Movie")){
            query = "Insert into media(name, release_year, synopsis, media_type, icon_path, duration, language) values(?,?,?,?,?,?,?)";

            try{
                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getDuration());
                stm.setString(7, media.getLanguage());
                stm.executeUpdate();
                ResultSet rs = stm.getGeneratedKeys();

                if(rs.next()){
                    media.setID(rs.getInt(1));
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else if(mediaType.equals("TvShow")){
            query = "Insert into media(name, release_year, synopsis, media_type, icon_path, season_count, episode_count, status) values(?,?,?,?,?,?,?,?)";

            try{
                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getSeasonCount());
                stm.setString(7, media.getEpisodeCount());
                stm.setString(8, media.getStatus());
                stm.executeUpdate();
                ResultSet rs = stm.getGeneratedKeys();

                if(rs.next()){
                    media.setID(rs.getInt(1));
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else if(mediaType.equals("Game")){
            query = "Insert into media(name, release_year, synopsis, media_type, icon_path, game_engine, system_requirements) values(?,?,?,?,?,?,?)";

            try{
                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getGameEngine());
                stm.setString(7, media.getSystemRequirements());
                stm.executeUpdate();
                ResultSet rs = stm.getGeneratedKeys();

                if(rs.next()){
                    media.setID(rs.getInt(1));
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            addMediaGameMode(media);
            addMediaGamePlatform(media);
        }

        addMediaPersonnel(media);
        addMediaCompany(media);
        addMediaGenre(media);
        addMediaAccess(media);
    }

    @Override
    public void addMediaPersonnel(Media media) {
        String query = "Insert into media_personnel(media_id, person_id, role_id) values (?, ?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<Person> mediaPersonnel = media.getMediaPersonnel();

            for(Person person : mediaPersonnel){
                stm.setInt(2, person.getPersonID());
                stm.setInt(3, person.getPersonRoleId());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMediaCompany(Media media) {
        String query = "Insert into media_company(media_id, company_id, role_id) values (?, ?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<Company> mediaCompany = media.getMediaCompany();

            for(Company company : mediaCompany){
                stm.setInt(2, company.getCompanyID());
                stm.setInt(3, company.getCompanyRoleId());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMediaGenre(Media media) {
        String query = "Insert into media_category(media_id, category_id) values (?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<Category> mediaCategory = media.getMediaGenres();

            for(Category category : mediaCategory){
                stm.setInt(2, category.getCategoryID());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMediaAccess(Media media) {
        String query = "Insert into media_access(media_id, website_id, url ) values (?, ?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<Website> mediaAccess = media.getOnlineAccess();

            for(Website website : mediaAccess){
                stm.setInt(2, website.getWebsiteID());
                stm.setString(3, website.getWebsiteURL());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMediaGameMode(Media media) {
        String query = "Insert into media_game_mode(media_id, mode_id) values (?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<GameMode>  mediaGameMode = media.getGameMode();

            for(GameMode gameMode : mediaGameMode){
                stm.setInt(2, gameMode.getGameModeID());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMediaGamePlatform(Media media) {
        String query = "Insert into media_game_platform(media_id, platform_id) values (?, ?)";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, media.getID());
            ArrayList<Platform>  mediaGamePlatform = media.getGamePlatform();

            for(Platform gamePlatform : mediaGamePlatform){
                stm.setInt(2, gamePlatform.getPlatformID());
                stm.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateMedia() {

    }
}
