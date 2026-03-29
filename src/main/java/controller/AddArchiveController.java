package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import kotlin.random.PlatformRandomKt;

import java.net.URL;
import java.util.ResourceBundle;

/*NOTE:
    Do we need to add, wherein if a person typed something on the bookx, and midway
    change to another type, do we clear anything typed???OR let it stay?????
*/

public class AddArchiveController implements Initializable {
    private boolean updatingMenu = false;
    @FXML private Label addArchiveLabel;

    @FXML private AnchorPane root;
    @FXML private VBox mainBox1;
    @FXML private GridPane nameGrid;


    @FXML private Button nxtBtn;
    @FXML private Button backBtn;
    @FXML private Button addBtn;


    @FXML private TextField nameField;
    @FXML private ComboBox typeMenu;
    @FXML private TextArea synopField;
    @FXML private TextField dateField;

    //---------Book Variables--------
    @FXML private VBox bookBox1;
    @FXML private VBox bookBox2;
    @FXML private ComboBox authorMenu;
    @FXML private FlowPane addAuthorPane;
    @FXML private ScrollPane addAuthorScrollPane;
    @FXML private ComboBox publisherMenu;
    @FXML private FlowPane addPublisherPane;
    @FXML private ScrollPane addPublisherScrollPane;
    @FXML private TextField isbnField;
    @FXML private TextField pageCountField;
    @FXML private TextField editionField;

    private ObservableList<String> authors;
    private FilteredList<String> authorFilter;

    private ObservableList<String> publishers;
    private FilteredList<String> publisherFilter;

    //----------------------------------

    //---------Game Variables--------
    @FXML private VBox gameBox1;
    @FXML private VBox gameBox2;
    @FXML private ComboBox gameEngineMenu;
    @FXML private TextArea systemRequire;

    @FXML private ComboBox gameModeMenu;
    @FXML private FlowPane addGameModePane;
    @FXML private ScrollPane addGameModeScrollPane;

    @FXML private ComboBox gamePlatformMenu;
    @FXML private FlowPane addPlatformPane;
    @FXML private ScrollPane addPlatformScrollPane;

    private ObservableList<String> gameModes;
    private FilteredList<String> gameModeFilter;

    private ObservableList<String> gamePlatforms;
    private FilteredList<String> gamePlatformFilter;
    //----------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainInitialize();
        bookInitialize();
        gameInitialize();
    }

    @FXML
    public void nextAdd(){
        if(mainBox1.isVisible()){
            String name = nameField.getText();
            String type = (String) typeMenu.getSelectionModel().getSelectedItem();
            String synop = synopField.getText();
            String date = dateField.getText();

            //System.out.println("Name: " + name);
            if(!checkMainEmpty(name, type, synop, date)){
                if(type.equals("Books")){
                    mainBox1.setVisible(false);
                    bookBox1.setVisible(true);
                }else if(type.equals("Games")){
                    mainBox1.setVisible(false);
                    gameBox1.setVisible(true);
                }
            }
        } else if(bookBox1.isVisible()){
            String isbn = isbnField.getText();
            String pageCount = pageCountField.getText();
            String edition = editionField.getText();

            if(!checkBook1Empty(isbn, pageCount, edition)){
                bookBox1.setVisible(false);
                bookBox2.setVisible(true);
            }
        } else if(gameBox1.isVisible()){
            String gameEngine = (String) gameEngineMenu.getSelectionModel().getSelectedItem();
            String systemRequirement = systemRequire.getText();

            if(!checkGame1Empty(gameEngine, systemRequirement)){
                gameBox1.setVisible(false);
                gameBox2.setVisible(true);
            }
        }
    }
    @FXML
    public void goBack(){
        if(bookBox1.isVisible()){
            bookBox1.setVisible(false);
            mainBox1.setVisible(true);
        }else if(bookBox2.isVisible()){
            bookBox2.setVisible(false);
            bookBox1.setVisible(true);
        } else if(gameBox1.isVisible()){
            gameBox1.setVisible(false);
            mainBox1.setVisible(true);
        }else if(gameBox2.isVisible()){
            gameBox2.setVisible(false);
            gameBox1.setVisible(true);
        }
    }

    @FXML
    public void saveMedia(){}

    //This unfocsing anything on the text fields, and stuff...
    @FXML
    public void bg1Click(){
        addArchiveLabel.getScene().getRoot().requestFocus();
    }

    @FXML
    public void bg2Click(){
        addArchiveLabel.getScene().getRoot().requestFocus();
    }

    //------------Methods for Putting Values on Combo Boxes and Adding Listeners-------------------
    public void setAuthorMenu(){
        authors = FXCollections.observableArrayList("Saori", "Dalia", "Marika", "Rika");
        authorFilter = new FilteredList<>(authors);
    }

    public void setPublisherMenu(){
        publishers = FXCollections.observableArrayList("Flins", "Varka", "Itto", "Aether");
        publisherFilter = new FilteredList<>(publishers);
    }

    public void setGameModeMenu(){
        gameModes = FXCollections.observableArrayList("Single-player", "PvP", "Online Co-op", "Multiplayer");
        gameModeFilter = new FilteredList<>(gameModes);
    }

    public void setGamePlatformMenu(){
        gamePlatforms = FXCollections.observableArrayList("PC", "Play Station", "Mobile", "Nintendo");
        gamePlatformFilter = new FilteredList<>(gamePlatforms);
    }


    public void addListenerAuthorMenu(){
        authorMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            if(updatingMenu) return; //safeguarders..pra, the texteditor would not do anything, if the program itself is  updating the selected item
            //especially if the user picked something, then we dont do anythinghere with the text..
            if(!authorMenu.isFocused()) return;

            Platform.runLater(() -> {
                authorFilter.setPredicate(item -> {
                    //if the user inputted nothing, jsut show the original list
                    if(input == null || input.isEmpty()) return true;

                    //this returns all items that are macthing as the user types
                    return item.toLowerCase().startsWith(input.toLowerCase());
                });

                if(!authorFilter.isEmpty()){
                    //Just refresh the dropdown menu pra ma see, the actual filtered stuff...
                    authorMenu.hide();
                    authorMenu.show();
                }else{
                    authorMenu.hide();
                }
            });

        });

        //this is for handling that if the user is typing something, we show the menu
        authorMenu.getEditor().focusedProperty().addListener((obs, oldVal, isFocus) -> {
            if(isFocus){
                //slight delay after typing to show the items to be selected/ or the item currently selected after refocusing
                Platform.runLater(() -> {
                    String currentText = (String) authorMenu.getSelectionModel().getSelectedItem();

                    authorFilter.setPredicate(item -> {
                        if(currentText == null || currentText.isEmpty()) return true;
                        return item.toLowerCase().startsWith(currentText.toLowerCase());
                    });

                    if(!authorFilter.isEmpty()){
                        authorMenu.hide();
                        authorMenu.show();
                    }
                });
            }
        });

        //this is for if instead the user typed something, or selected an item
        //we update also the text to that
        authorMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, picked) -> {
            if(picked != null){
                Platform.runLater(() -> {
                    updatingMenu = true;
                    authorMenu.getEditor().setText(picked.toString());
                    authorMenu.getEditor().positionCaret(picked.toString().length()); //we move the cursor/typing things, to the last kuan of the string
                    authorMenu.hide();
                    updatingMenu = false;
                });
            }
        });
    }

    public void addListenerPublisherMenu(){
        publisherMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            if(updatingMenu) return; //safeguarders..pra, the texteditor would not do anything, if the program itself is  updating the selected item
            //especially if the user picked something, then we dont do anythinghere with the text..
            if(!publisherMenu.isFocused()) return;

            Platform.runLater(() -> {
                publisherFilter.setPredicate(item -> {
                    //if the user inputted nothing, jsut show the original list
                    if(input == null || input.isEmpty()) return true;

                    //this returns all items that are macthing as the user types
                    return item.toLowerCase().startsWith(input.toLowerCase());
                });

                if(!publisherFilter.isEmpty()){
                    //Just refresh the dropdown menu pra ma see, the actual filtered stuff...
                    publisherMenu.hide();
                    publisherMenu.show();
                }else{
                    publisherMenu.hide();
                }
            });

        });

        //this is for handling that if the user is typing something, we show the menu
        publisherMenu.getEditor().focusedProperty().addListener((obs, oldVal, isFocus) -> {
            if(isFocus){
                //slight delay after typing to show the items to be selected..
                Platform.runLater(() -> publisherMenu.show());
            }
        });

        //this is for if instead the user typed something, or selected an item
        //we update also the text to that
        publisherMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, picked) -> {
            if(picked != null){
                Platform.runLater(() -> {
                    updatingMenu = true;
                    publisherMenu.getEditor().setText(picked.toString());
                    publisherMenu.getEditor().positionCaret(picked.toString().length()); //we move the cursor/typing things, to the last kuan of the string
                    updatingMenu = false;
                });
            }
        });
    }

    public void addListenerGameModeMenu(){
        gameModeMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            if(updatingMenu) return; //safeguarders..pra, the texteditor would not do anything, if the program itself is  updating the selected item
            //especially if the user picked something, then we dont do anythinghere with the text..
            if(!gameModeMenu.isFocused()) return;

            Platform.runLater(() -> {
                gameModeFilter.setPredicate(item -> {
                    //if the user inputted nothing, jsut show the original list
                    if(input == null || input.isEmpty()) return true;

                    //this returns all items that are macthing as the user types
                    return item.toLowerCase().startsWith(input.toLowerCase());
                });

                if(!gameModeFilter.isEmpty()){
                    //Just refresh the dropdown menu pra ma see, the actual filtered stuff...
                    gameModeMenu.hide();
                    gameModeMenu.show();
                }else{
                    gameModeMenu.hide();
                }
            });

        });

        //this is for handling that if the user is typing something, we show the menu
        gameModeMenu.getEditor().focusedProperty().addListener((obs, oldVal, isFocus) -> {
            if(isFocus){
                //slight delay after typing to show the items to be selected..
                Platform.runLater(() -> gameModeMenu.show());
            }
        });

        //this is for if instead the user typed something, or selected an item
        //we update also the text to that
        gameModeMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, picked) -> {
            if(picked != null){
                Platform.runLater(() -> {
                    updatingMenu = true;
                    gameModeMenu.getEditor().setText(picked.toString());
                    gameModeMenu.getEditor().positionCaret(picked.toString().length()); //we move the cursor/typing things, to the last kuan of the string
                    updatingMenu = false;
                });
            }
        });
    }

    public void addListenerGamePlatformMenu(){
        gamePlatformMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            if(updatingMenu) return; //safeguarders..pra, the texteditor would not do anything, if the program itself is  updating the selected item
            //especially if the user picked something, then we dont do anythinghere with the text..
            if(!gamePlatformMenu.isFocused()) return;

            Platform.runLater(() -> {
                gamePlatformFilter.setPredicate(item -> {
                    //if the user inputted nothing, jsut show the original list
                    if(input == null || input.isEmpty()) return true;

                    //this returns all items that are macthing as the user types
                    return item.toLowerCase().startsWith(input.toLowerCase());
                });

                if(!gamePlatformFilter.isEmpty()){
                    //Just refresh the dropdown menu pra ma see, the actual filtered stuff...
                    gamePlatformMenu.hide();
                    gamePlatformMenu.show();
                }else{
                    gamePlatformMenu.hide();
                }
            });

        });

        //this is for handling that if the user is typing something, we show the menu
        gamePlatformMenu.getEditor().focusedProperty().addListener((obs, oldVal, isFocus) -> {
            if(isFocus){
                //slight delay after typing to show the items to be selected..
                Platform.runLater(() -> gamePlatformMenu.show());
            }
        });

        //this is for if instead the user typed something, or selected an item
        //we update also the text to that
        gamePlatformMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, picked) -> {
            if(picked != null){
                Platform.runLater(() -> {
                    updatingMenu = true;
                    gamePlatformMenu.getEditor().setText(picked.toString());
                    gamePlatformMenu.getEditor().positionCaret(picked.toString().length()); //we move the cursor/typing things, to the last kuan of the string
                    updatingMenu = false;
                });
            }
        });
    }

    //---------------------------------------------------------------------------------------------


    //---------------------Book Button Methods-----------------------------------------------------
    @FXML public void addAuthor(){
        Object author = authorMenu.getSelectionModel().getSelectedItem();

        if(author != null && !author.toString().trim().isEmpty()){
            System.out.println("Author Picked: " + author.toString());

            //if previously there is no author pa sa list, and they need it, so we add it to the list...
            if(!authors.contains(author.toString())){
                authors.add(author.toString());
            }

            HBox container = new HBox();
            container.getStyleClass().add("author_hbox");

            Button rmvBtn = new Button("X");
            Label labelAuthor = new Label(author.toString());

            container.getChildren().addAll(labelAuthor, rmvBtn);
            rmvBtn.setOnAction(e -> {
                addAuthorPane.getChildren().remove(container);
            });

            addAuthorPane.getChildren().add(container);

            Platform.runLater(() -> {
                //after adding, we reset the menu
                authorMenu.getSelectionModel().clearSelection();
                authorFilter.setPredicate(null);
                authorMenu.getEditor().clear(); //clear any text typed
                authorMenu.setValue(null);
            });


        }
    }

    @FXML public void addPublisher(){
        Object publisher = publisherMenu.getSelectionModel().getSelectedItem();

        if(publisher != null && !publisher.toString().trim().isEmpty()){
            System.out.println("Author Picked: " + publisher.toString());

            //after adding, we reset the menu
            publisherMenu.getSelectionModel().clearAndSelect(-1);
            publisherFilter.setPredicate(null);
            publisherMenu.getEditor().clear(); //clear any text typed



            //if previously there is no author pa sa list, and they need it, so we add it to the list...
            if(!publishers.contains(publisher.toString())){
                publishers.add(publisher.toString());
            }

            HBox container = new HBox();
            container.getStyleClass().add("author_hbox");

            Button rmvBtn = new Button("X");
            Label labelAuthor = new Label(publisher.toString());

            container.getChildren().addAll(labelAuthor, rmvBtn);
            rmvBtn.setOnAction(e -> {
                addPublisherPane.getChildren().remove(container);
            });

            addPublisherPane.getChildren().add(container);

        }
    }
    //---------------------------------------------------------------------------------------------

    //---------------------Game Button Methods-----------------------------------------------------
    @FXML public void addGameMode(){
        Object gameMode = gameModeMenu.getSelectionModel().getSelectedItem();

        if(gameMode != null && !gameMode.toString().trim().isEmpty()){
            System.out.println("Author Picked: " + gameMode.toString());

            //after adding, we reset the menu
            gameModeMenu.getSelectionModel().clearAndSelect(-1);
            gameModeFilter.setPredicate(null);
            gameModeMenu.getEditor().clear(); //clear any text typed



            //if previously there is no author pa sa list, and they need it, so we add it to the list...
            if(!gameModes.contains(gameMode.toString())){
                gameModes.add(gameMode.toString());
            }

            HBox container = new HBox();
            container.getStyleClass().add("author_hbox");

            Button rmvBtn = new Button("X");
            Label labelAuthor = new Label(gameMode.toString());

            container.getChildren().addAll(labelAuthor, rmvBtn);
            rmvBtn.setOnAction(e -> {
                addGameModePane.getChildren().remove(container);
            });

            addGameModePane.getChildren().add(container);

        }
    }

    @FXML public void addGamePlatform(){
        Object gamePlatform = gamePlatformMenu.getSelectionModel().getSelectedItem();

        if(gamePlatform != null && !gamePlatform.toString().trim().isEmpty()){
            System.out.println("Author Picked: " + gamePlatform.toString());

            //after adding, we reset the menu
            gamePlatformMenu.getSelectionModel().clearAndSelect(-1);
            gamePlatformFilter.setPredicate(null);
            gamePlatformMenu.getEditor().clear(); //clear any text typed



            //if previously there is no author pa sa list, and they need it, so we add it to the list...
            if(!gamePlatforms.contains(gamePlatform.toString())){
                gamePlatforms.add(gamePlatform.toString());
            }

            HBox container = new HBox();
            container.getStyleClass().add("author_hbox");

            Button rmvBtn = new Button("X");
            Label labelAuthor = new Label(gamePlatform.toString());

            container.getChildren().addAll(labelAuthor, rmvBtn);
            rmvBtn.setOnAction(e -> {
                addPlatformPane.getChildren().remove(container);
            });

            addPlatformPane.getChildren().add(container);

        }
    }
    //---------------------------------------------------------------------------------------------

    //-------------------------------Helper Methods------------------------------------------------
    public boolean checkMainEmpty(String name, String type, String synop, String date){
        boolean hasEmpty = false;

        if(name.trim().isEmpty()){//removes any spaces, newlines to check if there is actual content
            nameField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 10;");
            hasEmpty = true;
        }else{
            nameField.setStyle("-fx-border-color: transparent; -fx-border-radius: 0; -fx-border-width: 2; -fx-background-radius: 10;");
        }

        if(type == null){
            typeMenu.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2;");
            hasEmpty = true;
        }else{
            typeMenu.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        }

        if(synop.trim().isEmpty()){ //removes any spaces, newlines to check if there is actual content
            //for the kuan we assume since this add archive is moderator lang, we assume valid input na..so no more crazy stuff..
            synopField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 20; -fx-border-width: 2; -fx-background-radius: 20;");
            hasEmpty = true;
        }else{
            synopField.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        }

        if(date.trim().isEmpty()){//removes any spaces, newlines to check if there is actual content
            dateField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 10;");
            hasEmpty = true;
        }else{
            dateField.setStyle("-fx-border-color: transparent; -fx-border-radius: 0; -fx-border-width: 2; -fx-background-radius: 10;");
        }
        return hasEmpty;
    }

    public boolean checkBook1Empty(String isbn, String pageCount, String edition){
        boolean hasEmpty = false;

        if(isbn.trim().isEmpty()){//removes any spaces, newlines to check if there is actual content
            isbnField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 10;");
            hasEmpty = true;
        }else{
            isbnField.setStyle("-fx-border-color: transparent; -fx-border-radius: 0; -fx-border-width: 2; -fx-background-radius: 10;");
        }

        if(pageCount.trim().isEmpty()){ //removes any spaces, newlines to check if there is actual content
            //for the kuan we assume since this add archive is moderator lang, we assume valid input na..so no more crazy stuff..
            pageCountField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 20; -fx-border-width: 2; -fx-background-radius: 20;");
            hasEmpty = true;
        }else{
            pageCountField.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        }

        if(edition.trim().isEmpty()){//removes any spaces, newlines to check if there is actual content
            editionField.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2; -fx-background-radius: 10;");
            hasEmpty = true;
        }else{
            editionField.setStyle("-fx-border-color: transparent; -fx-border-radius: 0; -fx-border-width: 2; -fx-background-radius: 10;");
        }

        ObservableList<Node> children = addAuthorPane.getChildren();
        if(children.isEmpty()){
            addAuthorScrollPane.getStyleClass().add("error");
            hasEmpty = true;
        }else{
            addAuthorScrollPane.getStyleClass().remove("error");
        }


        return hasEmpty;
    }

    public boolean checkGame1Empty(String gameEngine, String systemRequirement){
        boolean hasEmpty = false;

        if(gameEngine == null){
            gameEngineMenu.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 10; -fx-border-width: 2;");
            hasEmpty = true;
        }else{
            gameEngineMenu.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        }

        if(systemRequirement.trim().isEmpty()){ //removes any spaces, newlines to check if there is actual content
            //for the kuan we assume since this add archive is moderator lang, we assume valid input na..so no more crazy stuff..
            systemRequire.setStyle("-fx-border-color: #bb443c; -fx-border-radius: 20; -fx-border-width: 2; -fx-background-radius: 20;");
            hasEmpty = true;
        }else{
            systemRequire.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        }

        return hasEmpty;
    }

    public void mainInitialize(){
        root.getStylesheets().add(getClass().getResource("/ui/Button_Style.css").toExternalForm());
        typeMenu.getItems().addAll("Movie", "TV Shows", "Books", "Games");
        typeMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());

        //there is still an issue  with the synopField, dont knwo what to do with it yet...
        synopField.getStylesheets().add(getClass().getResource("/ui/AddArchive_TextArea.css").toExternalForm());
        nxtBtn.getStyleClass().add("navigate-btn");
        backBtn.getStyleClass().add("navigate-btn");
        addBtn.getStyleClass().add("navigate-btn");
    }


    public void bookInitialize(){

        setAuthorMenu();
        authorMenu.setItems(authorFilter);
        authorMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());
        addListenerAuthorMenu();
        addAuthorScrollPane.getStylesheets().add(getClass().getResource("/ui/AddAuthor.css").toExternalForm());
        addAuthorScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addAuthorScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addAuthorScrollPane.setFocusTraversable(false);
        addAuthorPane.setFocusTraversable(false);


        setPublisherMenu();
        publisherMenu.setItems(publisherFilter);
        publisherMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());
        addListenerPublisherMenu();
        addPublisherScrollPane.getStylesheets().add(getClass().getResource("/ui/AddAuthor.css").toExternalForm());
        addPublisherScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addPublisherScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addPublisherScrollPane.setFocusTraversable(false);
        addPublisherPane.setFocusTraversable(false);
    }

    public void gameInitialize(){
        gameEngineMenu.getItems().addAll("Unity", "Unreal Engine", "Godot", "GameMaker");
        gameEngineMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());
        systemRequire.getStylesheets().add(getClass().getResource("/ui/AddArchive_TextArea.css").toExternalForm());

        setGameModeMenu();
        addListenerGameModeMenu();
        gameModeMenu.setItems(gameModeFilter);
        gameModeMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());
        addGameModeScrollPane.getStylesheets().add(getClass().getResource("/ui/AddAuthor.css").toExternalForm());
        addGameModeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addGameModeScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addGameModeScrollPane.setFocusTraversable(false);
        addGameModePane.setFocusTraversable(false);

        setGamePlatformMenu();
        addListenerGamePlatformMenu();
        gamePlatformMenu.setItems(gamePlatformFilter);
        gamePlatformMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());
        addPlatformScrollPane.getStylesheets().add(getClass().getResource("/ui/AddAuthor.css").toExternalForm());
        addPlatformScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addPlatformScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addPlatformScrollPane.setFocusTraversable(false);
        addPlatformPane.setFocusTraversable(false);
    }




    //---------------------------------------------------------------------------------------------


}
