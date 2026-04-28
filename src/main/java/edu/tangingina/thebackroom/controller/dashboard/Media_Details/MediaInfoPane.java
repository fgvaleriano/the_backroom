package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import edu.tangingina.thebackroom.controller.GameDetailsForm;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MediaInfoPane extends VBox {
    /*
        Handles the right side of the details page that holds info
        abt the media
     */

    private Label title, divider, creatorLabel, synopsisLabel, plainDeets;
    private VBox headerBox, detailsBox;

    public MediaInfoPane(BaseMedia media){
        getStyleClass().add("media-info-pane");
        setSpacing(22);

        title = new Label(media.getTitle());
        title.setFont(FontLoader.extra(32));
        title.getStyleClass().add("media-title");
        title.setWrapText(true);

        creatorLabel = new Label(media.getMainCreator());
        creatorLabel.setFont(FontLoader.plex(28));
        creatorLabel.getStyleClass().add("media-creator");

        headerBox = new VBox(8, title, creatorLabel);
        headerBox.getStyleClass().add("media-header");

        divider = new Label();
        divider.getStyleClass().add("media-divider");

        synopsisLabel = new Label(media.getSynopsis());
        synopsisLabel.getStyleClass().add("media-synopsis");
        synopsisLabel.setWrapText(true);

        detailsBox = new VBox(18);
        detailsBox.getStyleClass().add("media-details-box");

        for (DetailField field : media.getDetailFields()) {
            if (field.hasLabel()) {
                detailsBox.getChildren().add(createLabelDetail(field.getLabel(), field.getValue()));
            } else {
                plainDeets = new Label(field.getValue());
                plainDeets.getStyleClass().add("media-plain-info");
                plainDeets.setWrapText(true);
                detailsBox.getChildren().add(plainDeets);
            }
        }

        getChildren().addAll(headerBox, divider, synopsisLabel, detailsBox);

    }

    private TextFlow createLabelDetail(String label, String value){
        Text labelText = new Text(label + ": ");
        labelText.setFont(FontLoader.plex(28));
        labelText.getStyleClass().add("media-label");

        Text valueText = new Text(value);
        valueText.setFont(FontLoader.plex(28));
        valueText.getStyleClass().add("media-value");

        TextFlow textFlow = new TextFlow(labelText, valueText);
        textFlow.getStyleClass().add("details");
        textFlow.setPrefWidth(650);
        textFlow.setPrefHeight(650);

        return textFlow;
    }
}
