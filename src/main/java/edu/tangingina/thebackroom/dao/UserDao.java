package edu.tangingina.thebackroom.dao;

import edu.tangingina.thebackroom.model.Users;

public interface UserDao {
    void login (String username, String pass) throws Exception;
    void signUp(String username, String pass) throws Exception;
    void getUser(String username) throws Exception;
    void guestLogin () throws Exception;
}
