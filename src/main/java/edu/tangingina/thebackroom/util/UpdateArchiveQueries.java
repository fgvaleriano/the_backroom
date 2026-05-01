package edu.tangingina.thebackroom.util;

public class UpdateArchiveQueries {

     /*
        Temporary only, final working queries will be transfered to
        FileManager

        purpose:
            - make it easier to see queries while output is not yet final
     */

    public static final String fetch_person_by_role = "select group_concat(p.name separator ', ') as all_personnel " +
            "from person p " +
            "join media_personnel mp on p.person_id = mp.person_id join role r on mp.role_id = r.role_id " +
            "where mp.media_id = ? and r.name = ?";

    public static final String fetch_company_by_role = "select group_concat(c.name separator ', ') as all_company " +
            "from company c " +
            "join media_company mc on c.company_id = mc.company_id join role r on mc.role_id = r.role_id " +
            "where mc.media_id = ? and r.name = ?";

    public static final String fetch_category = "select group_concat(c.name separator ', ') as all_category from category c " +
            "join media_category mc on c.category_id = mc.category_id where mc.media_id = ?";

    public static final String fetch_game_mode = "select group_concat(m.name separator ', ') as all_mode from mode m join " +
            "media_game_mode mode on m.mode_id = mode.mode_id where mode.media_id = ?";

    public static final String fetch_tv_show_status = "select statuc from tv_show_details where media_id = ?";

    public static final String fetch_game_platform = "select group_concat(pf.name separator ', ') as all_platform " +
            "from platform pf  join media_game_platform " +
            "mgp on pf.platform_id = mgp.platform_id where mgp.media_id = ?";

    public static final String getString = "select icon_path as path from media where media_id = ?";

    public static final String fetch_access_links = "select w.name, ma.url from website w " +
            "join media_access ma on w.website_id = ma.website_id where ma.media_id = ?";

    public static final String update_media = "update media set name = ?, synopsis = ?, release_year = ?, " +
            "icon path = ? where media_id = ?";

    public static final String update_book_deets = "update book_details set isbn = ?, page_count = ?, edition = ? " +
            "where media _id = ?";

    public static final String delete_media_category = "delete from media_catgegory where media_id = ?";

    public static final String delete_media_personnel = "delete from media_personnel where media_id = ? and role = ?";

    public static final String delete_media_company = "delete from media_company where media_id = ? and role = ?";

    public static final String delete_media_access = "delete from media_access where media_id = ?";
}