package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Website;

import java.util.HashMap;

public interface WebsiteDao {
    Integer addWesbite(Website website);
    HashMap<String, Website> getAllWebsite();
    void getWebsiteCategory();
}
