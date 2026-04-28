package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

public class DetailField {
    /*
        Handles logic of retrieving data from db
     */

    private final String label;
    private final String value;

    public DetailField (String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public boolean hasLabel() {
        return label != null && !label.isBlank();
    }
}
