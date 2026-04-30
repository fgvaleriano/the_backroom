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

    public static final String fetch_game_mode = "select m.name from mode m join " +
            "media_game_mode mode on m.mode_id = mode.mode_id where mode.media_id = ?";

    public static final String fetch_game_platform = "select pf.name from platform pf  join media_game_platform " +
            "mgp on pf.platform_id = mgp.platform_id where mgp.media_id = ?";
}
