package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.List;

public class SampleMediaData {

    public static List<BaseMedia> getAllMedia() {
        return List.of(
                getAddieLaRue(),
                getNoLongerHuman(),
                getLordOfTheFlies(),
                getDracula(),
                getTheSilentPatient(),
                getTheSilenceofTheLambs()
        );
    }

    public static List<BookMedia> getSampleBooks() {
        return List.of(
                getAddieLaRue(),
                getNoLongerHuman(),
                getLordOfTheFlies(),
                getDracula()
        );
    }

    public static List<FilmMedia> getSampleFilms() {
        return List.of(
                getTheSilentPatient(),
                getTheSilenceofTheLambs()
        );
    }

    public static BookMedia getSampleBook() {
        return getAddieLaRue();
    }

    public static BookMedia getAddieLaRue() {
        return new BookMedia(
                1,
                "The Invisible Life of Addie LaRue",
                """
                A Life No One Will Remember. A Story You Will Never Forget.
                France, 1714: in a moment of desperation, a young woman makes a Faustian bargain
                to live forever and is cursed to be forgotten by everyone she meets.
                """,
                2020,
                "/edu/tangingina/thebackroom/assets/for testing (delete later)/addie_larue.png",
                List.of("Fantasy", "Fiction", "Historical Fiction", "Romance"),
                List.of(
                        "https://www.goodreads.com/book/show/50623864-the-invisible-life-of-addie-larue",
                        "https://www.amazon.com/Invisible-Life-Addie-LaRue/dp/0765387565"
                ),
                "9781789098754",
                541,
                "First Titan Edition (October 2020)",
                List.of("V.E. Schwab")
        );
    }

    public static BookMedia getNoLongerHuman() {
        return new BookMedia(
                2,
                "No Longer Human",
                """
                A deeply personal and unsettling novel following Yozo Oba, a young man who feels alienated from society and unable to reveal his true self to others. Through a series of confessional reflections, the story explores shame, loneliness, performance, and the struggle to appear human in a world that feels distant and unreachable.
                """,
                1948,
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/no_longer_human.png",
                List.of("Fiction", "Classics", "Japanese Literature", "Psychological Fiction"),
                List.of(
                        "https://www.goodreads.com/book/show/194746.No_Longer_Human",
                        "https://www.amazon.com/No-Longer-Human-Osamu-Dazai/dp/0811204812"
                ),
                "9780811204811",
                177,
                "New Directions Edition",
                List.of("Osamu Dazai")
        );
    }

    public static BookMedia getLordOfTheFlies() {
        return new BookMedia(
                3,
                "Lord of the Flies",
                """
                After a group of schoolboys are stranded on an uninhabited island, their attempt to govern themselves slowly collapses into fear, rivalry, and violence. What begins as a survival story becomes a dark examination of human nature, social order, and the fragile line between civilization and savagery.
                """,
                1954,
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/lord_of_the_flies.png",
                List.of("Classics", "Fiction", "Dystopian", "Allegory"),
                List.of(
                        "https://www.goodreads.com/book/show/7624.Lord_of_the_Flies",
                        "https://www.amazon.com/Lord-Flies-William-Golding/dp/0399501487"
                ),
                "9780399501487",
                224,
                "Penguin Books Edition",
                List.of("William Golding")
        );
    }

    public static BookMedia getDracula() {
        return new BookMedia(
                4,
                "Dracula",
                """
                Told through letters, journal entries, and newspaper clippings, Dracula follows the terrifying arrival of Count Dracula in England and the group of people determined to stop him. The novel blends gothic horror, mystery, superstition, and Victorian anxieties into one of the most influential vampire stories ever written.
                """,
                1897,
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/dracula.png",
                List.of("Classics", "Horror", "Gothic", "Fantasy"),
                List.of(
                        "https://www.goodreads.com/book/show/17245.Dracula",
                        "https://www.amazon.com/Dracula-Bram-Stoker/dp/0486411095"
                ),
                "9780486411095",
                418,
                "Dover Thrift Edition",
                List.of("Bram Stoker")
        );
    }

    public static FilmMedia getTheSilentPatient() {
        return new FilmMedia(
                5,
                "The Silent Patient",
                """
                Alicia Berenson's life appears perfect until she is accused of murdering her husband and then refuses to speak another word. A criminal psychotherapist becomes determined to uncover the truth behind her silence, leading him into a psychological mystery filled with obsession, trauma, and hidden motives.
                """,
                2019,
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/patient.png",
                List.of("Thriller", "Mystery", "Psychological Fiction", "Crime"),
                List.of(
                        "https://www.goodreads.com/book/show/40097951-the-silent-patient",
                        "https://www.amazon.com/Silent-Patient-Alex-Michaelides/dp/1250301696"
                ),
                "9781250301697",
                "English",
                List.of("Alex Michaelides")

        );
    }

    public static FilmMedia getTheSilenceofTheLambs() {
        return new FilmMedia(
                6,
                "The Silent Patient",
                """
                Alicia Berenson's life appears perfect until she is accused of murdering her husband and then refuses to speak another word. A criminal psychotherapist becomes determined to uncover the truth behind her silence, leading him into a psychological mystery filled with obsession, trauma, and hidden motives.
                """,
                2019,
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/patient.png",
                List.of("Thriller", "Mystery", "Psychological Fiction", "Crime"),
                List.of(
                        "https://www.goodreads.com/book/show/40097951-the-silent-patient",
                        "https://www.amazon.com/Silent-Patient-Alex-Michaelides/dp/1250301696"
                ),
                "9781250301697",
                "English",
                List.of("Alex Michaelides")

        );
    }
}