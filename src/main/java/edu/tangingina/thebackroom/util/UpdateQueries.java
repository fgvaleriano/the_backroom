package edu.tangingina.thebackroom.util;

public class UpdateQueries {
    /*
        Temporary only, final working queries will be transfered to
        FileManager

        purpose:
            - make it easier to see queries while output is not yet final
     */

    public static final String fetch_person_by_role = "select p.name from person p " +
            "join media_personnel mp on p.person_id = mp.person_id join role r on mp.role_id = r.role_id " +
            "where mp.media_id = ? and r.name = ?";

    public static final String fetch_company_by_role = "select c.name from company c " +
            "join media_company mc on c.company_id = mc.company_id join role r on mc.role_id = r.role_id " +
            "where mc.media_id = ? and r.name = ?";

    public static final String fetch_category = "select c.name from category c " +
            "join media_category mc on c.category_id = mc.category_id where mc.media_id = ?";

    public static final String fetch_game_mode = "";
}
