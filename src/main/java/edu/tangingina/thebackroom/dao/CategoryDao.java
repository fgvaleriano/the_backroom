package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Category;

import java.util.ArrayList;
import java.util.HashMap;

public interface CategoryDao {
    Integer addCategory(Category category);
    HashMap<String, Category> getAllCategory();
    void getMediaCategory();
}
