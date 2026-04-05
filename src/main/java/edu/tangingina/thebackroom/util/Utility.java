package edu.tangingina.thebackroom.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Utility {
    Argon2 argon2 = Argon2Factory.create();

    //we are using Argon2 hashing algorithm, on storing the passwords on our user table
    //though its excessive for our project, but why not din dba????hahahhahaha

    public String getHashPass(String password){
        char[] pass = password.toCharArray();
        String hash = "";

        try{
            //this mean, the algorihtm would take 10 iterations, use 64 MB to calulcate, and 1 this one I dont know  yet...
            hash = argon2.hash(10, 65536, 1, pass);
        }finally{
            //it is good to wipe the sotred array, pra hindi  say traceable in memory.....
            argon2.wipeArray(pass);
        }
        return hash;
    }

    public boolean verifyPass(String dbPass, String inputPass){
        char[] userPass = inputPass.toCharArray();
        boolean logIn = false;

        try{
            logIn = argon2.verify(dbPass, userPass);
        }finally{
            //it is good to wipe the sotred array, pra hindi  say traceable in memory.....
            argon2.wipeArray(userPass);
        }
        return logIn;
    }

    public void saveCredentials(String username){
        try{
            File file = new File("app.env");
            FileWriter fw = new FileWriter(file);
            fw.write(username);
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeCredentials(){
        try{
            File file = new File("app.env");
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String checkAppEnv(){
        String username = null;
        try{
            File file = new File("app.env");
            if(file.exists()){
                Scanner reader  = new Scanner(file);
                if(reader.hasNextLine()){
                    username = reader.nextLine();
                }
                reader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }



}
