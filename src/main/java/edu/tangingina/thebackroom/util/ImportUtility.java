package edu.tangingina.thebackroom.util;

import edu.tangingina.thebackroom.model.*;

import java.util.ArrayList;

import static edu.tangingina.thebackroom.TheBackroom.*;

public class ImportUtility {

    public void handleBookCSV(String []line, Media media){
        String isbn = line[7];
        String edition = line[8];
        String pageCount = line[9];

        ArrayList<Person> bookAuthor = new ArrayList<>();
        ArrayList<Company> bookCompany = new ArrayList<>();

        if(!line[10].trim().isEmpty()){
            String[] author = line[10].split(";");
            for (String authorName : author) {
                if(authorName.trim().isEmpty()) continue;
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

        if(!line[11].trim().isEmpty()){
            String[] publisher = line[11].split(";");
            for (String publisherName : publisher) {
                if(publisherName.trim().isEmpty()) continue;
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
    }

    public void handleCSVMovie(String []line, Media media){
        String duration = line[7];
        String language = line[8];

        ArrayList<Person> movieDirector = new ArrayList<>();
        ArrayList<Company> movieStudio = new ArrayList<>();

        if(!line[9].trim().isEmpty()){
            String[] director = line[9].split(";");
            for (String directorName : director) {
                if(directorName.trim().isEmpty()) continue;
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

        if(!line[10].trim().isEmpty()){
            String[] studio = line[10].split(";");
            for (String studioName : studio) {
                if(studioName.trim().isEmpty()) continue;
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
    }

    public void handleCSVTvShow(String []line, Media media){
        String seasonCount = line[7];
        String episodeCount = line[8];
        String status = line[9];

        ArrayList<Person> showDirector = new ArrayList<>();
        ArrayList<Company> showStudio = new ArrayList<>();

        if(!line[10].trim().isEmpty()){
            String[] director = line[10].split(";");
            for (String directorName : director) {
                if(directorName.trim().isEmpty()) continue;
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

        if(!line[11].trim().isEmpty()){
            String[] studio = line[11].split(";");
            for (String studioName : studio) {
                if(studioName.trim().isEmpty()) continue;
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
    }

    public void handleCSVGame(String []line, Media media){
        String gameEngine = line[7];
        String systemRequirements = line[8];

        ArrayList<Person> mediaGamePersonnel = new ArrayList<>();
        ArrayList<Company> mediaGameCompany = new ArrayList<>();
        ArrayList<GameMode> mediaGameMode = new ArrayList<>();
        ArrayList<Platform> mediaGamePlatform = new ArrayList<>();

        //Person Game Developer
        if(!line[10].isEmpty()){
            String[] developer = line[10].split(";");

            for (String gameDevName : developer) {
                if(gameDevName.trim().isEmpty()) continue;
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
                if(studioName.trim().isEmpty()) continue;
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
        if(!line[12].trim().isEmpty()){
            String[] publisher = line[12].split(";");
            for (String publisherName : publisher) {
                if(publisherName.trim().isEmpty()) continue;
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

        if(!line[9].trim().isEmpty()){
            //Game Mode
            String[] gameMode = line[9].split(";");
            for (String modeName : gameMode) {
                if(modeName.trim().isEmpty()) continue;
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

        if(!line[13].trim().isEmpty()){
            String[] gamePlatform = line[13].split(";");
            //Game Platform
            for (String platformName : gamePlatform) {
                if(platformName.trim().isEmpty()) continue;
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
}
