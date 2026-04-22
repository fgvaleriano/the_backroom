package edu.tangingina.thebackroom.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.text.Element;

public class BookDetailsForm extends BaseMediaForm {
    /*
        Handles specific inputs for books such as:
            - ISBN
            - Genre
            - Page Count
            - Edition
     */

    private MultiValueField authorField, genreField;
    private FormFieldGroup titleField, publisherField,
            ISBNfield, pageField, editionField, linkField, widgetField, synopsisField;

    public BookDetailsForm() {
        view.getChildren().add(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        authorField = FormFieldFactory.createMultiValueField("Author", 175);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        publisherField = FormFieldFactory.createTextField("Publisher", 175);
        pageField = FormFieldFactory.createTextField("Page Count", 120);
        ISBNfield = FormFieldFactory.createTextField("ISBN", 120);
        editionField = FormFieldFactory.createTextField("Edition", 120);
        linkField = FormFieldFactory.createTextField("Access Link", 520);
        widgetField = FormFieldFactory.createTextField("Book Cover", 520);

        formColumn().getChildren().addAll(
                titleField.getView(),
                authorField.getView(),
                publisherField.getView(),
                synopsisField.getView(),
                genreField.getView(),
                FormFieldFactory.createYearPicker("Release Year", 120),
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
        Button btn = new Button();
        btn.getStyleClass().add("image-button");
        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/add_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

        btn.setGraphic(view);
        btn.setOnAction(e -> {
            validateInputs();

            if (validateInputs()) {
                AddArchive_v2.closeWindow();
            }
        });

        HBox container = new HBox(btn);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(520);

        return container;
    }

    //input validation
    private boolean validateInputs() {
        boolean isValid = false;

        titleField.clearError();
        ISBNfield.clearError();
        pageField.clearError();
        linkField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isValid = false;
        }

        if (ISBNfield.isEmpty()) {
            ISBNfield.showError();
            isValid = false;
        }

        if (pageField.isEmpty()) {
            pageField.showError();
            isValid = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isValid = false;
        }

        return isValid;
    }

    public MultiValueField getAuthorField() {
        return authorField;
    }

    public MultiValueField getGenreField() {
        return genreField;
    }

}
