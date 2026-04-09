package edu.tangingina.thebackroom.util;

import javafx.scene.text.Font;

public class FontLoader {
    //loads font for easy access cuz css be breaking down

    public static final String regular_family;
    public static final String bold_family;
    public static final String semi_bold_family;
    public static final String light_family;

    static {
        Font regular = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/RedditSansCondensed-Regular.ttf"),
                10 // dummy size (doesn't matter)
        );

        Font bold = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/RedditSansCondensed-Bold.ttf"),
                10
        );

        Font semi_bold = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/RedditSansCondensed-SemiBold.ttf"),
                10
        );

        Font light = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/RedditSansCondensed-Light.ttf"),
                10
        );

        regular_family = regular.getFamily();
        bold_family = bold.getFamily();
        semi_bold_family =  semi_bold.getFamily();
        light_family = light.getFamily();
    }
}
