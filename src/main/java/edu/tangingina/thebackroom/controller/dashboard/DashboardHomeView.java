package edu.tangingina.thebackroom.controller.dashboard;

import javafx.geometry.*;
import javafx.scene.image.*;

public class DashboardHomeView extends BaseView{
    /*
        Responsible for showing dashboard content of the home page
     */

    ImageView titleLogo, logo;
    MediaSection books, games, films;

    @Override
    protected void buildLayout() {
        root.getStyleClass().add("dashboard-home");
        root.setAlignment(Pos.TOP_CENTER);

        titleLogo = createTitleLogo();

        //media cards
        //book section
        books = new MediaSection("Books");
        books.addCard("The Invisible Life of Addie LaRue",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/genshin.png");
        books.addCard("The Silent Patient",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/patient.png");
        books.addCard("The Invisible Life of Addie LaRue",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/addie.png");
        books.addCard("The Silent Patient",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/patient.png");
        books.addCard("The Invisible Life of Addie LaRue",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/addie.png");
        books.addCard("The Silent Patient",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/patient.png");
        books.addCard("The Invisible Life of Addie LaRue",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/addie.png");

        games = new MediaSection("Games");
        games.addCard("Genshin Impact",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/genshin.png");
        games.addCard("Sims 4",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/sims.png");
        games.addCard("Genshin Impact",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/genshin.png");
        games.addCard("Sims 4",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/sims.png");
        games.addCard("Genshin Impact",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/genshin.png");
        games.addCard("Sims 4",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/sims.png");

        films = new MediaSection("Films and TV Shows");
        films.addCard("The Imitation Game",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/imitation.png");
        films.addCard("Barbie",
                "/edu/tangingina/thebackroom/assets/for testing (delete before submission)/barbie.png");

        root.getChildren().addAll(titleLogo, books, games, films);
    }

    //creates the logo found at the top of the dashboard
    private ImageView createTitleLogo(){
        Image img = new Image(getClass().getResource(
                "/edu/tangingina/thebackroom/assets/title.png").toExternalForm());

        logo = new ImageView(img);
        logo.setFitWidth(460);
        logo.setPreserveRatio(true);
        logo.getStyleClass().add("big-logo");

        return logo;
    }
}
