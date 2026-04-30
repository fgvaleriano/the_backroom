package edu.tangingina.thebackroom.util;

import javafx.scene.text.Font;

public class FontLoader {
    //loads font for easy access cuz css be breaking down
    public static final String regular_family;
    public static final String bold_family;
    public static final String semi_bold_family;
    public static final String light_family;
    public static final String extra_family;
    public static final String plex_family;

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

        Font extra = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/RedditSansCondensed-ExtraBold.ttf"),
                10
        );

        Font plex = Font.loadFont(
                FontLoader.class.getResourceAsStream("/edu/tangingina/thebackroom/assets/IBMPlexMono-Regular.ttf"),
                10
        );

        regular_family = regular.getFamily();
        bold_family = bold.getFamily();
        semi_bold_family =  semi_bold.getFamily();
        light_family = light.getFamily();
        extra_family = extra.getFamily();
        plex_family = plex.getFamily();
    }

    public static Font regular(double size) {
        return Font.font(regular_family, size);
    }

    public static Font bold(double size) {
        return Font.font(bold_family, size);
    }

    public static Font light(double size) {
        return Font.font(light_family, size);
    }

    public static Font semibold(double size) {
        return Font.font(semi_bold_family, size);
    }

    public static Font extra(double size) {
        return Font.font(extra_family, size);
    }

    public static Font plex(double size) {
        return Font.font(plex_family, size);
    }

    public static void debug() {
        System.out.println("--- FontLoader Debug ---");
        String[] families = {regular_family, bold_family, semi_bold_family, light_family};
        String[] names = {"Regular", "Bold", "Semi-Bold", "Light"};

        for (int i = 0; i < families.length; i++) {
            if (families[i] == null) {
                System.err.println("❌ " + names[i] + ": FAILED (File not found or invalid)");
            } else {
                System.out.println("✅ " + names[i] + ": LOADED (Family: " + families[i] + ")");
            }
        }
        System.out.println("-------------------------");
    }
}
