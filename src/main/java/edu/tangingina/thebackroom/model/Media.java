package edu.tangingina.thebackroom.model;

import java.util.ArrayList;

public class Media {
    //General Information
    private int ID;
    private String mediaName;
    private MediaType mediaType;
    private String synopsis;
    private String releaseYear;
    private String mediaIcon;
    private ArrayList<Website> onlineAccess;
    private ArrayList<Category> mediaGenres;
    private ArrayList<Person>  mediaPersonnel;
    private ArrayList<Company>  mediaCompany;


    //Book Details
    private String ISBN;
    private String pageCount;
    private String edition;
    private ArrayList<Person>  author;
    private ArrayList<Company>  publishers;

    //Movie Details and Show Details
    //Generic Information For Both
    private ArrayList<Person> directors;
    private ArrayList<Company> productionStudio;

    //Specific Movie Details
    private String duration;
    private String language;

    //Specific TvShow Details
    private String seasonCount;
    private String episodeCount;
    private String status;

    //Game Details
    private String gameEngine;
    private ArrayList<GameMode> gameMode;
    private String systemRequirements;

    private ArrayList<Company> gameDeveloper;
    private ArrayList<Company> gamePublishers;
    private ArrayList<Platform> gamePlatform;

    //Initialization of Media and Each specific Type
    //Setter of generic Media Information
    public Media(int id, String name, MediaType type, String year, String synopsis, String icon, ArrayList<Website> onlineAccess, ArrayList<Category> genre) {
        //Inialization of each ArrayList
        this.onlineAccess = new ArrayList<>();
        this.mediaGenres = new ArrayList<>();
        this.author = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.productionStudio = new ArrayList<>();
        this.gameDeveloper = new ArrayList<>();
        this.gamePublishers = new ArrayList<>();
        this.gamePlatform = new ArrayList<>();

        this.ID = id;
        this.mediaName = name;
        this.mediaType = type;
        this.releaseYear = year;
        this.synopsis = synopsis;
        this.mediaIcon = icon;
        this.onlineAccess = onlineAccess;
        this.mediaGenres = genre;
    }

    //Set of BookDetails
    public void setBookDetails(String isbn, String pageCount, String edition, ArrayList<Person> authors, ArrayList<Company> publishers) {
        this.ISBN = isbn;
        this.pageCount = pageCount;
        this.edition = edition;
        this.mediaPersonnel = authors;
        this.mediaCompany = publishers;
    }

    //Setter for MovieDetails
    public void setMovieDetails(String duration, String language, ArrayList<Person> directors, ArrayList<Company> studios) {
        this.duration = duration;
        this.language = language;
        this.mediaPersonnel = directors;
        this.mediaCompany = studios;
    }

    //Setter for TVShowDetails
    public void setTvShowDetails(String seasons, String episodes, String status, ArrayList<Person> directors, ArrayList<Company> studios) {
        this.seasonCount = seasons;
        this.episodeCount = episodes;
        this.status = status;
        this.mediaPersonnel = directors;
        this.mediaCompany = studios;
    }

    //Setter for GameDetails
    public void setGameDetails(String engine, String requirements, ArrayList<Person> mediaPersonnel, ArrayList<Company> mediaCompany, ArrayList<Platform> platforms, ArrayList<GameMode> mode) {
        this.gameEngine = engine;
        this.systemRequirements = requirements;
        this.gameMode = mode;
        this.mediaPersonnel = mediaPersonnel;
        this.mediaCompany = mediaCompany;
        this.gamePlatform = platforms;
    }

    //Getters and Setters for Each field
    public Integer getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getMediaName() { return mediaName; }
    public void setMediaName(String mediaName) { this.mediaName = mediaName; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }

    public String getMediaIcon() { return mediaIcon; }
    public void setMediaIcon(String mediaIcon) { this.mediaIcon = mediaIcon; }

    public ArrayList<Website> getOnlineAccess() { return onlineAccess; }
    public void setOnlineAccess(ArrayList<Website> onlineAccess) { this.onlineAccess = onlineAccess; }

    public ArrayList<Category> getMediaGenres() { return mediaGenres; }
    public void setMediaGenres(ArrayList<Category> mediaGenres) { this.mediaGenres = mediaGenres; }

    public ArrayList<Person> getMediaPersonnel() { return mediaPersonnel; }
    public void setMediaPersonnel(ArrayList<Person> mediaPersonnel) { this.mediaPersonnel = mediaPersonnel; }

    public ArrayList<Company> getMediaCompany() { return mediaCompany; }
    public void setMediaCompany(ArrayList<Company> mediaCompany) { this.mediaCompany = mediaCompany; }

    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getPageCount() { return pageCount; }
    public void setPageCount(String pageCount) { this.pageCount = pageCount; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public ArrayList<Person> getAuthor() { return author; }
    public void setAuthor(ArrayList<Person> author) { this.author = author; }

    public ArrayList<Company> getPublishers() { return publishers; }
    public void setPublishers(ArrayList<Company> publishers) { this.publishers = publishers; }

    public ArrayList<Person> getDirectors() { return directors; }
    public void setDirectors(ArrayList<Person> directors) { this.directors = directors; }

    public ArrayList<Company> getProductionStudio() { return productionStudio; }
    public void setProductionStudio(ArrayList<Company> productionStudio) { this.productionStudio = productionStudio; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getSeasonCount() { return seasonCount; }
    public void setSeasonCount(String seasonCount) { this.seasonCount = seasonCount; }

    public String getEpisodeCount() { return episodeCount; }
    public void setEpisodeCount(String episodeCount) { this.episodeCount = episodeCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameEngine() { return gameEngine; }
    public void setGameEngine(String gameEngine) { this.gameEngine = gameEngine; }

    public ArrayList<GameMode> getGameMode() { return gameMode; }
    public void setGameMode(ArrayList<GameMode> gameMode) { this.gameMode = gameMode; }

    public String getSystemRequirements() { return systemRequirements; }
    public void setSystemRequirements(String systemRequirements) { this.systemRequirements = systemRequirements; }


    public ArrayList<Company> getGameDeveloper() { return gameDeveloper; }
    public void setGameDeveloper(ArrayList<Company> gameDeveloper) { this.gameDeveloper = gameDeveloper; }

    public ArrayList<Company> getGamePublishers() { return gamePublishers; }
    public void setGamePublishers(ArrayList<Company> gamePublishers) { this.gamePublishers = gamePublishers; }

    public ArrayList<Platform> getGamePlatform() { return gamePlatform; }
    public void setGamePlatform(ArrayList<Platform> gamePlatform) { this.gamePlatform = gamePlatform; }

    public String getIconPath() {
        return mediaIcon;
    }
}
