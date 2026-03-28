package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddArchiveController implements Initializable {
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

    @FXML private Button book2Back;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.getStylesheets().add(getClass().getResource("/ui/Button_Style.css").toExternalForm());
        typeMenu.getItems().addAll("Movie", "TV Shows", "Books", "Games");
        typeMenu.getStylesheets().add(getClass().getResource("/ui/MediaType_ComboBox.css").toExternalForm());

        //there is still an issue  with the synopField, dont knwo what to do with it yet...
        synopField.getStylesheets().add(getClass().getResource("/ui/AddArchive_TextArea.css").toExternalForm());
        nxtBtn.getStyleClass().add("navigate-btn");
        backBtn.getStyleClass().add("navigate-btn");
        addBtn.getStyleClass().add("navigate-btn");
        bookInitialize();
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


    public void addListenerAuthorMenu(){
        authorMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            //this also let to not do anything below if the person is just picking.....
            if(!authorMenu.isFocused()) return; //this just safeguard kay theres an error if person is typing and then picking

            authorMenu.getSelectionModel().clearSelection();

            //this is our condition on what we want to show on the authorMenu
            authorFilter.setPredicate(item -> {
                //if no user input show original list
                if(input.isEmpty() || input == null) return true;

                //return if any similar input
                return item.toLowerCase().startsWith(input.toLowerCase());
            });

            //we force the dropDown Menu to show if user is typing
            if(authorMenu.isFocused()){
                authorMenu.hide();
                authorMenu.show();
            }

            authorMenu.getSelectionModel().clearSelection();
        });

        //this method is for if the user focused on the combobox and then refocus again and did something we clear the selection again
        authorMenu.getEditor().focusedProperty().addListener((obs, oldVal, currentlyFocus) -> {
            if(currentlyFocus){
                authorMenu.hide();
                authorMenu.show();
                authorMenu.getSelectionModel().clearSelection();
            }else{

                //we only clear if its not on the adding na btn...kay  technically they lost focus
                if(root.getScene().getFocusOwner() != addBtn) {
                    //this is when if the user typed something and focus elsewhere and not select it then we clear it automatically
                    authorMenu.getEditor().clear();
                    authorMenu.getSelectionModel().clearAndSelect(-1);
                    authorMenu.hide();
                    authorFilter.setPredicate(null); //this reset the authorFilter

                    //System.out.println("Focused elswhere clearing everything....");
                    /*if(authorMenu.getSelectionModel().getSelectedItem() == null){
                        System.out.println("Current select after clearing: " + authorMenu.getSelectionModel().getSelectedItem());
                    }*/
                }
            }

        });
    }

    public void addListenerPublisherMenu(){
        publisherMenu.getEditor().textProperty().addListener((obs, oldVal, input) -> {

            //this also let to not do anything below if the person is just picking.....
            if(!publisherMenu.isFocused()) return; //this just safeguard kay theres an error if person is typing and then picking

            publisherMenu.getSelectionModel().clearSelection();

            //this is our condition on what we want to show on the authorMenu
            publisherFilter.setPredicate(item -> {
                //if no user input show original list
                if(input.isEmpty() || input == null) return true;

                //return if any similar input
                return item.toLowerCase().startsWith(input.toLowerCase());
            });

            //we force the dropDown Menu to show if user is typing
            if(publisherMenu.isFocused()){
                publisherMenu.hide();
                publisherMenu.show();
            }

            publisherMenu.getSelectionModel().clearSelection();
        });

        //this method is for if the user focused on the combobox and then refocus again and did something we clear the selection again
        publisherMenu.getEditor().focusedProperty().addListener((obs, oldVal, currentlyFocus) -> {
            if(currentlyFocus){
                publisherMenu.hide();
                publisherMenu.show();
                publisherMenu.getSelectionModel().clearSelection();
            }else{

                //we only clear if its not on the adding na btn...kay  technically they lost focus
                if(root.getScene().getFocusOwner() != addBtn) {
                    //this is when if the user typed something and focus elsewhere and not select it then we clear it automatically
                    publisherMenu.getEditor().clear();
                    publisherMenu.getSelectionModel().clearSelection();
                    publisherMenu.hide();
                    publisherFilter.setPredicate(null); //this reset the authorFilter

                    //System.out.println("Focused elswhere clearing everything....");
                    /*if(authorMenu.getSelectionModel().getSelectedItem() == null){
                        System.out.println("Current select after clearing: " + authorMenu.getSelectionModel().getSelectedItem());
                    }*/
                }
            }

        });
    }

    //since ediatble say, when a user type and then select the item they need, we set the text to that...
    @FXML public void pickAuthor(){
        Object picked = authorMenu.getSelectionModel().getSelectedItem();
        if(picked != null){
            authorMenu.getEditor().setText(picked.toString());
        }
    }

    @FXML public void pickPublisher(){
        Object picked = publisherMenu.getSelectionModel().getSelectedItem();
        if(picked != null){
            publisherMenu.getEditor().setText(picked.toString());
        }

    }
    //---------------------------------------------------------------------------------------------

    //---------------------Book Button Methods-----------------------------------------------------
    @FXML
    public void addAuthor(){
        Object author = authorMenu.getSelectionModel().getSelectedItem();

        if(author != null && !author.toString().trim().isEmpty()){
            System.out.println("Author Picked: " + author.toString());

            //after adding, we reset the menu
            authorMenu.getSelectionModel().clearAndSelect(-1);
            authorFilter.setPredicate(null);
            authorMenu.getEditor().clear(); //clear any text typed



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


    //---------------------------------------------------------------------------------------------


}
