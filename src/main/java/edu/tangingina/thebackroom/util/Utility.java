package edu.tangingina.thebackroom.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.AccessLinkField;
import edu.tangingina.thebackroom.dao.WebsiteDao;
import edu.tangingina.thebackroom.dao.impl.*;
import edu.tangingina.thebackroom.model.*;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Utility {
    Argon2 argon2 = Argon2Factory.create();

    //we are using Argon2 hashing algorithm, on storing the passwords on our user table
    //though its excessive for our project, but why not din dba????hahahhahaha

    public String getHashPass(String password){
        char[] pass = password.toCharArray();
        String hash = "";

        try{
            //this mean, the algorihtm would take 10 iterations, use 64 MB to calulcate, and 1 this one I dont know  yet...
            hash = argon2.hash(10, 65536, 1, pass);
        }finally{
            //it is good to wipe the sotred array, pra hindi  say traceable in memory.....
            argon2.wipeArray(pass);
        }
        return hash;
    }

    public boolean verifyPass(String dbPass, String inputPass){
        char[] userPass = inputPass.toCharArray();
        boolean logIn = false;

        try{
            logIn = argon2.verify(dbPass, userPass);
        }finally{
            //it is good to wipe the sotred array, pra hindi  say traceable in memory.....
            argon2.wipeArray(userPass);
        }
        return logIn;
    }

    public void saveCredentials(String username){
        try{
            File file = new File("app.env");
            FileWriter fw = new FileWriter(file);
            fw.write(username);
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeCredentials(){
        try{
            File file = new File("app.env");
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String checkAppEnv(){
        String username = null;
        try{
            File file = new File("app.env");
            if(file.exists()){
                Scanner reader  = new Scanner(file);
                if(reader.hasNextLine()){
                    username = reader.nextLine();
                }
                reader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }

    public boolean checkDuplicate(Media m){
        if(TheBackroom.mediaUniqID.isEmpty()) return false; //safe check if the list is still empty
        String key = getMediaKey(m.getMediaName(), m.getMediaType().name(),m.getReleaseYear());
        Integer index = TheBackroom.mediaUniqID.get(key);

        if(index == null) return false;


        return true;
    }

    public String getMediaKey(String name, String type, String year){
        return name.trim().toLowerCase() + "|" +
                type.trim().toLowerCase() + "|" +
                year.trim();
    }

    public ArrayList<Website> ensureWebsiteExists(List<AccessLinkField.AccessLink> websites){
        String websiteName, url;
        WebsiteDaoImpl websiteDao = TheBackroom.websiteDao;
        ArrayList<Website> onlineAccess = new ArrayList<>();

        HashMap<String, Website> websiteList = TheBackroom.websiteList;
        int id = 0;

        for(AccessLinkField.AccessLink ac : websites){
            websiteName = ac.getWebsiteName().trim();
            url = ac.getWebsiteLink().trim();

            if(!websiteList.containsKey(websiteName)){
                Website w = new Website(0, websiteName, null);
                id = websiteDao.addWesbite(w);
                websiteList.put(websiteName, w);
            }else{
                id = websiteList.get(websiteName).getWebsiteID();
            }

            onlineAccess.add(new Website(id, websiteName, url));
        }

        return onlineAccess;
    }

    public ArrayList<Category> ensureCategoryExist(List<String> category){
        CategoryDaoImpl categoryDao = TheBackroom.categoryDao;
        ArrayList<Category> genre = new ArrayList<>();

        HashMap<String, Category> categoryList = TheBackroom.categoryList;
        int id = 0;

        for(String genreName : category){

            if(!categoryList.containsKey(genreName.trim())){
                Category c = new Category(0, genreName.trim());
                id = categoryDao.addCategory(c);
                categoryList.put(genreName, c);
            }else{
                id = categoryList.get(genreName.trim()).getCategoryID();
            }

            genre.add(new Category(id, genreName.trim()));
        }

        return genre;
    }

    public ArrayList<Person> ensurePersonExist(List<String> personnel, String role){
        PersonDaoImpl personDao = TheBackroom.personDao;
        ArrayList<Person> mediaPersonnel = new ArrayList<>();
        HashMap<String, Person> personList = TheBackroom.personList;
        HashMap<String, Role> roleList = TheBackroom.roleList;
        int id = 0;

        for(String personName : personnel){

            if (!personList.containsKey(personName)) {
                //the index here is temporary since the id value is put in the table itself
                Person p = new Person(0, personName.trim(), null, 0);
                id = personDao.addPerson(p);
                personList.put(personName.trim(), p);
            }else{
                id = personList.get(personName).getPersonID();
            }

            mediaPersonnel.add(new Person(id, personName.trim(), role, roleList.get(role).getRoleID()));
        }

        return mediaPersonnel;
    }

    public ArrayList<Company> ensureCompanyExists(List<String> company, String role){
        CompanyDaoImpl companyDao = TheBackroom.companyDao;
        ArrayList<Company> mediaCompany = new ArrayList<>();
        HashMap<String, Company> companyList = TheBackroom.companyList;
        HashMap<String, Role> roleList = TheBackroom.roleList;
        int id = 0;

        for(String companyName : company){

            if (!companyList.containsKey(companyName)) {
                //the index here is temporary since the id value is put in the table itself

                Company c = new Company(0, companyName.trim(), null, 0);
                id = companyDao.addCompany(c);
                companyList.put(companyName.trim(), c);
            }else{
                id = companyList.get(companyName.trim()).getCompanyID();
            }
            mediaCompany.add(new Company(id, companyName.trim(), role, roleList.get(role).getRoleID()));
        }

        return mediaCompany;
    }

    public ArrayList<GameMode> ensureGameModeExists(List<String> gameMode){
        GameModeDaoImpl gameModeDao = TheBackroom.gameModeDao;
        ArrayList<GameMode> mediaGameMode = new ArrayList<>();
        HashMap<String, GameMode> gameModeList = TheBackroom.gameModeList;
        int id = 0;

        for(String modeName : gameMode){

            if (!gameModeList.containsKey(gameModeDao)) {
                //the index here is temporary since the id value is put in the table itself

                GameMode gm = new GameMode(0, modeName.trim());
                id = gameModeDao.addGameMode(gm);
                gameModeList.put(modeName.trim(), gm);
            }else{
                id = gameModeList.get(modeName.trim()).getGameModeID();
            }
            mediaGameMode.add(new GameMode(id, modeName.trim()));
        }

        return mediaGameMode;
    }

    public ArrayList<Platform> ensureGamePlatformExists(List<String> gamePlatform){
        PlatformDaoImpl platformDao = TheBackroom.platformDao;
        ArrayList<Platform> mediaGamePlatform = new ArrayList<>();
        HashMap<String, Platform> platformlist = TheBackroom.platformList;
        int id = 0;

        for(String platformName : gamePlatform){

            if (!platformlist.containsKey(platformName)) {
                //the index here is temporary since the id value is put in the table itself
                Platform p = new Platform(0, platformName.trim());
                id = platformDao.addPlatform(p);
                platformlist.put(platformName.trim(), p);
            }else{
                id = platformlist.get(platformName.trim()).getPlatformID();
            }
            mediaGamePlatform.add(new Platform(id, platformName.trim()));
        }

        return mediaGamePlatform;
    }



}
