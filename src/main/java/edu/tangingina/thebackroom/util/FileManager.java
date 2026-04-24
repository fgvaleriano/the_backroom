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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void importCSV(Stage stage){

        File file = openFile(stage, "CSV Files (*.csv)", "*.csv");

        if(file != null){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String currLine;

                //Example this would read a person info, so (name, age, gender, sexuality)
                while((currLine = br.readLine()) != null){
                    String [] line = currLine.split(",");

                    //format(name, mediaType, synopsis, release year, icon path, genre, website) - for generic
                    String name = line[0];
                    String mediaType = line[1];
                    String synopsis = line[2];
                    String releaseYear = line[3];
                    String icon = line[4];
                    String[] category = line[5].split(";");
                    String[] website = line[6].split(";");

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
                        //format(isbn, edition, pageCount, author, publisher)
                        String isbn = line[7];
                        String edition = line[8];
                        String pageCount = line[9];
                        String[] author = line[10].split(";");
                        String[] publisher = line[11].split(";");

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

                        //format(duration, language, director, studio)
                        String duration = line[7];
                        String language = line[8];
                        String[] director = line[9].split(";");
                        String[] studio = line[10].split(";");

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

                        //format(seasonCount, episodeCount, status, director, studio)
                        String seasonCount = line[7];
                        String episodeCount = line[8];
                        String status = line[9];
                        String[] director = line[10].split(";");
                        String[] studio = line[11].split(";");

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
                        if(line[10].isEmpty()){
                            String[] developer = line[10].split(";");

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

                        if(line[11].isEmpty()){
                            String[] studio = line[11].split(";");

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
                        mediaDao.addMedia(media);
                    }
                    mediaList.put(media.getID(), media);
                }

                System.out.println("Imported as successfully!!!!");


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void importJSON(Stage stage){
        Gson gson = new Gson();
        File file = openFile(stage, "JSON Files (*.json)", "*.json");

        if(file != null){
            try(FileReader read = new FileReader(file)){
                /*Person[] person = gson.fromJson(read, Person[].class); //from the JSON file it is turned into a class

                for(int i = 0; i < person.length; i++){
                    Person temp = person[i];

                    String name = temp.name;
                    int age = temp.age;
                    String gender = temp.gender;
                    String sexuality = temp.sexuality;

                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Gender: " + gender);
                    System.out.println("Sexuality: " + sexuality);
                    System.out.println();

                }*/

            } catch (Exception e) {
                throw new RuntimeException(e);
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

        File newFile = new File(dir, file.getName());
        try{
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){

        }

        return "edu/tangingina/thebackroom/mediaIcon/" + newFileName;
    }


}
