package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.dashboard.DashboardShell;
import edu.tangingina.thebackroom.controller.dashboard.NavbarComponent;
import edu.tangingina.thebackroom.dao.impl.MediaDaoImpl;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.FileManager;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.Utility;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.text.Element;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static edu.tangingina.thebackroom.TheBackroom.mediaList;
import static edu.tangingina.thebackroom.TheBackroom.mediaUniqID;

public class BookDetailsForm extends BaseMediaForm {
    /*
        Handles specific inputs for books such as:
            - ISBN
            - Genre
            - Page Count
            - Edition
     */

    private MultiValueField authorField, genreField, publisherField;
    private FormFieldGroup titleField, ISBNfield, pageField, editionField, synopsisField, yearField;
    private ImageFileField widgetField;
    private AccessLinkField linkField;
    private boolean isUpdateMode = false;
    private int mediaId = -1;
    private Button btn;
    private Label errorLabel;

    private Media oldMedia;

    public BookDetailsForm() {
        view.getChildren().add(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        authorField = FormFieldFactory.createMultiValueField("Author", 175);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        publisherField = FormFieldFactory.createMultiValueField("Publisher", 175);
        pageField = FormFieldFactory.createTextField("Page Count", 120);
        ISBNfield = FormFieldFactory.createTextField("ISBN", 200);
        editionField = FormFieldFactory.createTextField("Edition", 200);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Book Cover", 250);
        yearField = FormFieldFactory.createYearPicker("Release Year", 120);

        formColumn().getChildren().addAll(
                titleField.getView(),
                authorField.getView(),
                publisherField.getView(),
                synopsisField.getView(),
                genreField.getView(),
                yearField.getView(),
                pageField.getView(),
                ISBNfield.getView(),
                editionField.getView(),
                linkField.getView(),
                widgetField.getView(),
                addButton()
        );

        Node lastNode = formColumn().getChildren().get(formColumn().getChildren().size() - 1);
        VBox.setMargin(lastNode, new javafx.geometry.Insets(0, 0, 0, 0));
    }

    private Node addButton(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        errorLabel = new Label("An error occurred, please try again");
        errorLabel.setFont(FontLoader.bold(20));
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        refreshButton();

        btn.setOnAction(e -> {
            if (validateInputs()) {
                if (isUpdateMode) {
                    handleUpdate();
                } else {
                    handleAdd();
                }
            }
        });

        HBox btnContainer = new HBox(btn);
        btnContainer.setAlignment(Pos.CENTER);

        VBox container = new VBox(8, btnContainer, errorLabel);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(520);

        return container;
    }

    //input validation
    private boolean validateInputs() {
        boolean isValid = true;

        titleField.clearError();
        ISBNfield.clearError();
        pageField.clearError();
        linkField.clearError();
        genreField.clearError();
        publisherField.clearError();
        authorField.clearError();
        synopsisField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isValid = false;
        }

        if (authorField.isEmpty()) {
            authorField.showError();
            isValid = false;
        }

        if (publisherField.isEmpty()) {
            publisherField.showError();
            isValid = false;
        }

        if (ISBNfield.isEmpty()) {
            ISBNfield.showError();
            isValid = false;
        }

        if (genreField.isEmpty()) {
            genreField.showError();
            isValid = false;
        }

        if (pageField.isEmpty()) {
            pageField.showError();
            isValid = false;
        }

        if (synopsisField.isEmpty()) {
            synopsisField.showError();
            isValid = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isValid = false;
        }

        return isValid;
    }

    public void populateForm (String author, String category, String publisher, String path,
                              String links, Media media) {

        TheBackroom.util.setIfNotNull(titleField, media.getMediaName());
        TheBackroom.util.setIfNotNull(ISBNfield, media.getISBN());
        TheBackroom.util.setIfNotNull(editionField, media.getEdition());
        TheBackroom.util.setIfNotNull(synopsisField, media.getSynopsis());
        TheBackroom.util.setIfNotNull(pageField, media.getPageCount());

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        if (yearPicker != null && media.getReleaseYear() != null && !media.getReleaseYear().equals("null")) {
            yearPicker.setValue(Integer.valueOf(media.getReleaseYear()));
        }

        TheBackroom.util.setIfNotNull(authorField, author);
        TheBackroom.util.setIfNotNull(publisherField, publisher);
        TheBackroom.util.setIfNotNull(genreField, category);
        TheBackroom.util.setIfNotNull(linkField, links);
        TheBackroom.util.setIfNotNull(widgetField, path);

        setOldMedia(media);
    }

    //call beofre showing update form
    public void setUpdateMode(int mediaId) {
        this.isUpdateMode = true;
        this.mediaId = mediaId;
        refreshButton();
    }

    private void refreshButton() {
        String assetName = isUpdateMode ? "update_btn.png" : "add_btn.png";
        Image img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/" + assetName));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);
        btn.setGraphic(view);
    }

    private void handleAdd() {
        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Book;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();

        String year = "2024";
        if(yearPicker != null){
            year = String.valueOf(yearPicker.getValue());
        }

        String imgIcon = fm.saveIMGRelative(widgetField.getSelectedFile());
        List<String> genre = genreField.getValues();
        List<AccessLinkField.AccessLink> onlineAccess = linkField.getValues();
        String isbn = ISBNfield.getUserInput();
        String edition = editionField.getUserInput();
        String pageCount = pageField.getUserInput();
        List<String> author = authorField.getValues();
        List<String> publisher = publisherField.getValues();

        ArrayList<Category> mediaGenre = util.ensureCategoryExist(genre);
        ArrayList<Website> mediaWebsite = util.ensureWebsiteExists(onlineAccess);
        ArrayList<Person> bookAuthor = util.ensurePersonExist(author, "Author");
        ArrayList<Company> bookPublisher = util.ensureCompanyExists(publisher, "Publisher");


        Media media = new Media(0, title, mediaType, year, synopsis, imgIcon, mediaWebsite, mediaGenre);
        media.setBookDetails(isbn, pageCount, edition, bookAuthor, bookPublisher);

        try{
            mediaDao.addMedia(media);
            mediaList.put(media.getID(), media);
            mediaUniqID.put(util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());
            TheBackroom.bookMedia.add(media.getID());

            AddArchive_v2.closeWindow();

        }catch (Exception e1){
            e1.getMessage();
        }
    }

    private void handleUpdate() {
        System.out.println("Update mode");

        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Book;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        String year = "2024";
        if (yearPicker != null) year = String.valueOf(yearPicker.getValue());

        // saves only if nagpick ng new path
        String imgIcon = widgetField.getSelectedFile() != null
                ? fm.saveIMGRelative(widgetField.getSelectedFile())
                : widgetField.getCurrentPath();

        List<String> genre = genreField.getValues();
        List<AccessLinkField.AccessLink> onlineAccess = linkField.getValues();
        String isbn = ISBNfield.getUserInput();
        String edition = editionField.getUserInput();
        String pageCount = pageField.getUserInput();
        List<String> author = authorField.getValues();
        List<String> publisher = publisherField.getValues();

        ArrayList<Category> mediaGenre = util.ensureCategoryExist(genre);
        ArrayList<Website> mediaWebsite = util.ensureWebsiteExists(onlineAccess);
        ArrayList<Person> bookAuthor = util.ensurePersonExist(author, "Author");
        ArrayList<Company> bookPublisher = util.ensureCompanyExists(publisher, "Publisher");

        Media media = new Media(mediaId, title, mediaType, year, synopsis, imgIcon, mediaWebsite, mediaGenre);
        media.setBookDetails(isbn, pageCount, edition, bookAuthor, bookPublisher);

        try{
            //if the name was updated, then we update our cache
            if(!media.getMediaName().equals(oldMedia.getMediaName())){
                TheBackroom.mediaUniqID.remove(TheBackroom.util.getMediaKey(oldMedia.getMediaName(), oldMedia.getMediaType().name(), oldMedia.getReleaseYear()));
                TheBackroom.mediaUniqID.put(TheBackroom.util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());
            }

            mediaDao.updateMedia(media, oldMedia);
            mediaList.put(mediaId, media);
            UpdateArchive.closeWindow();
        }catch (Exception e){
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void setOldMedia(Media media){
        this.oldMedia = media;
    }


}
