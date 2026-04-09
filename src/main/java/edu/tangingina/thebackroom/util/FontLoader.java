package edu.tangingina.thebackroom.util;

import javafx.scene.text.Font;

public class FontLoader {
    //loads font for easy access cuz css be breaking down
    public static final String regular_family;
    public static final String bold_family;
    public static final String semi_bold_family;
    public static final String light_family;
    public static final String extra_family;

    static {
        Font regular = Font.loadFont(
                FontLoader.class.getResourceAsStream(
                        "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Regular.ttf"),
                10 // dummy size (doesn't matter)
        );
        Font bold = Font.loadFont(
                FontLoader.class.getResourceAsStream(
                        "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Bold.ttf"),
                10
        );
        Font semi_bold = Font.loadFont(
                FontLoader.class.getResourceAsStream(
                        "/edu/tangingina/thebackroom/assets/RedditSansCondensed-SemiBold.ttf"),
                10
        );
        Font light = Font.loadFont(
                FontLoader.class.getResourceAsStream(
                        "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Light.ttf"),
                10
        );
        Font extra = Font.loadFont(
                FontLoader.class.getResourceAsStream(
                        "/edu/tangingina/thebackroom/assets/RedditSansCondensed-ExtraBold.ttf"),
                10
        );

        regular_family = regular.getFamily();
        bold_family = bold.getFamily();
        semi_bold_family =  semi_bold.getFamily();
        light_family = light.getFamily();
        extra_family = extra.getFamily();
    }

    public static Font regular(double size) {
        var stream = FontLoader.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Regular.ttf");
        System.out.println("STREAM = " + stream);

        Font font = Font.loadFont(stream, size);
        System.out.println("FONT = " + font);

        if (font != null) {
            System.out.println("FONT NAME = " + font.getName());
            System.out.println("FAMILY = " + font.getFamily());
        }

        return font;
    }

    public static Font bold(double size) {
        var stream = FontLoader.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Bold.ttf");
        System.out.println("STREAM = " + stream);

        Font font = Font.loadFont(stream, size);
        System.out.println("FONT = " + font);

        if (font != null) {
            System.out.println("FONT NAME = " + font.getName());
            System.out.println("FAMILY = " + font.getFamily());
        }

        return font;
    }

    public static Font light(double size) {
        var stream = FontLoader.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/RedditSansCondensed-Light.ttf");
        System.out.println("STREAM = " + stream);

        Font font = Font.loadFont(stream, size);
        System.out.println("FONT = " + font);

        if (font != null) {
            System.out.println("FONT NAME = " + font.getName());
            System.out.println("FAMILY = " + font.getFamily());
        }

        return font;
    }

    public static Font semibold(double size) {
        var stream = FontLoader.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/RedditSansCondensed-SemiBold.ttf");
        System.out.println("STREAM = " + stream);

        Font font = Font.loadFont(stream, size);
        System.out.println("FONT = " + font);

        if (font != null) {
            System.out.println("FONT NAME = " + font.getName());
            System.out.println("FAMILY = " + font.getFamily());
        }

        return font;
    }

    public static Font extra(double size) {
        var stream = FontLoader.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/RedditSansCondensed-ExtraBold.ttf");
        System.out.println("STREAM = " + stream);

        Font font = Font.loadFont(stream, size);
        System.out.println("FONT = " + font);

        if (font != null) {
            System.out.println("FONT NAME = " + font.getName());
            System.out.println("FAMILY = " + font.getFamily());
        }

        return font;
    }
}
