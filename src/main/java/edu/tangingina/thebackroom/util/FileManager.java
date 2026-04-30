package edu.tangingina.thebackroom.util;

import com.google.gson.Gson;
import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.*;
import edu.tangingina.thebackroom.model.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    public File openFile(Stage stage, String description, String fileType){
        FileChooser selectFile = new FileChooser();

        //this would make only csv files to be opened... (filterer)
        selectFile.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(description, fileType)
        );

        File file = selectFile.showOpenDialog(stage);
        return file;

    }

    public void importCSV(Stage stage) throws Exception{

        File file = openFile(stage, "CSV Files (*.csv)", "*.csv");

        if(file != null){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String currLine;
                ArrayList<Media> duplicateMedia = new ArrayList<>();

                int successCount = 0;
                int errorCount = 0;
                int totalCount = 0;


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


                        ArrayList<Website> onlineAccess = new ArrayList<>();
                        ArrayList<Category> genre = new ArrayList<>();


                        if(website != null){
                            for (String websiteName : website) {
                                String[] parts = websiteName.trim().split(":", 2);
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

                        if(mediaType == MediaType.Book){
                            //format(isbn, edition, pageCount, author, publisher)
                            String isbn = line[7];
                            String edition = line[8];
                            String pageCount = line[9];
                            String[] author = line[10].split(";");
                            String[] publisher = line[11].split(";");

                            ArrayList<Person> bookAuthor = new ArrayList<>();
                            ArrayList<Company> bookCompany = new ArrayList<>();

                            if(author != null){
                                for (String authorName : author) {
                                    int id = 0;
                                    if (!personList.containsKey(authorName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = personDao.addPerson(new Person(0, authorName, null, 0));
                                        personList.put(authorName, new Person(id, authorName, null, 0));
                                    }else{
                                        id = personList.get(authorName).getPersonID();
                                    }

                                    bookAuthor.add(new Person(id, authorName, "Author", roleList.get("Author").getRoleID()));
                                }
                            }

                            if(publisher != null){
                                for (String publisherName : publisher) {
                                    int id = 0;

                                    if (!companyList.containsKey(publisherName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = companyDao.addCompany(new Company(0, publisherName, null, 0));
                                        companyList.put(publisherName, new Company(id, publisherName, null, 0));
                                    }else{
                                        id = companyList.get(publisherName).getCompanyID();
                                    }
                                    bookCompany.add(new Company(id, publisherName, "Publisher", roleList.get("Publisher").getRoleID()));
                                }
                            }
                            media.setBookDetails(isbn, pageCount, edition, bookAuthor, bookCompany);

                        }else if(mediaType == MediaType.Movie){

                            //format(duration, language, director, studio)
                            String duration = line[7];
                            String language = line[8];
                            String[] director = line[9].split(";");
                            String[] studio = line[10].split(";");

                            ArrayList<Person> movieDirector = new ArrayList<>();
                            ArrayList<Company> movieStudio = new ArrayList<>();

                            if(director != null){
                                for (String directorName : director) {
                                    int id = 0;
                                    if (!personList.containsKey(directorName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = personDao.addPerson(new Person(0, directorName, null, 0));
                                        personList.put(directorName, new Person(id, directorName, null, 0));
                                    }else{
                                        id = personList.get(directorName).getPersonID();
                                    }

                                    movieDirector.add(new Person(id, directorName, "Director", roleList.get("Director").getRoleID()));
                                }
                            }

                            if(studio != null){
                                for (String studioName : studio) {
                                    int id = 0;

                                    if (!companyList.containsKey(studioName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = companyDao.addCompany(new Company(0, studioName, null, 0));
                                        companyList.put(studioName, new Company(id, studioName, null, 0));
                                    }else{
                                        id = companyList.get(studioName).getCompanyID();
                                    }
                                    movieStudio.add(new Company(id, studioName, "Production Studio", roleList.get("Production Studio").getRoleID()));
                                }
                            }
                            media.setMovieDetails(duration, language, movieDirector, movieStudio);


                        }else if(mediaType == MediaType.TvShow){

                            //format(seasonCount, episodeCount, status, director, studio)
                            String seasonCount = line[7];
                            String episodeCount = line[8];
                            String status = line[9];
                            String[] director = line[10].split(";");
                            String[] studio = line[11].split(";");

                            ArrayList<Person> showDirector = new ArrayList<>();
                            ArrayList<Company> showStudio = new ArrayList<>();

                            if(director != null){
                                for (String directorName : director) {
                                    int id = 0;
                                    if (!personList.containsKey(directorName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = personDao.addPerson(new Person(0, directorName, null, 0));
                                        personList.put(directorName, new Person(id, directorName, null, 0));
                                    }else{
                                        id = personList.get(directorName).getPersonID();
                                    }

                                    showDirector.add(new Person(id, directorName, "Director", roleList.get("Director").getRoleID()));
                                }
                            }

                            if(studio != null){
                                for (String studioName : studio) {
                                    int id = 0;

                                    if (!companyList.containsKey(studioName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = companyDao.addCompany(new Company(0, studioName, null, 0));
                                        companyList.put(studioName, new Company(id, studioName, null, 0));
                                    }else{
                                        id = companyList.get(studioName).getCompanyID();
                                    }
                                    showStudio.add(new Company(id, studioName, "Production Studio", roleList.get("Production Studio").getRoleID()));
                                }
                            }
                            media.setTvShowDetails(seasonCount, episodeCount, status, showDirector, showStudio);

                        }else if(mediaType == MediaType.Game){

                            //format(gameEngine, systemRequirements, gameMode, gameDev, gameStudio, publisher, platform)
                            String gameEngine = line[7];
                            String systemRequirements = line[8];
                            String[] gameMode = line[9].split(";");
                            String[] publisher = line[12].split(";");
                            String[] gamePlatform = line[13].split(";");

                            ArrayList<Person> mediaGamePersonnel = new ArrayList<>();
                            ArrayList<Company> mediaGameCompany = new ArrayList<>();
                            ArrayList<GameMode> mediaGameMode = new ArrayList<>();
                            ArrayList<Platform> mediaGamePlatform = new ArrayList<>();

                            //Person Game Developer
                            if(!line[10].isEmpty()){
                                String[] developer = line[10].split(";");

                                for (String gameDevName : developer) {
                                    int id = 0;
                                    if (!personList.containsKey(gameDevName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = personDao.addPerson(new Person(0, gameDevName, null, 0));
                                        personList.put(gameDevName, new Person(id, gameDevName, null, 0));
                                    }else{
                                        id = personList.get(gameDevName).getPersonID();
                                    }

                                    mediaGamePersonnel.add(new Person(id, gameDevName, "Game Developer", roleList.get("Game Developer").getRoleID()));
                                }
                            }

                            if(!line[11].isEmpty()){
                                String[] studio = line[11].split(";");

                                for (String studioName : studio) {
                                    int id = 0;

                                    if (!companyList.containsKey(studioName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = companyDao.addCompany(new Company(0, studioName, null, 0));
                                        companyList.put(studioName, new Company(id, studioName, null, 0));
                                    }else{
                                        id = companyList.get(studioName).getCompanyID();
                                    }
                                    mediaGameCompany.add(new Company(id, studioName, "Game Studio", roleList.get("Game Studio").getRoleID()));
                                }
                            }
                            //Company Game Publisher
                            if(publisher != null){
                                for (String publisherName : publisher) {
                                    int id = 0;

                                    if (!companyList.containsKey(publisherName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = companyDao.addCompany(new Company(0, publisherName, null, 0));
                                        companyList.put(publisherName, new Company(id, publisherName, null, 0));
                                    }else{
                                        id = companyList.get(publisherName).getCompanyID();
                                    }
                                    mediaGameCompany.add(new Company(id, publisherName, "Publisher", roleList.get("Publisher").getRoleID()));
                                }
                            }

                            if(gameMode != null){
                                //Game Mode
                                for (String modeName : gameMode) {
                                    int id = 0;

                                    if (!gameModeList.containsKey(modeName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = gameModeDao.addGameMode(new GameMode(0, modeName));
                                        gameModeList.put(modeName, new GameMode(id, modeName));
                                    }else{
                                        id = gameModeList.get(modeName).getGameModeID();
                                    }

                                    mediaGameMode.add(new GameMode(id, modeName));
                                }
                            }

                            if(gamePlatform != null){
                                //Game Platform
                                for (String platformName : gamePlatform) {
                                    int id = 0;

                                    if (!platformList.containsKey(platformName)) {
                                        //the index here is temporary since the id value is put in the table itself
                                        id = platformDao.addPlatform(new Platform(0, platformName));
                                        platformList.put(platformName, new Platform(id, platformName));
                                    }else{
                                        id = platformList.get(platformName).getPlatformID();
                                    }

                                    mediaGamePlatform.add(new Platform(id, platformName));
                                }
                            }
                            media.setGameDetails(gameEngine, systemRequirements, mediaGamePersonnel, mediaGameCompany, mediaGamePlatform, mediaGameMode);
                        }

                        if(TheBackroom.util.checkDuplicate(media)){
                            duplicateMedia.add(media);
                            continue;
                        }

                        mediaDao.addMedia(media);
                        mediaList.put(media.getID(), media);
                        successCount++;
                    } catch (Exception e) {
                        errorCount++;
                        e.printStackTrace();
                    }
                }

                if(!duplicateMedia.isEmpty()){
                    Scanner scan = new Scanner(System.in);
                    System.out.print(duplicateMedia.size() + " found. Update Existing Files [Y/N]: ");
                    String choice = scan.nextLine().trim().toUpperCase();

                    switch (choice) {
                        case "Y":
                            try{
                                for(Media m : duplicateMedia){
                                    int mediaId = TheBackroom.mediaUniqID.get(TheBackroom.util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()));
                                    m.setID(mediaId);
                                    mediaDao.updateMedia(m, TheBackroom.mediaList.get(mediaId));
                                    TheBackroom.mediaList.put(m.getID(), m);
                                    successCount++;
                                }

                                duplicateMedia.clear();
                            }catch (Exception e){
                                errorCount++;
                            }
                            break;
                            default: System.out.println("Invalid input. Please enter Y or N.");
                            break;
                        }
                    }
                System.out.println("\n========== IMPORT SUMMARY ==========");
                System.out.println("Total Processed : " + totalCount);
                System.out.println("Successfully Added : " + successCount);
                System.out.println("Duplicates Found : " + duplicateMedia.size());
                System.out.println("Errors : " + errorCount);
                System.out.println("====================================\n");


            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public void importJSON(Stage stage) throws Exception{
        Gson gson = new Gson();
        File file = openFile(stage, "JSON Files (*.json)", "*.json");

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

                        if(TheBackroom.util.checkDuplicate(m)){
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
                                        System.out.println("Warning: GameMode name is null!");
                                        continue; // Skip this broken item!
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
                        TheBackroom.mediaUniqID.put(TheBackroom.util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()), m.getID());
                        TheBackroom.mediaList.put(m.getID(), m);
                        successCount++;
                        System.out.println("Press enter to Continue");
                        Scanner scan = new Scanner(System.in);
                        scan.nextLine();
                    } catch (Exception e) {
                        //e.printStackTrace();
                        errorCount++;
                    }
                }


                if(!duplicateMedia.isEmpty()){
                    Scanner scan = new Scanner(System.in);
                    System.out.print(duplicateMedia.size() + " found. Update Existing Files [Y/N]: ");
                    String choice = scan.nextLine().trim().toUpperCase();

                    switch (choice) {
                        case "Y":
                            try{
                                for(Media m : duplicateMedia){
                                    int mediaId = TheBackroom.mediaUniqID.get(TheBackroom.util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()));
                                    m.setID(mediaId);
                                    mediaDao.updateMedia(m, TheBackroom.mediaList.get(mediaId));
                                    TheBackroom.mediaList.put(m.getID(), m);
                                    successCount++;
                                }

                                duplicateMedia.clear();
                            }catch (Exception e){
                                errorCount++;
                            }
                            break;

                        default:
                            System.out.println("Invalid input. Please enter Y or N.");
                            break;
                    }
                }


                System.out.println("\n========== IMPORT SUMMARY ==========");
                System.out.println("Total Processed : " + totalCount);
                System.out.println("Successfully Added : " + successCount);
                System.out.println("Duplicates Found : " + duplicateMedia);
                System.out.println("Errors : " + errorCount);
                System.out.println("====================================\n");
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void importSQL(Stage stage) throws Exception {
        File file = openFile(stage, "SQL Files (*.sql)", "*.sql");

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
                System.out.println("\n========== SQL IMPORT SUMMARY ==========");
                System.out.println("Successfully Executed : " + successCount);
                System.out.println("Errors/Duplicates Skipped : " + errorCount);
                System.out.println("========================================\n");

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
        String baseCols = "media_id, name, release_year, synopsis";
        String specificCols = "";
        String query;

        try {
            dm.getConnection();
            Connection conn = dm.conn;

            if (selectedType != null) {
                switch (selectedType) {
                    case "Book":
                        specificCols = ", isbn, page_count, edition, language";
                        break;
                    case "Film":
                        specificCols = ", duration, language";
                        break;
                    case "TV Show":
                        specificCols = ", season_count, episode_count, status, language";
                        break;
                    case "Game":
                        specificCols = ", game_engine, system_requirements, status";
                        break;
                    default:
                        baseCols = "*";
                        break;
                }
            }

            query = "Select " + baseCols + specificCols + " from media";

            if (selectedType != null && !selectedType.equals("All Media")) {
                query += " where media_type = ?";
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 PrintWriter out = new PrintWriter(new File(filePath))) {

                if (selectedType != null && !selectedType.equals("All Media")) {
                    pstmt.setString(1, selectedType);
                }

                ResultSet rs = pstmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columns = rsmd.getColumnCount();

                for (int i = 1; i <= columns; i++) {
                    out.print(rsmd.getColumnName(i) + (i < columns ? ", " : ""));
                }
                out.println();

                while (rs.next()) {
                    for (int i = 1; i <= columns; i++) {
                        String colName = rsmd.getColumnName(i);
                        String val = rs.getString(i);

                        if (val == null) {
                            out.print("" + (i < columns ? "," : ""));
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getMediaData (int mediaId) {
        String sql = "select * from media where media_id = ?";

        try {
            DatabaseManager dm = new DatabaseManager();
            dm.getConnection();

            PreparedStatement pstmt = dm.conn.prepareStatement(sql);
            pstmt.setInt(1, mediaId);

            return pstmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPersonnelName (int mediaId, String role) {
        try {
            DatabaseManager dm = new DatabaseManager();
            dm.getConnection();

            PreparedStatement pstmt = dm.conn.prepareStatement(UpdateQueries.fetch_person_by_role);

            pstmt.setInt(1, mediaId);
            pstmt.setString(2, role);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return rs.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "";
    }


}
