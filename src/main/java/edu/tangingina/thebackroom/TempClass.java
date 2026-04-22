package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.dao.impl.*;
import edu.tangingina.thebackroom.model.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static edu.tangingina.thebackroom.TheBackroom.fm;

public class TempClass {

    public CategoryDaoImpl categoryDao =  TheBackroom.categoryDao;
    public WebsiteDaoImpl websiteDao = TheBackroom.websiteDao;
    public PersonDaoImpl personDao = TheBackroom.personDao;
    public CompanyDaoImpl companyDao = TheBackroom.companyDao;
    public RoleDaoImpl roleDao = TheBackroom.roleDao;
    public MediaDaoImpl mediaDao = TheBackroom.mediaDao;
    public PlatformDaoImpl platformDao = TheBackroom.platformDao;
    public GameModeDaoImpl gameModeDao = TheBackroom.gameModeDao;


    public HashMap<String, Category> categoryList = TheBackroom.categoryList;
    public HashMap<String, Website> websiteList = TheBackroom.websiteList;
    public HashMap<String, Person> personList = TheBackroom.personList;
    public HashMap<String, Company> companyList = TheBackroom.companyList;
    public HashMap<String, Role> roleList = TheBackroom.roleList;
    public HashMap<String, Platform> platformList = TheBackroom.platformList;
    public HashMap<String, GameMode> gameModeList = TheBackroom.gameModeList;
    public HashMap<Integer, Media> mediaList = TheBackroom.mediaList;

    public void search(){
        Scanner scan = new Scanner(System.in);

        ArrayList<String> categoryFilter = new ArrayList<>();
        ArrayList<Media> results = new ArrayList<>();

        System.out.println("Searching Media....");
        System.out.print("Add Genre to Filter [Y/N]: ");
        String choice = scan.nextLine().trim().toUpperCase();

        switch (choice) {
            case "Y":

                if (!categoryList.isEmpty()) {
                    System.out.println("Categories: ");
                    for (String categoryName : categoryList.keySet()) {
                        System.out.println(categoryName);
                    }
                }

                System.out.println("Input Category to Filter (Separate by Comma): ");
                String inputCategory = scan.nextLine();
                String[] category = inputCategory.split(",");

                for (String categoryName : category) {
                    categoryFilter.add(categoryName.trim());
                }
                break;
        }
        System.out.print("Add Media Type to Filter [Y/N]: ");
        choice = scan.nextLine().trim().toUpperCase();
        String mediaType = null;

        switch (choice) {
            case "Y":
                System.out.print("Media Type:\n1. Book\n2. Movie\n3. TV Shows\n4. Game\nType: ");
                int type = scan.nextInt();
                switch(type) {
                    case 1:
                        mediaType = "Book";
                        break;

                    case 2:
                        mediaType = "Movie";
                        break;

                    case 3:
                        mediaType = "TvShow";
                        break;

                    case 4:
                        mediaType = "Game";
                        break;
                }
                scan.nextLine();
                break;
        }

        System.out.print("Search Name [Y/N]: ");
        choice = scan.nextLine().trim().toUpperCase();
        String name = null;

        switch (choice) {
            case "Y":
                System.out.print("Name: ");
                name = scan.nextLine();
                break;
        }

        for(Media m : mediaList.values()){
            boolean nameMatch = (name == null || name.isEmpty()) ||
                    m.getMediaName().toLowerCase().contains(name.toLowerCase());



            // 2. Type Match: If type is empty, it's 'true'. Otherwise, check equals.
            boolean typeMatch = (mediaType == null || mediaType.isEmpty()) ||
                    m.getMediaType().equalsIgnoreCase(mediaType);

            // 3. Genre Match: If filter list is empty, it's 'true'. Otherwise, check containsAll.
            boolean genreMatch = (categoryFilter == null || categoryFilter.isEmpty());

            if(!genreMatch){
                ArrayList<String> mediaGenre = m.getMediaGenres().stream().map(Category::getCategoryName).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
                genreMatch = mediaGenre.containsAll(categoryFilter);
            }

            // ONLY if all three are true (or bypassed), we add to results
            if (nameMatch && typeMatch && genreMatch) {
                results.add(m);
            }
        }

        System.out.println("Search List: ");

        if(results.isEmpty()) System.out.println("No results found.");
        else{
            System.out.println("Results:");
            for(Media m : results){
                System.out.println("Name: " + m.getMediaName());
                System.out.println("Media Type: " + m.getMediaType());
                System.out.println("Synopsis: " + m.getSynopsis());
                System.out.println("Release Year: " + m.getReleaseYear());
                System.out.println("Media Icon: " + m.getMediaIcon());
                System.out.println();
            }
        }
        results.clear();

        System.out.println("Enter to Continue");
        scan.nextLine();




    }

    public Media pickMedia(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Media List: ");
        for(Media m : mediaList.values()){
            System.out.println("[" + m.getID() + "] " + m.getMediaName());
        }
        System.out.print("Input Media ID: ");
        int id = scan.nextInt();

        return mediaList.get(id);

    }

    public void updateMedia (Stage stage){
        Media oldMedia = pickMedia();
        Scanner scan = new Scanner(System.in);
        while(true) {


            System.out.println("Updating Media\n");


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

            System.out.println("Updating Media.\nEnter to Continue");
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
            media.setID(oldMedia.getID());

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
                mediaDao.updateMedia(media, oldMedia);
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
                mediaDao.updateMedia(media, oldMedia);
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
                mediaDao.updateMedia(media, oldMedia);
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

                    if (!platformList.containsKey(platformName)) {
                        //the index here is temporary since the id value is put in the table itself
                        id = platformDao.addPlatform(new Platform(0, platformName));
                    }else{
                        id = platformList.get(platformName).getPlatformID();
                    }

                    mediaGamePlatform.add(new Platform(id, platformName));
                }

                media.setGameDetails(gameEngine, systemRequirements, mediaGamePersonnel, mediaGameCompany, mediaGamePlatform, mediaGameMode);
                mediaDao.updateMedia(media, oldMedia);


            }

            mediaList.put(media.getID(), media);
            break;


        }
    }

}
