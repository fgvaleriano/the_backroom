package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Role;

import java.util.HashMap;

public interface RoleDao {
    HashMap<String, Role> getAllRole();
    void getMediaContributors();
}
