package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.model.Media;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.util.function.Consumer;

public class DashboardHomeView extends BaseView{
    /*
        Responsible for showing dashboard content of the home page
     */

    ImageView titleLogo, logo;
    MediaSection books, games, films;
    private Consumer<Integer> mediaSelected; // New field

    public DashboardHomeView(Consumer<Integer> mediaSelected){
        //dont merge this part na
        this.mediaSelected = mediaSelected;
        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.getStyleClass().add("dashboard-home");
        root.setAlignment(Pos.TOP_CENTER);

        titleLogo = createTitleLogo();

        //media cards
        //book section

        //below this dont need to merge
        books = new MediaSection("Books", mediaSelected);
        for(Integer id : TheBackroom.bookMedia){
            Media m = TheBackroom.mediaList.get(id);
            books.addCard(m.getID().toString(), m.getMediaIcon());
        }

        games = new MediaSection("Games", mediaSelected);
        for(Integer id : TheBackroom.gameMedia){
            Media m = TheBackroom.mediaList.get(id);
            games.addCard(m.getID().toString(), m.getMediaIcon());
        }
        films = new MediaSection("Films and TV Shows", mediaSelected);
        for(Integer id : TheBackroom.videoMedia){
            Media m = TheBackroom.mediaList.get(id);
            films.addCard(m.getID().toString(), m.getMediaIcon());
        }

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
