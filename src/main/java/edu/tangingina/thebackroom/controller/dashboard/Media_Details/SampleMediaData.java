package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.List;

public class SampleMediaData {
    public static BookMedia getSampleBook() {
        return new BookMedia(
                1,
                "The Invisible Life of Addie LaRue",
                "V.E. Schwab",
                """
                A Life No One Will Remember. A Story You Will Never Forget. France, 1714: in a moment of desperation, a young woman makes a Faustian bargain to live forever--and is cursed to be forgotten by everyone she meets. Thus begins the extraordinary life of Addie LaRue, and a dazzling adventure that will play out across centuries and continents, across history and art, as a young woman learns how far she will go to leave her mark on the world. But everything changes when, after nearly 300 years, Addie stumbles across a young man in a hidden bookstore and he remembers her name.
                """,
                2020,
                "/edu/tangingina/thebackroom/assets/addie_larue.jpg",
                List.of(
                        "https://www.goodreads.com/book/show/50623864-the-invisible-life-of-addie-larue",
                        "https://www.amazon.com/Invisible-Life-Addie-LaRue/dp/0765387565"
                ),
                List.of("Fantasy", "Fiction", "Historical Fiction", "Romance"),
                541,
                "First Titan Edition (October 2020)",
                "99781789098754"
        );
    }
}
