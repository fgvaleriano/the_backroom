package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.UpdateArchive;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.InternetManager;
import edu.tangingina.thebackroom.util.UpdateArchiveQueries;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

public class MediaDetailsView extends BaseView {

    private int mediaID;
    private Media media;
    private Runnable onBack;

    public MediaDetailsView(int mediaID, Runnable onBack) {
        super();
        this.mediaID = mediaID;
        // Basic data fetch
        this.media = TheBackroom.mediaList.get(mediaID);
        this.onBack = onBack;
        buildLayout();
    }

    private int getMediaID() {
        return mediaID;
    }

    @Override
    protected void buildLayout() {
        root.setPadding(Insets.EMPTY);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("detail-page");

        //The Gray Background for the entire media info
        VBox detailCard = new VBox(25);
        detailCard.setMaxWidth(1150);
        detailCard.getStyleClass().add("detail-card");
        VBox.setVgrow(detailCard, Priority.ALWAYS);


        HBox contentLayout = new HBox(90);
        contentLayout.getStyleClass().add("content-layout");
        contentLayout.setAlignment(Pos.TOP_CENTER);

        HBox actionBar = createActionBar();
        VBox leftSide = createLeftSide();

        VBox rightSide = createRightSide();


        contentLayout.getChildren().addAll(leftSide, rightSide);
        detailCard.getChildren().addAll(actionBar, contentLayout);
        root.getChildren().add(detailCard);
    }

    public HBox createActionBar(){
        HBox actionBar = new HBox();
        actionBar.getStyleClass().add("action-bar");
        actionBar.setAlignment(Pos.CENTER_LEFT);

        Button back = new Button("← Back");
        back.setFont(FontLoader.bold(25));
        back.getStyleClass().add("btn-guest");

        back.setOnAction(e -> {
            onBack.run();
        });

        Button update = new Button("Update");
        update.getStyleClass().add("btn-guest");
        update.setFont(FontLoader.bold(25));
        update.setOnAction(e -> {
            System.out.println("Update media: " + media);

            UpdateArchive.updateArchiveView(media.getID(), media.getMediaType());
        });

        Region spacce = new Region();
        HBox.setHgrow(spacce, Priority.ALWAYS);

        actionBar.getChildren().addAll(back, spacce, update);
        return actionBar;
    }

    private VBox createLeftSide(){
        VBox leftSide = new VBox(28);
        leftSide.getStyleClass().add("left-side");
        leftSide.setAlignment(Pos.CENTER);

        CardLayout cover = new CardLayout(media.getMediaName(), media.getMediaIcon(), 250, 390);

        Button btn = new Button();
        btn.getStyleClass().add("image-button");
        Image img = new Image(
                getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/access_btn.png")
        );
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(200);
        btn.setGraphic(view);

        btn.setOnAction(e -> {
            InternetManager im = TheBackroom.im;
            im.openWebsite(media);
        });

        leftSide.getChildren().addAll(cover, btn);
        return leftSide;
    }

    public VBox createRightSide(){
        VBox rightSide = new VBox(22);
        Label title, divider, creatorLabel, synopsisLabel, plainDeets;

        title = new Label(media.getMediaName());
        title.setFont(FontLoader.extra(40));
        title.setWrapText(true);

        String synopsis = media.getSynopsis();

        if(synopsis == null || synopsis.isEmpty()){
            synopsis = "No summary input";
        }

        synopsisLabel = new Label(synopsis);
        synopsisLabel.setFont(FontLoader.plex(17));
        synopsisLabel.setWrapText(true);
        synopsisLabel.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);

        if(synopsis.equals("No input")) synopsisLabel.getStyleClass().add("media-no-input");


        VBox headerBox = new VBox(8, title);
        divider = new Label();
        divider.getStyleClass().add("media-divider");

        VBox detailsBox = null;
        MediaType mediaType = media.getMediaType();

        if(mediaType.equals(MediaType.Book)){
            detailsBox = createBookDetails();
        }else if(mediaType.equals(MediaType.Movie)){
            detailsBox = createMovieDetails();
        }else if(mediaType.equals(MediaType.TvShow)){
            detailsBox = createTvShowDetails();
        }else if(mediaType.equals(MediaType.Game)){
            detailsBox = createGameDetails();
        }
        rightSide.getChildren().addAll(headerBox, divider, synopsisLabel, detailsBox);
        return rightSide;
    }

    public VBox createBookDetails(){
        VBox detailsBox = new VBox(15);
        detailsBox.getStyleClass().add("media-details-box");

        addDetail(detailsBox, "Author", formatPersonList(media.getMediaPersonnel()));
        addDetail(detailsBox, "Genre", formatCategoryList(media.getMediaGenres()));
        addDetail(detailsBox, "Release Year", media.getReleaseYear());
        addDetail(detailsBox, "Publisher", formatCompanyList(media.getMediaCompany(), "Publisher"));
        addDetail(detailsBox, "ISBN", media.getISBN());
        addDetail(detailsBox, "Edition", media.getEdition());
        addDetail(detailsBox, "Page Count", media.getPageCount());
        return detailsBox;
    }

    public VBox createMovieDetails(){
        VBox detailsBox = new VBox(15);

        detailsBox.getStyleClass().add("media-details-box");
        addDetail(detailsBox, "Director", formatPersonList(media.getMediaPersonnel()));
        addDetail(detailsBox, "Production Studio", formatCompanyList(media.getMediaCompany(), "Production Studio"));
        addDetail(detailsBox, "Genre", formatCategoryList(media.getMediaGenres()));
        addDetail(detailsBox, "Release Year", media.getReleaseYear());
        addDetail(detailsBox, "Duration (minutes)", media.getDuration());
        addDetail(detailsBox, "Language", media.getLanguage());
        return detailsBox;
    }

    public VBox createTvShowDetails(){
        VBox detailsBox = new VBox(15);
        detailsBox.getStyleClass().add("media-details-box");

        addDetail(detailsBox, "Director", formatPersonList(media.getMediaPersonnel()));
        addDetail(detailsBox, "Production Studio", formatCompanyList(media.getMediaCompany(), "Production Studio"));
        addDetail(detailsBox, "Status", media.getStatus());
        addDetail(detailsBox, "Genre", formatCategoryList(media.getMediaGenres()));
        addDetail(detailsBox, "Release Year", media.getReleaseYear());
        addDetail(detailsBox, "Season Count", media.getSeasonCount());
        addDetail(detailsBox, "Episode Count", media.getEpisodeCount());
        return detailsBox;
    }
    public VBox createGameDetails(){
        VBox detailsBox = new VBox(15);
        detailsBox.getStyleClass().add("media-details-box");

        addDetail(detailsBox, "Game Developer", formatPersonList(media.getMediaPersonnel()));
        addDetail(detailsBox, "Game Studio", formatCompanyList(media.getMediaCompany(), "Game Studio"));
        addDetail(detailsBox, "Genre", formatCategoryList(media.getMediaGenres()));
        addDetail(detailsBox, "Release Year", media.getReleaseYear());
        addDetail(detailsBox, "Platform", formatGamePlatform(media.getGamePlatform()));
        addDetail(detailsBox, "Game Mode", formatGameModeList(media.getGameMode()));
        addDetail(detailsBox, "Game Publisher", formatCompanyList(media.getMediaCompany(), "Publisher"));
        addDetail(detailsBox, "Game Engine", media.getGameEngine());


        HBox sysReq = new HBox(10);
        sysReq.setAlignment(Pos.TOP_LEFT);

        Label sysReqLabel = new Label("System Requirements: ");
        sysReqLabel.setFont(FontLoader.bold(18));
        sysReqLabel.setMinWidth(170);
        sysReqLabel.setMaxWidth(170);

        String reqs = media.getSystemRequirements();
        if (reqs == null || reqs.trim().isEmpty()) reqs = "No input";

        Label reqsValue = new Label(reqs);
        reqsValue.setFont(FontLoader.regular(18));
        reqsValue.setWrapText(true);
        reqsValue.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        reqsValue.setMaxWidth(750);

        HBox.setHgrow(reqsValue, Priority.ALWAYS);
        if (reqs.equals("No input")) {
            reqsValue.getStyleClass().add("media-no-input");
        }
        sysReq.getChildren().addAll(sysReqLabel, reqsValue);
        detailsBox.getChildren().add(sysReq);

        return detailsBox;
    }

    private String formatPersonList(ArrayList<Person> personList) {
        if (personList == null || personList.isEmpty()) return "No input";

        //Turn many-to-many relationships, or data into one string
        return personList.stream()
                .map(Person::getPersonName)
                .reduce((t, u) -> t + ", " + u)
                .orElse("No input");
    }

    private String formatCompanyList(ArrayList<Company> companyList, String role) {
        if (companyList == null || companyList.isEmpty()) return "No input";

        //Turn many-to-many relationships, or data into one string
        return companyList.stream()
                .filter(c -> role.equals(c.getCompanyRole()))
                .map(Company::getCompanyName)
                .reduce((t, u) -> t + ", " + u)
                .orElse("No input");
    }

    private String formatCategoryList(ArrayList<Category> categoryList) {
        if (categoryList == null || categoryList.isEmpty()) return "No input";

        //Turn many-to-many relationships, or data into one string
        return categoryList.stream()
                .map(Category::getCategoryName)
                .reduce((t, u) -> t + ", " + u)
                .orElse("No input");
    }

    private String formatGameModeList(ArrayList<GameMode> gameModeList) {
        if (gameModeList == null || gameModeList.isEmpty()) return "No input";

        //Turn many-to-many relationships, or data into one string
        return gameModeList.stream()
                .map(GameMode::getGameModeName)
                .reduce((t, u) -> t + ", " + u)
                .orElse("No input");
    }

    private String formatGamePlatform(ArrayList<Platform> gamePlatformList) {
        if (gamePlatformList == null || gamePlatformList.isEmpty()) return "No input";

        //Turn many-to-many relationships, or data into one string
        return gamePlatformList.stream()
                .map(Platform::getPlatformName)
                .reduce((t, u) -> t + ", " + u)
                .orElse("No input");
    }

    private void addDetail(VBox container, String label, String value) {
        String displayValue;

        if (value == null || value.trim().isEmpty() || value.equals("null")) {
            displayValue = "No input";
        } else {
            displayValue = value;
        }

        HBox row = new HBox(10);

        Label labelTitle = new Label(label + ": ");
        labelTitle.setFont(FontLoader.bold(18));
        labelTitle.setMinWidth(150);

        Label labelValue = new Label(displayValue);
        labelValue.setFont(FontLoader.regular(18));
        labelValue.setWrapText(true);
        labelValue.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        HBox.setHgrow(labelValue, Priority.ALWAYS);

        // Styling for no input
        if (displayValue.equals("No input")) {
            labelValue.setFont(FontLoader.light(18));
            labelValue.getStyleClass().add("media-no-input");
        }

        row.getChildren().addAll(labelTitle, labelValue);
        container.getChildren().add(row);
    }

}