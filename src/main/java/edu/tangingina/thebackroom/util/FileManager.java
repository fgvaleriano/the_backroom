package edu.tangingina.thebackroom.util;

import com.google.gson.Gson;
import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.*;
import edu.tangingina.thebackroom.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

import static edu.tangingina.thebackroom.TheBackroom.mediaUniqID;
import static edu.tangingina.thebackroom.TheBackroom.util;

public class FileManager {
    private CategoryDaoImpl categoryDao =  TheBackroom.categoryDao;
    private WebsiteDaoImpl websiteDao = TheBackroom.websiteDao;
    private PersonDaoImpl personDao = TheBackroom.personDao;
    private CompanyDaoImpl companyDao = TheBackroom.companyDao;
    private RoleDaoImpl roleDao = TheBackroom.roleDao;
    private MediaDaoImpl mediaDao = TheBackroom.mediaDao;
    private PlatformDaoImpl platformDao = TheBackroom.platformDao;
    private GameModeDaoImpl gameModeDao = TheBackroom.gameModeDao;


    private HashMap<String, Category> categoryList = TheBackroom.categoryList;
    private HashMap<String, Website> websiteList = TheBackroom.websiteList;
    private HashMap<String, Person> personList = TheBackroom.personList;
    private HashMap<String, Company> companyList = TheBackroom.companyList;
    private HashMap<String, Role> roleList = TheBackroom.roleList;
    private HashMap<String, Platform> platformList = TheBackroom.platformList;
    private HashMap<String, GameMode> gameModeList = TheBackroom.gameModeList;
    private HashMap<Integer, Media> mediaList = TheBackroom.mediaList;

    private ImportUtility iu = new ImportUtility();

    public File openFile(Stage stage, String description, String fileType){
        FileChooser selectFile = new FileChooser();

        //this would make only csv files to be opened... (filterer)
        selectFile.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(description, fileType)
        );

        File file = selectFile.showOpenDialog(stage);
        return file;

    }

    public void importCSV(String path) throws Exception{

        File file = new File(path);

        if(file != null){
            int successCount = 0;
            int errorCount = 0;
            int totalCount = 0;
            ArrayList<Media> duplicateMedia = new ArrayList<>();

            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String currLine;

                while((currLine = br.readLine()) != null){
                    if (currLine.trim().isEmpty()) continue;
                    try{
                        String [] line = currLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                        totalCount++;

                        //format(name, mediaType, synopsis, release year, icon path, genre, website) - for generic
                        String name = line[0];
                        if (name.startsWith("\"") && name.endsWith("\"")) {
                            name = name.substring(1, name.length() - 1);
                        }
                        MediaType mediaType = MediaType.getType(line[1]);
                        String synopsis = line[2];

                        //we remove the quotation at the first and last lines due to the format of csv
                        if (synopsis.startsWith("\"") && synopsis.endsWith("\"")) {
                            synopsis = synopsis.substring(1, synopsis.length() - 1);
                        }
                        //replace all double qotation to single quotation
                        synopsis = synopsis.replace("\"\"", "\"");

                        String releaseYear = line[3];
                        String icon = line[4];

                        if (icon.startsWith("\"") && icon.endsWith("\"")) {
                            icon = icon.substring(1, icon.length() - 1);
                        }

                        String[] category = line[5].split(";");

                        String websiteColumn = line[6].trim();
                        if (websiteColumn.startsWith("\"") && websiteColumn.endsWith("\"")) {
                            websiteColumn = websiteColumn.substring(1, websiteColumn.length() - 1);
                        }
                        String[] website = websiteColumn.split(";");


                        if(name == null || name.isEmpty() ||mediaType == null) {
                            errorCount++;
                            continue;
                        }

                        if(icon != null){
                            icon = saveIMGRelative(icon);
                        }

                        if(mediaType == MediaType.Book){
                            if(line.length < 12) {
                                errorCount++;
                                continue;
                            }
                        }else if(mediaType == MediaType.Movie){
                            if(line.length < 11) {
                                errorCount++;
                                continue;
                            }
                        }else if(mediaType == MediaType.TvShow){
                            if(line.length < 12) {
                                errorCount++;
                                continue;
                            }
                        }else if(mediaType == MediaType.Game){
                            if(line.length < 14) {
                                errorCount++;
                                continue;
                            }
                        }


                        ArrayList<Website> onlineAccess = new ArrayList<>();
                        ArrayList<Category> genre = new ArrayList<>();


                        if(website != null){
                            for (String websiteName : website) {
                                if (websiteName.trim().isEmpty()) continue;

                                String[] parts = websiteName.trim().split(":", 2);

                                if (parts.length < 2) continue; //safety check

                                int id  = 0;

                                if (!websiteList.containsKey(parts[0].trim())) {
                                    id = websiteDao.addWesbite(new Website(0, parts[0].trim(), null));
                                    websiteList.put(parts[0].trim(), new Website(id, parts[0].trim(), null));
                                }else{
                                    id = websiteList.get(parts[0].trim()).getWebsiteID();
                                }

                                onlineAccess.add(new Website(id, parts[0].trim(), parts[1].trim()));
                            }
                        }

                        if(category != null){
                            for (String categoryName : category) {

                                if(categoryName.trim().isEmpty()) continue;
                                int id = 0;

                                if (!categoryList.containsKey(categoryName)) {
                                    //the index here is temporary since the id value is put in the table itself
                                    id = categoryDao.addCategory(new Category(0, categoryName));
                                    categoryList.put(categoryName, new Category(id, categoryName));
                                }else{
                                    id = categoryList.get(categoryName).getCategoryID();
                                }

                                genre.add(new Category(id, categoryName));
                            }
                        }

                        Media media = new Media(0, name, mediaType, releaseYear, synopsis, icon, onlineAccess, genre);

                        //we populate the information here
                        if(mediaType == MediaType.Book){
                            //format(isbn, edition, pageCount, author, publisher)
                            iu.handleBookCSV(line, media);
                        }else if(mediaType == MediaType.Movie){
                            //format(duration, language, director, studio)
                            iu.handleCSVMovie(line, media);
                        }else if(mediaType == MediaType.TvShow){
                            //format(seasonCount, episodeCount, status, director, studio)
                            iu.handleCSVTvShow(line, media);
                        }else if(mediaType == MediaType.Game){
                            //format(gameEngine, systemRequirements, gameMode, gameDev, gameStudio, publisher, platform)
                            iu.handleCSVGame(line, media);
                        }

                        if(util.checkDuplicate(media)){
                            duplicateMedia.add(media);
                            continue;
                        }

                        mediaDao.addMedia(media);
                        mediaList.put(media.getID(), media);
                        mediaUniqID.put(util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());

                        //this is for populaing our cache
                        if(mediaType == MediaType.Book){
                            TheBackroom.bookMedia.add(media.getID());
                        }else if(mediaType == MediaType.Movie){
                            TheBackroom.videoMedia.add(media.getID());
                        }else if(mediaType == MediaType.TvShow){
                            TheBackroom.videoMedia.add(media.getID());
                        }else if(mediaType == MediaType.Game){
                            TheBackroom.gameMedia.add(media.getID());
                        }

                        successCount++;
                    } catch (Exception e) {
                        errorCount++;
                        e.printStackTrace();
                    }
                }

                if(!duplicateMedia.isEmpty()){
                    boolean willUpdate = askToUpdateDuplicates();

                    if(willUpdate){
                        try {
                            for(Media m : duplicateMedia) {

                                String key = util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear());
                                Integer mediaId = TheBackroom.mediaUniqID.get(key);

                                System.out.println("[DEBUG] Processing update for: " + m.getMediaName());
                                System.out.println("[DEBUG] Found existing ID: " + mediaId);

                                if (mediaId == null) {
                                    System.out.println("[DEBUG] WARNING: No ID found for key: " + key + ". Skipping update.");
                                    continue;
                                }

                                m.setID(mediaId);

                                mediaDao.updateMedia(m, TheBackroom.mediaList.get(mediaId));
                                TheBackroom.mediaList.put(m.getID(), m);
                                System.out.println("[DEBUG] mediaDao.updateMedia called successfully.");

                                if(m.getMediaType() == MediaType.Book){
                                    if(!TheBackroom.bookMedia.contains(m.getID())) {
                                        TheBackroom.bookMedia.add(m.getID());
                                    } else {
                                        System.out.println("[DEBUG] ID already in bookMedia, skipping list add.");
                                    }
                                } else if(m.getMediaType() == MediaType.Movie || m.getMediaType() == MediaType.TvShow){
                                    if(!TheBackroom.videoMedia.contains(m.getID())) {
                                        TheBackroom.videoMedia.add(m.getID());
                                    }
                                } else if(m.getMediaType() == MediaType.Game){
                                    if(!TheBackroom.gameMedia.contains(m.getID())) {
                                        TheBackroom.gameMedia.add(m.getID());
                                    }
                                }

                                successCount++;
                            }
                            duplicateMedia.clear();
                        } catch (Exception e) {
                            System.out.println("[ERROR] Update failed: " + e.getMessage());
                            e.printStackTrace();
                            errorCount++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            showImportSummary(totalCount, successCount, duplicateMedia.size(), errorCount);

        }

    }

    public void importJSON(String path) throws Exception{
        Gson gson = new Gson();
        File file = new File(path);

        ArrayList<Media> duplicateMedia = new ArrayList<>();

        if(file != null){
            int successCount = 0;
            int errorCount = 0;
            int totalCount = 0;

            try(FileReader read = new FileReader(file)){
                Media[] media = gson.fromJson(read, Media[].class);
                totalCount = media.length;

                for(Media m: media){
                    try{
                        if(m.getMediaName() == null || m.getMediaName().isEmpty() ||m.getMediaType() == null) {
                            errorCount++;
                            continue;
                        }

                        if(util.checkDuplicate(m)){
                            duplicateMedia.add(m);
                            continue;
                        }
                        MediaType type = m.getMediaType();

                        if(m.getMediaIcon() != null){
                            m.setMediaIcon(saveIMGRelative(m.getMediaIcon()));
                        }

                        ArrayList<Person> mediaPersonnel = m.getMediaPersonnel();
                        ArrayList<Company> mediaCompany = m.getMediaCompany();
                        ArrayList<Category> mediaGenre = m.getMediaGenres();
                        ArrayList<Website>  mediaAccess = m.getOnlineAccess();
                        ArrayList<GameMode> mediaGameMode = m.getGameMode();
                        ArrayList<Platform> mediaGamePlatform = m.getGamePlatform();
                        //This part, we ensure if for each of the many to many relationship of the media, that specific data must exist first in the databse

                        int id = 0;
                        if(mediaPersonnel != null){
                            for(Person p : mediaPersonnel){
                                if(!personList.containsKey(p.getPersonName())){
                                    id = personDao.addPerson(p);
                                    p.setPersonID(id);
                                    personList.put(p.getPersonName(), new Person(id, p.getPersonName(), null, 0));
                                }else{
                                    p.setPersonID(personList.get(p.getPersonName()).getPersonID());
                                }
                                p.setPersonRoleID(roleList.get(p.getPersonRole()).getRoleID());
                            }
                        }

                        if(mediaCompany != null){
                            for(Company c : mediaCompany){
                                if(!companyList.containsKey(c.getCompanyName())){
                                    id = companyDao.addCompany(c);
                                    c.setCompanyID(id);
                                    companyList.put(c.getCompanyName(), new Company(id, c.getCompanyName(), null, 0));
                                }else{
                                    c.setCompanyID(companyList.get(c.getCompanyName()).getCompanyID());
                                }

                                c.setCompanyRoleID(roleList.get(c.getCompanyRole()).getRoleID());
                            }
                        }

                        if(mediaGenre != null){
                            for(Category c : mediaGenre){
                                if(!categoryList.containsKey(c.getCategoryName())){
                                    id = categoryDao.addCategory(c);
                                    c.setCategoryID(id);
                                    categoryList.put(c.getCategoryName(), c);
                                }else{
                                    c.setCategoryID(categoryList.get(c.getCategoryName()).getCategoryID());
                                }
                            }
                        }

                        if(mediaAccess != null){
                            for(Website w : mediaAccess){
                                if(!websiteList.containsKey(w.getWebisteName())){
                                    id = websiteDao.addWesbite(w);
                                    w.setWebsiteID(id);
                                    websiteList.put(w.getWebisteName(), new Website(id, w.getWebisteName(), null));
                                }else{
                                    w.setWebsiteID(websiteList.get(w.getWebisteName()).getWebsiteID());
                                }
                            }

                        }

                        if(type == MediaType.Game){
                            if(mediaGameMode != null){
                                for(GameMode gm : mediaGameMode){
                                    if (gm.getGameModeName() == null || gm.getGameModeName().isEmpty()) {
                                        continue;
                                    }
                                    if(!gameModeList.containsKey(gm.getGameModeName())){
                                        id = gameModeDao.addGameMode(gm);
                                        gm.setGameModeID(id);
                                        gameModeList.put(gm.getGameModeName(), gm);
                                    }else{
                                        gm.setGameModeID(gameModeList.get(gm.getGameModeName()).getGameModeID());
                                    }
                                }
                            }

                            if(mediaGamePlatform != null){
                                for(Platform p : mediaGamePlatform){
                                    if(!platformList.containsKey(p.getPlatformName())){
                                        id = platformDao.addPlatform(p);
                                        p.setPlatformID(id);
                                        platformList.put(p.getPlatformName(), p);
                                    }else{
                                        p.setPlatformID(platformList.get(p.getPlatformName()).getPlatformID());
                                    }
                                }
                            }

                        }

                        mediaDao.addMedia(m);
                        TheBackroom.mediaUniqID.put(util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()), m.getID());
                        TheBackroom.mediaList.put(m.getID(), m);

                        if(m.getMediaType() == MediaType.Book){
                            TheBackroom.bookMedia.add(m.getID());
                        }else if(m.getMediaType() == MediaType.Movie){
                            TheBackroom.videoMedia.add(m.getID());
                        }else if(m.getMediaType() == MediaType.TvShow){
                            TheBackroom.videoMedia.add(m.getID());
                        }else if(m.getMediaType() == MediaType.Game){
                            TheBackroom.gameMedia.add(m.getID());
                        }

                        successCount++;
                    } catch (Exception e) {
                        //e.printStackTrace();
                        errorCount++;
                    }
                }


                if(!duplicateMedia.isEmpty()){
                    boolean willUpdate = askToUpdateDuplicates();

                    if(willUpdate) {
                        try {
                            for (Media m : duplicateMedia) {

                                String key = util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear());
                                Integer mediaId = TheBackroom.mediaUniqID.get(key);

                                System.out.println("[DEBUG] Processing update for: " + m.getMediaName());
                                System.out.println("[DEBUG] Found existing ID: " + mediaId);

                                if (mediaId == null) {
                                    System.out.println("[DEBUG] WARNING: No ID found for key: " + key + ". Skipping update.");
                                    continue;
                                }

                                m.setID(mediaId);

                                mediaDao.updateMedia(m, TheBackroom.mediaList.get(mediaId));
                                TheBackroom.mediaList.put(m.getID(), m);
                                System.out.println("[DEBUG] mediaDao.updateMedia called successfully.");

                                if (m.getMediaType() == MediaType.Book) {
                                    if (!TheBackroom.bookMedia.contains(m.getID())) {
                                        TheBackroom.bookMedia.add(m.getID());
                                    } else {
                                        System.out.println("[DEBUG] ID already in bookMedia, skipping list add.");
                                    }
                                } else if (m.getMediaType() == MediaType.Movie || m.getMediaType() == MediaType.TvShow) {
                                    if (!TheBackroom.videoMedia.contains(m.getID())) {
                                        TheBackroom.videoMedia.add(m.getID());
                                    }
                                } else if (m.getMediaType() == MediaType.Game) {
                                    if (!TheBackroom.gameMedia.contains(m.getID())) {
                                        TheBackroom.gameMedia.add(m.getID());
                                    }
                                }

                                successCount++;
                            }
                            duplicateMedia.clear();
                        } catch (Exception e) {
                            System.out.println("[ERROR] Update failed: " + e.getMessage());
                            e.printStackTrace();
                            errorCount++;
                        }
                    }
                }

                showImportSummary(totalCount, successCount, duplicateMedia.size(), errorCount);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void importSQL(String path) throws Exception {
        File file = new File(path);

        if (file != null) {
            int successCount = 0;
            int errorCount = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder queryBuild = new StringBuilder();
                String currLine;

                try (Statement stmt = DatabaseManager.conn.createStatement()) {

                    while ((currLine = br.readLine()) != null) {
                        String line = currLine.trim();

                        //skip empty lines and then comments
                        if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*")) {
                            continue;
                        }

                        //we then append each line to the query while adding space
                        queryBuild.append(line).append(" ");

                        // if the line ends with a ;, then we exceute the query
                        if (line.endsWith(";")) {
                            String query = queryBuild.toString();

                            try {
                                stmt.executeUpdate(query);
                                successCount++;
                            } catch (Exception e) {
                                System.out.println("Failed Query: " + query);
                                System.out.println("Reason: " + e.getMessage());
                                errorCount++;
                            }
                            //reset the queryBuild for the next query
                            queryBuild.setLength(0);
                        }
                    }
                }
                showImportSummary(successCount, errorCount);

                TheBackroom.resetCache();
                TheBackroom.reloadCache();

            } catch (Exception e) {
                System.out.println("A FATAL ERROR KILLED THE SQL IMPORT!");
                e.printStackTrace();
                throw e; // Throw it back to the UI so you can show an error alert box
            }
        }
    }

    //this is importing IMG by user
    public File importImg(Stage stage){
        FileChooser choose = new FileChooser();

        choose.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG/PNG Files", "*.jpg", "*.jpeg", "*.png")
        );

        File file = choose.showOpenDialog(stage);

        return file;
    }

    public String saveIMGRelative(File file){
        //First we create the directory on our project folder
        File dir = new File("uploads/mediaIcon");

        //if it doesnt exist yeah we create the directory
        if(!dir.exists()){
            dir.mkdirs();
        }

        //Here we get the file extension pra kuan for the naming
        if(file != null){
            int dotIndex = file.getName().indexOf(".");
            String imgExtension = "";
            if(dotIndex > 0){
                imgExtension = file.getName().substring(dotIndex);
            }

            String newFileName = System.currentTimeMillis() + imgExtension;

            File newFile = new File(dir, newFileName);
            try{
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch(Exception e){

            }

            return "uploads/mediaIcon/" + newFileName;
        }

        return null;

    }

    public String saveIMGRelative(String path){
        //First we create the directory on our project folder

        File file = new File(path);
        if(!file.exists()){
            return null;
        }
        File dir = new File("uploads/mediaIcon");

        //if it doesnt exist yeah we create the directory
        if(!dir.exists()){
            dir.mkdirs();
        }

        //Here we get the file extension pra kuan for the naming
        int dotIndex = file.getName().indexOf(".");
        String imgExtension = "";
        if(dotIndex > 0){
            imgExtension = file.getName().substring(dotIndex);
        }

        String newFileName = System.currentTimeMillis() + imgExtension;

        File newFile = new File(dir, newFileName);
        try{
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){

        }

        return "uploads/mediaIcon/" + newFileName;
    }


    public static void exportData (DatabaseManager dm, String filePath, String selectedType) {
        System.out.println("Preparing to save data");

        try {
            dm.getConnection();
            Connection conn = dm.conn;

            String mediaQuery;
            boolean filter = selectedType != null && !selectedType.equals("All Media");
            int mediaCount = 0, personnelCount = 0, companyCount = 0;

            switch (selectedType != null ? selectedType : "All Media") {
                case "Book" :
                    mediaQuery = "select media_id, name, release_year, synopsis, icon_path, isbn, page_count, " +
                            "edition from media where media_type = ?";
                    break;
                case "Movie" :
                    mediaQuery = "select media_id, name, release_year, synopsis, icon_path, duration, language " +
                            "from media where media_type = ?";
                    break;
                case "TvShow" :
                    mediaQuery = "select media_id, name, release_year, synopsis, icon_path, season_count, episode_count " +
                            "status from media where media_type = ?";
                    break;
                case "Game" :
                    mediaQuery = "select m.media_id, m.name, m.release_year, m.synopsis, m.icon_path, m.game_engine, " +
                            "m.system_requirements, group_concat(mo.name order by mo.name separator ';') " +
                            "as modes from media m left join media_game_mode mgm on m.media_id = mgm.media_id " +
                            "left join mode mo on mgm.mode_id = mo.mode_id where m.media_type = ? group by m.media_id";
                    break;
                default:
                    mediaQuery = "select media_id, name, release_year, synopsis, media_type, icon_path from media";
                    break;
            }

            //exporting personnel information
            String personnelExport = "select mp.media_id, p.name as person_name, r.name as role_name from " + "" +
                    "media_personnel mp join person p on mp.person_id = p.person_id join role r on mp.role_id = r.role_id " +
                    (filter ? "where mp.media_id in (select media_id from media where media_type = ?) " : "") +
                    "order by mp.media_id";

            //exporting company informatino
            String companyExport = "select mc.media_id, c.name as company_name, r.name as role_name from media_company mc " +
                    "join company c on mc.company_id = c.company_id join role r on mc.role_id = r.role_id " +
                    (filter ? "where mc.media_id in (select  media_id from media where media_type = ?) " : "") +
                    "order by mc.media_id";

            try (PrintWriter out = new PrintWriter(new File(filePath))) {

                out.println("=========== MEDIA ===========");
                    try (PreparedStatement pstmt = conn.prepareStatement(mediaQuery)) {
                        if (filter) {
                            pstmt.setString(1, selectedType);
                        }

                        ResultSet rs = pstmt.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columns = rsmd.getColumnCount();

                        //for header
                        for (int i = 1; i <= columns; i++) {
                            out.print(rsmd.getColumnName(i) + (i < columns ? "," : ""));
                        }
                        out.println();

                        //for the rows
                        while (rs.next()) {
                            for (int i = 1; i <= columns; i++) {
                                String colName = rsmd.getColumnName(i);
                                String val = rs.getString(i);
                                if (val == null) {
                                    out.print(i < columns ? "," : "");
                                } else {
                                    if (colName.equalsIgnoreCase("isbn")) {
                                        out.print("=\"" + val + "\"");
                                    } else {
                                        out.print(val.replace(",", ";"));
                                    }
                                    out.print(i < columns ? "," : "");
                                }
                            }
                            out.println();
                            mediaCount++;
                        }
                    }

                out.println();
                out.println("=========== PERSONNEL ===========");
                out.println("media_id,person_name,role_name");

                try (PreparedStatement pstmt = conn.prepareStatement(personnelExport)) {
                    if (filter) {
                        pstmt.setString(1, selectedType);
                    }

                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        out.print(rs.getString("media_id") + ",");
                        out.print(rs.getString("person_name").replace(",", ";") + ",");
                        out.println(rs.getString("role_name").replace(",", ";"));
                        personnelCount++;
                    }
                }

                out.println();
                out.println("=========== COMPANIES ===========");
                out.println("media_id,company_name,role_name");

                try (PreparedStatement pstmt = conn.prepareStatement(companyExport)) {
                    if (filter) {
                        pstmt.setString(1, selectedType);
                    }

                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        out.print(rs.getString("media_id") + ",");
                        out.print(rs.getString("company_name").replace(",", ";") + ",");
                        out.println(rs.getString("role_name").replace(",", ";"));
                        companyCount++;
                    }
                }

                showExportSummary(selectedType, mediaCount, personnelCount, companyCount, filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getString (int mediaId) {
        try {
            DatabaseManager dm = new DatabaseManager();
            dm.getConnection();

            PreparedStatement pstmt = dm.conn.prepareStatement(UpdateArchiveQueries.fetch_game_platform);

            pstmt.setInt(1, mediaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return rs.getString("icon_path");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "";
    }

    private boolean askToUpdateDuplicates() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Duplicates Detected");
        alert.setHeaderText("We found duplicates on your file!");
        alert.setContentText("Do you wish to update the existing entries with the new data from this file, or skip them?");

        try {
            Image logo = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png"));
            ImageView icon = new ImageView(logo);
            icon.setFitHeight(50);
            icon.setFitWidth(50);
            icon.setPreserveRatio(true);
            alert.setGraphic(icon);

            DialogPane dialogPane = alert.getDialogPane();
            String cssPath = getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm();
            dialogPane.getStylesheets().add(cssPath);
            dialogPane.getStyleClass().add("custom-dialog");
        } catch (Exception e) {

        }

        ButtonType updateBtn = new ButtonType("Update", ButtonBar.ButtonData.YES);
        ButtonType skipBtn = new ButtonType("Skip", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(updateBtn, skipBtn);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == updateBtn;
    }

    private void showImportSummary(int totalCount, int successCount, int duplicateCount, int errorCount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import Complete");
        alert.setHeaderText("Import Summary");

        String summaryText = "Total Processed : " + totalCount + "\n" +
                "Successfully Added : " + successCount + "\n" +
                "Duplicates Found : " + duplicateCount + "\n" +
                "Errors : " + errorCount;

        alert.setContentText(summaryText);

        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png"));
            ImageView customIcon = new ImageView(logoImage);
            customIcon.setFitHeight(50);
            customIcon.setFitWidth(50);
            customIcon.setPreserveRatio(true);
            alert.setGraphic(customIcon);

            DialogPane dialogPane = alert.getDialogPane();
            String cssPath = getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm();
            dialogPane.getStylesheets().add(cssPath);
            dialogPane.getStyleClass().add("custom-dialog");
        } catch (Exception e) {
            System.out.println("Could not load custom icon: " + e.getMessage());
        }
        alert.showAndWait();
    }

    private void showImportSummary(int successCount, int errorCount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import Complete");
        alert.setHeaderText("Import Summary");

        String summaryText = "Successfully Added : " + successCount + "\n" +
                "Duplicates/Error Found : " + errorCount;

        alert.setContentText(summaryText);

        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png"));
            ImageView customIcon = new ImageView(logoImage);
            customIcon.setFitHeight(50);
            customIcon.setFitWidth(50);
            customIcon.setPreserveRatio(true);
            alert.setGraphic(customIcon);

            DialogPane dialogPane = alert.getDialogPane();
            String cssPath = getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm();
            dialogPane.getStylesheets().add(cssPath);
            dialogPane.getStyleClass().add("custom-dialog");
        } catch (Exception e) {
            System.out.println("Could not load custom icon: " + e.getMessage());
        }
        alert.showAndWait();
    }

    private static void showExportSummary(String selectedType, int mediaCount, int personnelCount,
                                          int companyCount, String filePath) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Complete");
        alert.setHeaderText("Export Summary");

        String type = (selectedType == null || selectedType.equals("All Media")) ? "All Media" : selectedType;
        String summaryText = "Type Exported : " + type + "\n" +
                "Media Rows Exported : " + mediaCount + "\n" +
                "Personnel Rows Exported : " + personnelCount + "\n" +
                "Company Rows Exported : " + companyCount + "\n" +
                "Saved To : " + filePath;

        alert.setContentText(summaryText);

        try {
            Image logoImage = new Image(FileManager.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png"));
            ImageView customIcon = new ImageView(logoImage);
            customIcon.setFitHeight(50);
            customIcon.setFitWidth(50);
            customIcon.setPreserveRatio(true);
            alert.setGraphic(customIcon);

            DialogPane dialogPane = alert.getDialogPane();
            String cssPath = FileManager.class.getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm();
            dialogPane.getStylesheets().add(cssPath);
            dialogPane.getStyleClass().add("custom-dialog");
        } catch (Exception e) {
            System.out.println("Could not load custom icon: " + e.getMessage());
        }
        alert.showAndWait();
    }

}
