package edu.tangingina.thebackroom.dao.impl;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.MediaDao;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaDaoImpl implements MediaDao {
    @Override
    public void findMedia() {
        //We do not need this na since we are caching all of the database info we have...
    }

    @Override
    public HashMap<Integer, Media> getAllMedia() throws Exception {
        String query = "Select * from media";
        HashMap<Integer, Media> mediaList = new HashMap<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                String mediaType = rs.getString("media_type");
                int id = rs.getInt("media_id");

                Media media = new Media(id, rs.getString("name"), MediaType.getType(rs.getString("media_type")),
                        rs.getString("release_year"),rs.getString("synopsis"), rs.getString("icon_path"),
                        getMediaAccess(id), getMediaGenre(id));

                if(mediaType.equals("Book")){
                    media.setBookDetails(rs.getString("isbn"), rs.getString("page_count"),
                            rs.getString("edition"), getMediaPersonnel(id), getMediaCompany(id));
                }else if(mediaType.equals("Movie")){
                    media.setMovieDetails(rs.getString("duration"), rs.getString("language"), getMediaPersonnel(id), getMediaCompany(id));
                }else if(mediaType.equals("TvShow")){
                    media.setTvShowDetails(rs.getString("season_count"), rs.getString("episode_count"),
                            rs.getString("status"), getMediaPersonnel(id), getMediaCompany(id));
                }else if(mediaType.equals("Game")){
                    media.setGameDetails(rs.getString("game_engine"), rs.getString("system_requirements"),
                            getMediaPersonnel(id), getMediaCompany(id), getMediaGamePlatform(id), getMediaGameMode(id));
                }

                mediaList.put(media.getID(), media);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

        return mediaList;
    }

    @Override
    public ArrayList<Integer> getMediaByCategory(String category) throws Exception {
        String query = "Select media_id from media where media_type = ?";
        ArrayList<Integer> mediaCategory = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, category);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                mediaCategory.add(rs.getInt("media_id"));
            }
        }catch (Exception e){
            throw e;
        }

        return mediaCategory;
    }

    @Override
    public ArrayList<Integer> getMediaByCategory(String category, String category2) throws Exception {
        String query = "Select media_id from media where media_type = ? or media_type = ?";
        ArrayList<Integer> mediaCategory = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, category);
            stm.setString(2, category2);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                mediaCategory.add(rs.getInt("media_id"));
            }
        }catch (Exception e){
            throw e;
        }

        return mediaCategory;
    }

    @Override
    public ArrayList<String> getTopMediaCategory(String category) throws Exception {
        String query = "SELECT category_name FROM media_category_overview WHERE media_type = ? GROUP BY category_name ORDER BY count(category_name) DESC";
        ArrayList<String> topGenre = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, category);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                topGenre.add(rs.getString("category_name"));
            }

        } catch (Exception e) {
            throw e;
        }
        return topGenre;
    }

    @Override
    public ArrayList<String> getTopMediaCategory(String category1, String category2) throws Exception {
        String query = "SELECT category_name FROM media_category_overview WHERE media_type = ? or media_type = ? GROUP BY category_name ORDER BY count(category_name) DESC";
        ArrayList<String> topGenre = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setString(1, category1);
            stm.setString(2, category2);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                topGenre.add(rs.getString("category_name"));
            }

        } catch (Exception e) {
            throw e;
        }
        return topGenre;
    }

    @Override
    public ArrayList<Category> getMediaGenre(int id) throws Exception {
        ArrayList<Category> categoryList = new ArrayList<>();
        String query = "Select c.category_id, c.name from media_category natural join category c where media_id = ?";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                categoryList.add(new Category(rs.getInt("category_id"), rs.getString("name")));
            }
        }catch (Exception e){
            throw e;
        }

        return categoryList;
    }

    @Override
    public ArrayList<Website> getMediaAccess(int id) throws Exception {
        ArrayList<Website> websiteList = new ArrayList<>();
        String query = "Select w.website_id, w.name, m.url from media_access as m join website w using(website_id) where media_id = ?";

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                websiteList.add(new Website(rs.getInt("website_id"), rs.getString("name"), rs.getString("url")));
            }
        }catch (Exception e){
            throw e;
        }

        return websiteList;
    }

    @Override
    public ArrayList<Company> getMediaCompany(int id) throws Exception {
        String query = "Select c.company_id, c.name, r.role_id, r.name from media_company join company as c using(company_id) join role as r using(role_id) where media_id = ?";
        ArrayList<Company> companyList = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                companyList.add(new Company(rs.getInt("company_id"), rs.getString(2), rs.getString(4), rs.getInt(3)));
            }
        }catch (Exception e){
            throw e;
        }

        return companyList;
    }

    @Override
    public ArrayList<Person> getMediaPersonnel(int id) throws Exception{
        String query = "Select p.person_id, p.name, r.role_id, r.name from media_personnel join person as p using(person_id) join role as r using(role_id) where media_id = ?";
        ArrayList<Person> personList = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                personList.add(new Person(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(3)));
            }
        }catch (Exception e){
            throw e;
        }

        return personList;
    }

    @Override
    public ArrayList<GameMode> getMediaGameMode(int id) throws Exception{
        String query = "Select m.mode_id, m.name from media_game_mode join mode as m using(mode_id) where media_id = ?";
        ArrayList<GameMode> gameModeList = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                gameModeList.add(TheBackroom.gameModeList.get(rs.getString(2)));
            }
        }catch (Exception e){
            throw e;
        }

        return gameModeList;
    }

    @Override
    public ArrayList<Platform> getMediaGamePlatform(int id) throws Exception{
        String query = "Select p.platform_id, p.name from media_game_platform join platform as p using(platform_id) where media_id = ?";
        ArrayList<Platform> gamePlatformList = new ArrayList<>();

        try{
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                gamePlatformList.add(TheBackroom.platformList.get(rs.getString(2)));
            }
        }catch (Exception e){
            throw e;
        }

        return gamePlatformList;
    }

    @Override
    public void addMedia(Media media) throws Exception{
        MediaType mediaType = media.getMediaType();
        String query = null;

        try{
            DatabaseManager.conn.setAutoCommit(false);
            if(mediaType == MediaType.Book){
                query = "Insert into media(name, release_year, synopsis, media_type, icon_path, isbn, page_count, edition) values(?,?,?,?,?,?,?,?)";

                try{
                    PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, media.getMediaName());
                    stm.setString(2, media.getReleaseYear());
                    stm.setString(3, media.getSynopsis());
                    stm.setString(4, media.getMediaType().toString());
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
            }else if(mediaType == MediaType.Movie){
                query = "Insert into media(name, release_year, synopsis, media_type, icon_path, duration, language) values(?,?,?,?,?,?,?)";

                try{
                    PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, media.getMediaName());
                    stm.setString(2, media.getReleaseYear());
                    stm.setString(3, media.getSynopsis());
                    stm.setString(4, media.getMediaType().toString());
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
            }else if(mediaType == MediaType.TvShow){
                query = "Insert into media(name, release_year, synopsis, media_type, icon_path, season_count, episode_count, status) values(?,?,?,?,?,?,?,?)";

                try{
                    PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, media.getMediaName());
                    stm.setString(2, media.getReleaseYear());
                    stm.setString(3, media.getSynopsis());
                    stm.setString(4, media.getMediaType().toString());
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
            }else if(mediaType == MediaType.Game){
                query = "Insert into media(name, release_year, synopsis, media_type, icon_path, game_engine, system_requirements) values(?,?,?,?,?,?,?)";

                try{
                    PreparedStatement stm = DatabaseManager.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, media.getMediaName());
                    stm.setString(2, media.getReleaseYear());
                    stm.setString(3, media.getSynopsis());
                    stm.setString(4, media.getMediaType().toString());
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
            DatabaseManager.conn.commit();
            System.out.println("Update successful and committed!");
            DatabaseManager.conn.setAutoCommit(true);

        }catch (Exception e){
            try {
                //we rollback if there was an error
                DatabaseManager.conn.rollback();
            } catch (Exception e1) {
                throw e1;
            }
            throw e;
        }


    }

    @Override
    public void addMediaPersonnel(Media media) throws Exception{
        if(media.getMediaPersonnel() == null) return;
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
            throw e;
        }
    }

    @Override
    public void addMediaCompany(Media media) throws Exception{
        if(media.getMediaCompany() == null) return;
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
            throw e;
        }
    }

    @Override
    public void addMediaGenre(Media media) throws Exception{
        if(media.getMediaGenres() == null) return;
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
            throw e;
        }
    }

    @Override
    public void addMediaAccess(Media media) throws Exception{
        if(media.getOnlineAccess() == null) return;
        String query = "Insert into media_access(media_id, website_id, url) values (?, ?, ?)";

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
            throw e;
        }
    }

    @Override
    public void addMediaGameMode(Media media) throws Exception{
        if(media.getGameMode() == null) return;
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
            throw e;
        }
    }

    @Override
    public void addMediaGamePlatform(Media media) throws Exception{
        if(media.getGamePlatform() == null) return;
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
            throw e;
        }
    }

    @Override
    public void updateMedia(Media media, Media oldMedia) throws Exception{
        MediaType mediaType = media.getMediaType();
        String query = null;

        try {
            DatabaseManager.conn.setAutoCommit(false);

            if (mediaType == MediaType.Book) {
                query = "UPDATE media SET name = ?, release_year = ?, synopsis = ?, media_type = ?, icon_path = ?, isbn = ?, page_count = ?, edition = ? WHERE media_id = ?;";

                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);

                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType().toString());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getISBN());
                stm.setString(7, media.getPageCount());
                stm.setString(8, media.getEdition());
                stm.setInt(9, media.getID());
                stm.executeUpdate();

            } else if (mediaType == MediaType.Movie) {
                query = "UPDATE media SET name = ?, release_year = ?, synopsis = ?, media_type = ?, icon_path = ?, duration = ?, language = ? WHERE media_id = ?";

                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);

                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType().toString());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getDuration());
                stm.setString(7, media.getLanguage());
                stm.setInt(8, media.getID());
                stm.executeUpdate();

            } else if (mediaType == MediaType.TvShow) {
                query = "UPDATE media SET name = ?, release_year = ?, synopsis = ?, media_type = ?, icon_path = ?, season_count = ?, episode_count = ?, status = ? WHERE media_id = ?";

                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);

                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType().toString());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getSeasonCount());
                stm.setString(7, media.getEpisodeCount());
                stm.setString(8, media.getStatus());
                stm.setInt(9, media.getID());
                stm.executeUpdate();

            } else if (mediaType == MediaType.Game) {
                query = "UPDATE media SET name = ?, release_year = ?, synopsis = ?, media_type = ?, icon_path = ?, game_engine = ?, system_requirements = ? WHERE media_id = ?";

                PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);

                stm.setString(1, media.getMediaName());
                stm.setString(2, media.getReleaseYear());
                stm.setString(3, media.getSynopsis());
                stm.setString(4, media.getMediaType().toString());
                stm.setString(5, media.getMediaIcon());
                stm.setString(6, media.getGameEngine());
                stm.setString(7, media.getSystemRequirements());
                stm.setInt(8, media.getID());
                stm.executeUpdate();

                //=========Update on the Game Mode=========//
                ArrayList<GameMode> oldGameMode = oldMedia.getGameMode();
                ArrayList<GameMode> newGameMode = media.getGameMode();

                for (GameMode oldGm : oldGameMode) {
                    boolean stillExists = newGameMode.stream()
                            .anyMatch(newGm -> newGm.getGameModeID() == oldGm.getGameModeID());
                    if (!stillExists) {
                        removeMediaGameMode(media.getID(), oldGm);
                    }
                }

                //we add only for the new things which we will remove things that already exists
                ArrayList<GameMode> tempGameMode = new ArrayList<>();
                for (GameMode newGm : newGameMode) {
                    boolean isNew = oldGameMode.stream()
                            .noneMatch(oldGm -> oldGm.getGameModeID() == newGm.getGameModeID());
                    if (isNew) {
                        tempGameMode.add(newGm);
                    }
                }
                media.setGameMode(tempGameMode);
                addMediaGameMode(media);
                media.setGameMode(newGameMode);

                //=========Update on the Game Platform=========//
                ArrayList<Platform> oldPlatform = oldMedia.getGamePlatform();
                ArrayList<Platform> newPlatform = media.getGamePlatform();

                for (Platform oldP : oldPlatform) {
                    boolean stillExists = newPlatform.stream()
                            .anyMatch(newP -> newP.getPlatformID() == oldP.getPlatformID());
                    if (!stillExists) removeMediaGamePlatform(media.getID(), oldP);
                }

                ArrayList<Platform> tempPlatform = new ArrayList<>();
                for (Platform newP : newPlatform) {
                    boolean isNew = oldPlatform.stream()
                            .noneMatch(oldP -> oldP.getPlatformID() == newP.getPlatformID());
                    if (isNew) tempPlatform.add(newP);
                }
                media.setGamePlatform(tempPlatform);
                addMediaGamePlatform(media);
                media.setGamePlatform(newPlatform);
            }

            //=========Update on the Media Personnel=========//
            ArrayList<Person> oldPersonnel = oldMedia.getMediaPersonnel();
            ArrayList<Person> newPersonnel = media.getMediaPersonnel();

            for (Person oldP : oldPersonnel) {
                // Matching by Person ID AND Role ID
                boolean stillExists = newPersonnel.stream()
                        .anyMatch(newP -> newP.getPersonID() == oldP.getPersonID() && newP.getPersonRoleId() == oldP.getPersonRoleId());
                if (!stillExists) removeMediaPersonnel(media.getID(), oldP);
            }

            ArrayList<Person> tempPersonnel = new ArrayList<>();
            for (Person newP : newPersonnel) {
                boolean isNew = oldPersonnel.stream()
                        .noneMatch(oldP -> oldP.getPersonID() == newP.getPersonID() && oldP.getPersonRoleId() == newP.getPersonRoleId());
                if (isNew) tempPersonnel.add(newP);
            }
            media.setMediaPersonnel(tempPersonnel);
            addMediaPersonnel(media);
            media.setMediaPersonnel(newPersonnel);

            //=========Update on the Media Company=========//
            ArrayList<Company> oldCompany = oldMedia.getMediaCompany();
            ArrayList<Company> newCompany = media.getMediaCompany();

            for (Company oldC : oldCompany) {
                // Matching by Company ID AND Role ID (since Company has Publisher roles etc.)
                boolean stillExists = newCompany.stream()
                        .anyMatch(newC -> newC.getCompanyID() == oldC.getCompanyID() && newC.getCompanyRoleId() == oldC.getCompanyRoleId());
                if (!stillExists) removeMediaCompany(media.getID(), oldC);
            }

            ArrayList<Company> tempCompany = new ArrayList<>();
            for (Company newC : newCompany) {
                boolean isNew = oldCompany.stream()
                        .noneMatch(oldC -> oldC.getCompanyID() == newC.getCompanyID() && oldC.getCompanyRoleId() == newC.getCompanyRoleId());
                if (isNew) tempCompany.add(newC);
            }
            media.setMediaCompany(tempCompany);
            addMediaCompany(media);
            media.setMediaCompany(newCompany);

            //=========Update on the Media Genre=========//
            ArrayList<Category> oldCategory = oldMedia.getMediaGenres();
            ArrayList<Category> newCategory = media.getMediaGenres();

            for (Category oldCat : oldCategory) {
                boolean stillExists = newCategory.stream()
                        .anyMatch(newCat -> newCat.getCategoryID() == oldCat.getCategoryID());
                if (!stillExists) removeMediaGenre(media.getID(), oldCat);
            }

            ArrayList<Category> tempCategory = new ArrayList<>();
            for (Category newCat : newCategory) {
                boolean isNew = oldCategory.stream()
                        .noneMatch(oldCat -> oldCat.getCategoryID() == newCat.getCategoryID());
                if (isNew) tempCategory.add(newCat);
            }
            media.setMediaGenres(tempCategory);
            addMediaGenre(media);
            media.setMediaGenres(newCategory);


            //=========Update on the Media Online Access=========//
            ArrayList<Website> oldWebsite = oldMedia.getOnlineAccess();
            ArrayList<Website> newWebsite = media.getOnlineAccess();

            for (Website oldW : oldWebsite) {
                boolean stillExists = newWebsite.stream()
                        .anyMatch(newW -> newW.getWebsiteID() == oldW.getWebsiteID());
                if (!stillExists) removeMediaAccess(media.getID(), oldW);
            }

            ArrayList<Website> tempWebsite = new ArrayList<>();
            for (Website newW : newWebsite) {
                boolean isNew = oldWebsite.stream()
                        .noneMatch(oldW -> oldW.getWebsiteID() == newW.getWebsiteID());
                if (isNew) tempWebsite.add(newW);
            }
            media.setOnlineAccess(tempWebsite);
            addMediaAccess(media);
            media.setOnlineAccess(newWebsite);

            DatabaseManager.conn.commit();
            System.out.println("Update successful and committed!");
            DatabaseManager.conn.setAutoCommit(true);

        } catch (Exception e) {
            try {
                //we rollback if there was an error
                DatabaseManager.conn.rollback();
                DatabaseManager.conn.setAutoCommit(true);
            } catch (Exception e1) {
                throw e1;
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void removeMediaPersonnel(int mediaId, Person person) throws Exception{
        String query = "DELETE FROM media_personnel WHERE media_id = ? AND person_id = ? AND role_id = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, person.getPersonID());
            stm.setInt(3, person.getPersonRoleId());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeMediaCompany(int mediaId, Company company) throws Exception {
        String query = "DELETE FROM media_company WHERE media_id = ? AND company_id = ? AND role_id = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, company.getCompanyID());
            stm.setInt(3, company.getCompanyRoleId());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeMediaGenre(int mediaId, Category category) throws Exception {
        String query = "DELETE FROM media_category WHERE media_id = ? AND category_id = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, category.getCategoryID());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeMediaAccess(int mediaId, Website website) throws Exception {
        String query = "DELETE FROM media_access WHERE media_id = ? AND website_id = ? AND url = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, website.getWebsiteID());
            stm.setString(3, website.getWebsiteURL());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeMediaGameMode(int mediaId, GameMode mode) throws Exception {
        String query = "DELETE FROM media_game_mode WHERE media_id = ? AND mode_id = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, mode.getGameModeID());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void removeMediaGamePlatform(int mediaId, Platform platform) throws Exception {
        String query = "DELETE FROM media_game_platform WHERE media_id = ? AND platform_id = ?";

        try {
            PreparedStatement stm = DatabaseManager.conn.prepareStatement(query);
            stm.setInt(1, mediaId);
            stm.setInt(2, platform.getPlatformID());
            stm.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }
}
