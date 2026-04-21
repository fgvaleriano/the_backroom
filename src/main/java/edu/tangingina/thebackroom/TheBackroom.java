package edu.tangingina.thebackroom;

//import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.dao.CompanyDao;
import edu.tangingina.thebackroom.dao.impl.*;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
===================================================
  (ノಠ益ಠ)ノ  The Fucking To do LIST!!!!!!!
==================================================
    [] UI ARCHITECTURE & FIXES
       - Fix FXML Location: Use leading slash "/edu/tangingina/..."
       - Controller Pathing: Ensure fx:controller includes full package name.
       - Resource Migration: Move all CSS and static icons to /resources.

    [] UI DESIGN & WORKFLOW
       - Scene Switching: Methods in TheBackroom.java to toggle between views.
       - Button Logic: Add onAction handlers to switch VBox visibility (Step 1, 2, 3).
       - Controller Linking: Match all @FXML variables with FXML fx:id.

    [] DATABASE ENGINE (AIVEN CLOUD)
       - Connection: Finalize DatabaseManager.java with SSL and Aiven URI.
       - Schema Setup: Create tables for Users, Archives, and Favorites.
       - DAO & Impl: Create interfaces and classes; Impl MUST throw Exceptions.

    [] HOME PAGE & SEARCH
       - Data Fetching: Query to get all media (Movies, Books, etc.) for the home view.
       - Search Filter: Implement "WHERE name LIKE %?%" logic in the DAO.
       - Category Filter: Filter results by Media Type or Genre.

    [] PERSONALIZATION & FAVORITES
       - Favorites Table: Schema (user_id, archive_id) to link items to users.
       - Recommendations: Query to suggest items based on the user's favorite genre.
       - Toggle Feature: Heart/Star button to add or remove items from Favorites.

    [] ERROR HANDLING & POLISH
       - UI Alerts: Use JavaFX Alerts to display errors thrown by the Impl.
       - Validation: Prevent saving if required fields (Name, Type) are empty.

    [DONE] LOGIN & SIGNUP

    [] (ノಠ益ಠ)ノ MAKE EVRYTHING FUCKING WORK........... (ಥ﹏ಥ)(╥﹏╥)(╥﹏╥)(╥﹏╥)

 */

public class TheBackroom extends Application {
    public static boolean inSession = false;
    public static Users currUser;

    //(ノಠ益ಠ)ノ CURRUSER ISSUE:
    //what if someone iinputted or created here a username in the databse
    //but they are allowed na to do things sa database, even without  kuan checking they are indeed sa dataabse, and limited only
    //to their role

    public static Connection conn;
    public static String user;
    Scanner scan = new Scanner(System.in);

    Scene scene;
    StackPane root, homePage, addPage;

    public static DatabaseManager dm = new DatabaseManager();
    public static Utility util = new Utility();

    public static SceneManager sm;

    FileManager fm = new FileManager();
    InternetManager im = new InternetManager();

    public static CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    public static WebsiteDaoImpl websiteDao = new WebsiteDaoImpl();
    public static PersonDaoImpl personDao = new PersonDaoImpl();
    public static CompanyDaoImpl companyDao = new CompanyDaoImpl();
    public static RoleDaoImpl roleDao = new RoleDaoImpl();
    public static MediaDaoImpl mediaDao = new MediaDaoImpl();
    public static PlatformDaoImpl platformDao = new PlatformDaoImpl();
    public static GameModeDaoImpl gameModeDao = new GameModeDaoImpl();


    public static HashMap<String, Category> categoryList;
    public static HashMap<String, Website> websiteList;
    public static HashMap<String, Person> personList;
    public static HashMap<String, Company> companyList;
    public static HashMap<String, Role> roleList;
    public static HashMap<String, Platform> platformList;
    public static HashMap<String, GameMode> gameModeList;
    public static HashMap<Integer, Media> mediaList;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Hello World!!!");
        openDB();
        loadCache();
        printMediaList();

        //addMedia(primaryStage);

        //fm.importImg(primaryStage);
        //sm = new SceneManager(primaryStage);


        //showLogin(primaryStage);
        fontLoader();
        //sm.showLogin();

        //showLogin(primaryStage);
        //fontLoader();
        //showSignUp(primaryStage);
        //showHome(primaryStage);
        //showAddArchive(primaryStage);
        //fm.importCSV(primaryStage);
        //fm.importJSON(primaryStage);
        //fm.importImg(primaryStage);
        //im.openWebsite("https://www.youtube.com/watch?v=Yw7DQhx08ak&pp=ygUEYWhvZg%3D%3D");


        //Flow -> Open the app, and when they enter, put this error if not wifi....or down an database...
        /*
        int choice = 0;
        UserDaoImpl userDao = new UserDaoImpl(); //this would be put in the login stuff, so yeah
        String username, pass;
        while(true){
            if(!inSession && !openDB()) continue; //inSession this means if currently logIN..

            if(currUser == null) {
                System.out.println("Currently Log-In as GUEST");
            }else{
                System.out.println("Currently Log-In as " + currUser.getUsername() + "\n");
            }

            System.out.print("1. Login\n2. SignUp\n3.Log-Out\n4.Exit\nChoice: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch (choice){
                case 1:
                    //this part should be put after connecting, but lets put it here la anay muba..
                    username = util.checkAppEnv();
                    if(username != null){
                        try{
                            userDao.getUser(username); //this verifies if there is truly a user with that username on the app.env
                        }catch(Exception e){

                        }
                    }else{
                        System.out.print("Username: "); username = scan.nextLine();
                        System.out.print("Password: ") ; pass = scan.nextLine();
                        try{

                            userDao.login(username, pass);
                            System.out.println("Remember me?\n[Y/N]");
                            String rem;
                            rem = scan.nextLine();

                            if(rem.toLowerCase().equals("y")) util.saveCredentials(username);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Username: "); username = scan.nextLine();
                    System.out.print("Password: ") ; pass = scan.nextLine();
                    try{
                        userDao.signUp(username, util.getHashPass(pass));
                        System.out.println("Remember me?\n[Y/N]");
                        String rem;
                        rem = scan.nextLine();

                        if(rem.toLowerCase().equals("y")) util.saveCredentials(username);

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    if(currUser != null){
                        System.out.println("Logging out..");
                        dm.resetConnection();
                        System.out.println("UnRemember:\n[Y/N] ");
                        currUser = null;
                        String rem;
                        rem = scan.nextLine();
                        if(rem.toLowerCase().equals("y")) util.removeCredentials();
                    }
                    break;

                case 4:
                    System.out.println("Exiting the app");
                    return;


            }
        }*/

    }

    //current holder for logic Muna

    public void printMediaList() {
        if (mediaList == null || mediaList.isEmpty()) {
            System.out.println("(ノಠ益ಠ)ノ THE ARCHIVE IS EMPTY! Records found: 0");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("📂 DATABASE SCAN COMPLETE: " + mediaList.size() + " ITEMS FOUND");
        System.out.println("=".repeat(50));

        for (Media m : mediaList.values()) {
            System.out.println("Name: " + m.getMediaName());
            System.out.println("Media Type: " + m.getMediaType());
            System.out.println("Synopsis: " + m.getSynopsis());
            System.out.println("Release Year: " + m.getReleaseYear());
            System.out.println("Media Icon: " + m.getMediaIcon());

            System.out.print("Category: ");
            for(Category category : m.getMediaGenres()){
                System.out.print(category.getCategoryName() + ", ");
            }
            System.out.println();

            switch(m.getMediaType()){
                case "Book":
                    System.out.println("ISBN: " + m.getISBN());
                    System.out.println("Page Count: " + m.getPageCount());
                    System.out.println("Edition: " + m.getEdition());
                    System.out.print("Author: ");
                    for(Person person : m.getMediaPersonnel()){
                        System.out.print(person.getPersonName() + ", ");
                    }
                    System.out.println();
                    System.out.print("Publisher: ");
                    for(Company company : m.getMediaCompany()){
                        System.out.print(company.getCompanyName() + ", ");
                    }
                    System.out.println();

                    break;

                case "Movie":
                    System.out.println("Duration: " + m.getDuration());
                    System.out.println("Language: " + m.getLanguage());
                    System.out.print("Director: ");
                    for(Person person : m.getMediaPersonnel()){
                        System.out.print(person.getPersonName() + ", ");
                    }
                    System.out.println();
                    System.out.print("Studio: ");
                    for(Company company : m.getMediaCompany()){
                        System.out.print(company.getCompanyName() + ", ");
                    }
                    System.out.println();
                    break;

                case "TvShow":
                    System.out.println("Season Count: " + m.getSeasonCount());
                    System.out.println("Episode Count: " + m.getEpisodeCount());
                    System.out.println("Status: " + m.getStatus());
                    System.out.print("Director: ");
                    for(Person person : m.getMediaPersonnel()){
                        System.out.print(person.getPersonName() + ", ");
                    }
                    System.out.println();
                    System.out.print("Studio: ");
                    for(Company company : m.getMediaCompany()){
                        System.out.print(company.getCompanyName() + ", ");
                    }
                    System.out.println();

                    break;

                case "Game":
                    ArrayList<Person> gameDev = new ArrayList<>();
                    ArrayList<Company> gameStudio = new ArrayList<>();
                    ArrayList<Company> gamePublisher = new ArrayList<>();

                    for(Person person : m.getMediaPersonnel()){
                        if(person.getPersonRole().equals("Game Developer")) gameDev.add(person);
                    }

                    for(Company company : m.getMediaCompany()){
                        if(company.getCompanyRole().equals("Game Studio")) gameStudio.add(company);
                        else if(company.getCompanyRole().equals("Publisher")) gamePublisher.add(company);
                    }

                    System.out.println("Game Engine: " + m.getGameEngine());
                    System.out.println("System Requirement: " + m.getSystemRequirements());

                    System.out.print("Game Mode: ");
                    for(GameMode mode : m.getGameMode()){
                        System.out.print(mode.getGameModeName() + ", ");
                    }
                    System.out.println();
                    System.out.print("Game Platform: ");
                    for(Platform platform : m.getGamePlatform()){
                        System.out.print(platform.getPlatformName() + ", ");
                    }
                    System.out.println();

                    if(!gameDev.isEmpty()){
                        System.out.print("Game Developer: ");
                        for(Person person : gameDev){
                            System.out.print(person.getPersonName() + ", ");
                        }
                        System.out.println();
                    }

                    if(!gameStudio.isEmpty()){
                        System.out.print("Game Studio: ");
                        for(Company company : gameStudio){
                            System.out.print(company.getCompanyName() + ", ");
                        }
                        System.out.println();
                    }

                    System.out.print("Game Publisher: ");
                    for(Company company : gamePublisher){
                        System.out.print(company.getCompanyName() + ", ");
                    }
                    System.out.println();





                    break;
            }


            System.out.print("Online Access: ");
            for(Website website : m.getOnlineAccess()){
                System.out.println(website.getWebisteName() + ": " + website.getWebsiteURL());
            }
            System.out.println();

            System.out.println("_".repeat(50));
        }
    }

    private void addMedia(Stage stage){
        //For the add here, every after add, for anything, include it also to the cache....
        Scanner scan = new Scanner(System.in);
        while(true) {


            System.out.println("Adding Media\n");


            System.out.print("Name: ");
            String name = scan.nextLine();

            System.out.print("Media Type:\n1. Book\n2. Movie\n3. TV Shows\n4. Game\nType: ");
            int type = scan.nextInt();
            scan.nextLine();


            System.out.print("Synopsis: ");
            String synopsis = scan.nextLine();

            System.out.print("Release Year: ");
            String releaseYear = scan.nextLine();

            System.out.print("Image Icon: ");
            String icon = fm.saveIMGRelative(fm.importImg(stage));


            if (!categoryList.isEmpty()) {
                System.out.println("Categories: ");
                for (String categoryName : categoryList.keySet()) {
                    System.out.println(categoryName);
                }
            }

            System.out.println("Input Category (Separate by Comma): ");
            String inputCategory = scan.nextLine();
            String[] category = inputCategory.split(",");

            if (!websiteList.isEmpty()) {
                System.out.println("Websites: ");
                for (String websiteName : websiteList.keySet()) {
                    System.out.println(websiteName);
                }
            }

            System.out.println("Input Websites (Format: Name:URL, Name:URL): ");
            String inputWebsite = scan.nextLine();
            String[] website = inputWebsite.split(",");

            String mediaType = null;
            String isbn = null;
            String edition = null;
            String pageCount = null;
            String duration = null;
            String language = null;
            String seasonCount = null;
            String episodeCount = null;
            String status = null;
            String gameEngine = null;
            String systemRequirements = null;
            String[] author = null;
            String[] publisher = null;
            String[] director = null;
            String[] studio = null;
            String[] developer = null;
            String[] gameMode = null;
            String[] gamePlatform = null;

            if (type == 1) { //Book
                mediaType = "Book";
                System.out.print("ISBN: ");
                isbn = scan.nextLine();

                System.out.print("Edition: ");
                edition = scan.nextLine();

                System.out.print("Page Count: ");
                pageCount = scan.nextLine();


                //==============Insert of Authors================///
                if (!personList.isEmpty()) {
                    System.out.println("List of Persons: ");
                    for (String personName : personList.keySet()) {
                        System.out.println(personName);
                    }
                }

                System.out.println("Input Authors (Separate by Comma): ");
                String inputAuthors = scan.nextLine();
                author = inputAuthors.split(",");

                //==============Insert of Publishers================///
                if (!companyList.isEmpty()) {
                    System.out.println("List of Companies: ");
                    for (String companyName : companyList.keySet()) {
                        System.out.println(companyName);
                    }
                }

                System.out.println("Input Publishers (Separate by Comma): ");
                String inputPublishers = scan.nextLine();
                publisher = inputPublishers.split(",");

            }else if(type == 2){ //Movie
                mediaType = "Movie";
                System.out.print("Duration: ");
                duration = scan.nextLine();

                System.out.print("Language: ");
                language = scan.nextLine();

                //==============Insert of Director================///
                if (!personList.isEmpty()) {
                    System.out.println("List of Persons: ");
                    for (String personName : personList.keySet()) {
                        System.out.println(personName);
                    }
                }

                System.out.println("Input Director (Separate by Comma): ");
                String inputAuthors = scan.nextLine();
                director = inputAuthors.split(",");

                //==============Insert of Studio================///
                if (!companyList.isEmpty()) {
                    System.out.println("List of Companies: ");
                    for (String companyName : companyList.keySet()) {
                        System.out.println(companyName);
                    }
                }

                System.out.println("Input Production Studio (Separate by Comma): ");
                String inputPublishers = scan.nextLine();
                studio = inputPublishers.split(",");
            }else if(type == 3){ //TvShow
                mediaType = "TvShow";
                System.out.print("Season Count: ");
                seasonCount = scan.nextLine();

                System.out.print("Episode Count: ");
                episodeCount = scan.nextLine();

                System.out.print("Status: ");
                status = scan.nextLine();

                //==============Insert of Director================///
                if (!personList.isEmpty()) {
                    System.out.println("List of Persons: ");
                    for (String personName : personList.keySet()) {
                        System.out.println(personName);
                    }
                }

                System.out.println("Input Director (Separate by Comma): ");
                String inputAuthors = scan.nextLine();
                director = inputAuthors.split(",");

                //==============Insert of Studio================///
                if (!companyList.isEmpty()) {
                    System.out.println("List of Companies: ");
                    for (String companyName : companyList.keySet()) {
                        System.out.println(companyName);
                    }
                }

                System.out.println("Input Production Studio (Separate by Comma): ");
                String inputPublishers = scan.nextLine();
                studio = inputPublishers.split(",");
            }else if(type == 4){ //Game
                mediaType = "Game";
                System.out.print("Game Engine: ");
                gameEngine = scan.nextLine();

                System.out.print("System Requirements: ");
                systemRequirements = scan.nextLine();

                //==============Insert of Game Mode================///
                if (!gameModeList.isEmpty()) {
                    System.out.println("List of Game Modes: ");
                    for (String gameModeName : gameModeList.keySet()) {
                        System.out.println(gameModeName);
                    }
                }

                System.out.println("Input Game Mode (Separate by Comma): ");
                String inputGameMode = scan.nextLine();
                gameMode = inputGameMode.split(",");

                //==============Insert of Game Developer================///
                if (!personList.isEmpty()) {
                    System.out.println("List of Persons: ");
                    for (String gameDevName : personList.keySet()) {
                        System.out.println(gameDevName);
                    }
                }

                System.out.print("Input Game Developer [Y/N]: ");
                String choice = scan.nextLine().trim().toUpperCase();

                switch (choice) {
                    case "Y":
                        System.out.println("Input Game Developer (Separate by Comma): ");
                        String inputGameDeveloper = scan.nextLine();
                        developer = inputGameDeveloper.split(",");
                        break;

                    case "N":
                        System.out.println("Skipping Game Developer input...");
                        break;

                    default:
                        System.out.println("Invalid input. Please enter Y or N.");
                        break;
                }


                //==============Insert of Game Studio================///
                if (!companyList.isEmpty()) {
                    System.out.println("List of Companies: ");
                    for (String gameStudioName : companyList.keySet()) {
                        System.out.println(gameStudioName);
                    }
                }

                System.out.print("Input Game Studio [Y/N]: ");
                choice = scan.nextLine().trim().toUpperCase();

                switch (choice) {
                    case "Y":
                        System.out.println("Input Game Studio (Separate by Comma): ");
                        String inputGameStudio = scan.nextLine();
                        studio = inputGameStudio.split(",");
                        break;

                    case "N":
                        System.out.println("Skipping Game Studio input...");
                        break;

                    default:
                        System.out.println("Invalid input. Please enter Y or N.");
                        break;
                }

                if(developer == null && studio == null){
                    System.out.println("Please input in either Game Developer or Game Studio or both.");
                    break;
                }

                //==============Insert of Game Publisher================///
                if (!companyList.isEmpty()) {
                    System.out.println("List of Companies: ");
                    for (String gamePublisherName : companyList.keySet()) {
                        System.out.println(gamePublisherName);
                    }
                }

                System.out.println("Input Game Publisher (Separate by Comma): ");
                String inputGamePublisher = scan.nextLine();
                publisher = inputGamePublisher.split(",");

                //==============Insert of Game Platform================///
                if (!platformList.isEmpty()) {
                    System.out.println("List of Platfrom: ");
                    for (String platformName : platformList.keySet()) {
                        System.out.println(platformName);
                    }
                }

                System.out.println("Input Game Platform (Separate by Comma): ");
                String inputPlatform = scan.nextLine();
                gamePlatform = inputPlatform.split(",");


            }

            System.out.println("Inserting New Media.\nEnter to Continue");
            scan.nextLine();

            ArrayList<Website> onlineAccess = new ArrayList<>();
            ArrayList<Category> genre = new ArrayList<>();


            for (String websiteName : website) {
                String[] parts = websiteName.trim().split(":", 2);
                int id  = 0;

                if (!websiteList.containsKey(parts[0].trim())) {
                    id = websiteDao.addWesbite(new Website(0, parts[0].trim(), null));
                }else{
                    id = websiteList.get(parts[0].trim()).getWebsiteID();
                }

                onlineAccess.add(new Website(id, parts[0].trim(), parts[1].trim()));
            }
            for (String categoryName : category) {
                int id = 0;

                if (!categoryList.containsKey(categoryName)) {
                    //the index here is temporary since the id value is put in the table itself
                    id = categoryDao.addCategory(new Category(0, categoryName));
                }else{
                    id = categoryList.get(categoryName).getCategoryID();
                }

                genre.add(new Category(id, categoryName));
            }

            Media media = new Media(0, name, mediaType, releaseYear, synopsis, icon, onlineAccess, genre);

            if(mediaType.equals("Book")){
                ArrayList<Person> bookAuthor = new ArrayList<>();
                ArrayList<Company> bookCompany = new ArrayList<>();

                for (String authorName : author) {
                    int id = 0;
                    if (!personList.containsKey(authorName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = personDao.addPerson(new Person(0, authorName, null, 0));
                    }else{
                        id = personList.get(authorName).getPersonID();
                    }

                    bookAuthor.add(new Person(id, authorName, "Author", roleList.get("Author").getRoleID()));
                }

                for (String publisherName : publisher) {
                    int id = 0;

                    if (!companyList.containsKey(publisherName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = companyDao.addCompany(new Company(0, publisherName, null, 0));
                    }else{
                        id = companyList.get(publisherName).getCompanyID();
                    }
                    bookCompany.add(new Company(id, publisherName, "Publisher", roleList.get("Publisher").getRoleID()));
                }
                media.setBookDetails(isbn, pageCount, edition, bookAuthor, bookCompany);
                mediaDao.addMedia(media);
            }else if(mediaType.equals("Movie")){
                ArrayList<Person> movieDirector = new ArrayList<>();
                ArrayList<Company> movieStudio = new ArrayList<>();

                for (String directorName : director) {
                    int id = 0;
                    if (!personList.containsKey(directorName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = personDao.addPerson(new Person(0, directorName, null, 0));
                    }else{
                        id = personList.get(directorName).getPersonID();
                    }

                    movieDirector.add(new Person(id, directorName, "Director", roleList.get("Director").getRoleID()));
                }

                for (String studioName : studio) {
                    int id = 0;

                    if (!companyList.containsKey(studioName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = companyDao.addCompany(new Company(0, studioName, null, 0));
                    }else{
                        id = companyList.get(studioName).getCompanyID();
                    }
                    movieStudio.add(new Company(id, studioName, "Production Studio", roleList.get("Production Studio").getRoleID()));
                }

                media.setMovieDetails(duration, language, movieDirector, movieStudio);
                mediaDao.addMedia(media);
            }else if(mediaType.equals("TvShow")){
                ArrayList<Person> showDirector = new ArrayList<>();
                ArrayList<Company> showStudio = new ArrayList<>();

                for (String directorName : director) {
                    int id = 0;
                    if (!personList.containsKey(directorName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = personDao.addPerson(new Person(0, directorName, null, 0));
                    }else{
                        id = personList.get(directorName).getPersonID();
                    }

                    showDirector.add(new Person(id, directorName, "Director", roleList.get("Director").getRoleID()));
                }

                for (String studioName : studio) {
                    int id = 0;

                    if (!companyList.containsKey(studioName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = companyDao.addCompany(new Company(0, studioName, null, 0));
                    }else{
                        id = companyList.get(studioName).getCompanyID();
                    }
                    showStudio.add(new Company(id, studioName, "Production Studio", roleList.get("Production Studio").getRoleID()));
                }

                media.setTvShowDetails(seasonCount, episodeCount, status, showDirector, showStudio);
                mediaDao.addMedia(media);
            }else if(mediaType.equals("Game")){
                ArrayList<Person> mediaGamePersonnel = new ArrayList<>();
                ArrayList<Company> mediaGameCompany = new ArrayList<>();
                ArrayList<GameMode> mediaGameMode = new ArrayList<>();
                ArrayList<Platform> mediaGamePlatform = new ArrayList<>();

                //Person Game Developer
                if(developer != null){
                    for (String gameDevName : developer) {
                        int id = 0;
                        if (!personList.containsKey(gameDevName)) {
                            //the index here is temporary since the id value is put in the table itself
                            id = personDao.addPerson(new Person(0, gameDevName, null, 0));
                        }else{
                            id = personList.get(gameDevName).getPersonID();
                        }

                        mediaGamePersonnel.add(new Person(id, gameDevName, "Game Developer", roleList.get("Game Developer").getRoleID()));
                    }
                }


                if(studio != null){
                    //Company Game Developer
                    for (String studioName : studio) {
                        int id = 0;

                        if (!companyList.containsKey(studioName)) {
                            //the index here is temporary since the id value is put in the table itself
                            id = companyDao.addCompany(new Company(0, studioName, null, 0));
                        }else{
                            id = companyList.get(studioName).getCompanyID();
                        }
                        mediaGameCompany.add(new Company(id, studioName, "Game Studio", roleList.get("Game Studio").getRoleID()));
                    }
                }

                //Company Game Publisher
                for (String publisherName : publisher) {
                    int id = 0;

                    if (!companyList.containsKey(publisherName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = companyDao.addCompany(new Company(0, publisherName, null, 0));
                    }else{
                        id = companyList.get(publisherName).getCompanyID();
                    }
                    mediaGameCompany.add(new Company(id, publisherName, "Publisher", roleList.get("Publisher").getRoleID()));
                }

                //Game Mode
                for (String modeName : gameMode) {
                    int id = 0;

                    if (!gameModeList.containsKey(modeName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = gameModeDao.addGameMode(new GameMode(0, modeName));
                    }else{
                        id = gameModeList.get(modeName).getGameModeID();
                    }

                    mediaGameMode.add(new GameMode(id, modeName));
                }

                //Game Platform
                for (String platformName : gamePlatform) {
                    int id = 0;

                    if (!gameModeList.containsKey(platformName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = platformDao.addPlatform(new Platform(0, platformName));
                    }else{
                        id = platformList.get(platformName).getPlatformID();
                    }

                    mediaGamePlatform.add(new Platform(id, platformName));
                }

                media.setGameDetails(gameEngine, systemRequirements, mediaGamePersonnel, mediaGameCompany, mediaGamePlatform, mediaGameMode);
                mediaDao.addMedia(media);


            }
            break;


        }
    }

    public void showSignUp(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/edu/tangingina/thebackroom/SignUp.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = signUp.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            //this would maximized everything including the windows tab (weird enough if resizable was put before show, it become full screen)
            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);
        }catch (Exception e)  {
            throw new RuntimeException(e);
        }

    }

    public void showAddArchive(Stage stage){
        try{
            //Note the fxml file is already the ui class file...so no need to create another ui class file

            FXMLLoader addArchive = new FXMLLoader(getClass().getResource("/edu/tangingina/thebackroom/AddArchive.fxml"));
            //no need to set up the controller here since we already did that na sa scene builder...

            Parent root = addArchive.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }catch (Exception e)  {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void showLogin(Stage stage){
        try {
            LoginController controller = new LoginController();
            root = controller.getLayout(stage);
            scene = new Scene(root);

            //for styling
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom - Login");

            //.setResizable(true);
            //i think teh better if we do it like this:
            //stage.setMinWidth(1000);
            //stage.setMinHeight(600);

            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showHome(Stage stage) {
        try {
            HomePageController home = new HomePageController();
            homePage = home.getLayout(stage);
            scene = new Scene(homePage);
            scene.getStylesheets().add(getClass().getResource(
                    "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("The Backroom");

            stage.setMaximized(true);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean openDB(){
        boolean connected = false;

        try{
            dm.getConnection();
            System.out.println("Welcome to The Backroom!!!!");
            user = "Guest";
            connected = true;
            inSession = true;
        }catch (Exception e){
            //put an error message here......
            System.out.println(e.getMessage());
        }
        return connected;
    }


    public void showAddArchive_v2(Stage stage) {
        try {
            AddArchive_v2 add = new AddArchive_v2();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fontLoader() {
        FontLoader load = new  FontLoader();
        load.debug();
        return;
    }


    public void loadCache(){
        categoryList = categoryDao.getAllCategory();
        websiteList = websiteDao.getAllWebsite();
        personList = personDao.getAllPersons();
        companyList = companyDao.getAllCompany();
        roleList = roleDao.getAllRole();
        platformList = platformDao.getAllPlatform();
        gameModeList = gameModeDao.getAllGameMode();
        mediaList = mediaDao.getAllMedia();
    }

}
