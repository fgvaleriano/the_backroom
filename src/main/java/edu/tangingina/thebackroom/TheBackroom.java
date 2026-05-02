package edu.tangingina.thebackroom;

import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.HomePageController;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.controller.dashboard.DashboardShell;
import edu.tangingina.thebackroom.dao.impl.UserDaoImpl;
import edu.tangingina.thebackroom.model.Users;
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

public class TheBackroom extends Application {
    public static Users currUser;

    public static DatabaseManager dm;
    public static Utility util;

    public static SceneManager sm;
    public static FileManager fm;
    public static InternetManager im = new InternetManager();

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
    public static HashMap<String, Integer> mediaUniqID;

    public static ArrayList<Integer> bookMedia;
    public static ArrayList<Integer> videoMedia;
    public static ArrayList<Integer> gameMedia;

    public static ArrayList<String> top6BookGenre;
    public static ArrayList<String> top6VidGenre;
    public static ArrayList<String> top6GameGenre;

    public static boolean onlineDatabase = false;

    @Override
    public void start(Stage primaryStage) {
        initialize();
        openDB();
        loadCache();

        fm = new FileManager();
        sm = new SceneManager(primaryStage);

    }

    public boolean openDB(){
        boolean connected = false;

        try{
            dm.getConnection();
            System.out.println("Welcome to The Backroom!!!!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connected;
    }


    public void initialize(){
        dm = new DatabaseManager();
        util = new Utility();
    }


    public void loadCache(){
        mediaUniqID = new HashMap<>();

        categoryList = categoryDao.getAllCategory();
        websiteList = websiteDao.getAllWebsite();
        personList = personDao.getAllPersons();
        companyList = companyDao.getAllCompany();
        roleList = roleDao.getAllRole();
        platformList = platformDao.getAllPlatform();
        gameModeList = gameModeDao.getAllGameMode();
        try{
            mediaList = mediaDao.getAllMedia();
            bookMedia = mediaDao.getMediaByCategory("Book");
            videoMedia = mediaDao.getMediaByCategory("Movie", "TvShow");
            gameMedia = mediaDao.getMediaByCategory("Game");

            top6BookGenre = mediaDao.getTopMediaCategory("Book");
            top6GameGenre = mediaDao.getTopMediaCategory("Game");
            top6VidGenre = mediaDao.getTopMediaCategory("Movie", "TvShow");

            if(!mediaList.isEmpty()){
                for(Media m : mediaList.values()){
                    mediaUniqID.put(util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()), m.getID());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void resetCache(){
        categoryList.clear();
        websiteList.clear();
        personList.clear();
        companyList.clear();
        roleList.clear();
        platformList.clear();
        gameModeList.clear();
        mediaList.clear();
        mediaUniqID.clear();
        bookMedia.clear();
        videoMedia.clear();
        gameMedia.clear();
        top6BookGenre.clear();
        top6GameGenre.clear();
        top6VidGenre.clear();

    }

    public static void reloadCache(){
        mediaUniqID = new HashMap<>();

        categoryList = categoryDao.getAllCategory();
        websiteList = websiteDao.getAllWebsite();
        personList = personDao.getAllPersons();
        companyList = companyDao.getAllCompany();
        roleList = roleDao.getAllRole();
        platformList = platformDao.getAllPlatform();
        gameModeList = gameModeDao.getAllGameMode();
        try{
            mediaList = mediaDao.getAllMedia();
            bookMedia = mediaDao.getMediaByCategory("Book");
            videoMedia = mediaDao.getMediaByCategory("Movie", "TvShow");
            gameMedia = mediaDao.getMediaByCategory("Game");

            top6BookGenre = mediaDao.getTopMediaCategory("Book");
            top6GameGenre = mediaDao.getTopMediaCategory("Game");
            top6VidGenre = mediaDao.getTopMediaCategory("Movie", "TvShow");

            if(!mediaList.isEmpty()){
                for(Media m : mediaList.values()){
                    mediaUniqID.put(util.getMediaKey(m.getMediaName(), m.getMediaType().name(), m.getReleaseYear()), m.getID());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
