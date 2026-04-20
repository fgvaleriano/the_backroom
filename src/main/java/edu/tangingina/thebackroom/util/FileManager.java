package edu.tangingina.thebackroom.util;

import com.google.gson.Gson;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public File openFile(Stage stage, String description, String fileType){
        FileChooser selectFile = new FileChooser();

        //this would make only csv files to be opened... (filterer)
        selectFile.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(description, fileType)
        );

        File file = selectFile.showOpenDialog(stage);
        return file;

    }

    public void importCSV(Stage stage){

        File file = openFile(stage, "CSV Files (*.csv)", "*.csv");

        if(file != null){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String currLine;

                //Example this would read a person info, so (name, age, gender, sexuality)
                while((currLine = br.readLine()) != null){
                    String [] line = currLine.split(",");
                    /*
                    String name = line[0];
                    String age = line [1];
                    String gender = line[2];
                    String sexuality =  line[3];

                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Gender: " + gender);
                    System.out.println("Sexuality: " + sexuality);
                    System.out.println();
                     */
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void importJSON(Stage stage){
        Gson gson = new Gson();
        File file = openFile(stage, "JSON Files (*.json)", "*.json");

        if(file != null){
            try(FileReader read = new FileReader(file)){
                /*Person[] person = gson.fromJson(read, Person[].class); //from the JSON file it is turned into a class

                for(int i = 0; i < person.length; i++){
                    Person temp = person[i];

                    String name = temp.name;
                    int age = temp.age;
                    String gender = temp.gender;
                    String sexuality = temp.sexuality;

                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Gender: " + gender);
                    System.out.println("Sexuality: " + sexuality);
                    System.out.println();

                }*/

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //this is importing IMG by user
    public File importImg(Stage stage){
        FileChooser choose = new FileChooser();

        choose.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG/PNG Files", "*.jpg", "*.jpeg", "*.png")
        );

        File file = choose.showOpenDialog(stage);

        return file;
    }

    public String saveIMGRelative(File file){
        //First we create the directory on our project folder
        File dir = new File("uploads/mediaIcon");

        //if it doesnt exist yeah we create the directory
        if(!dir.exists()){
            dir.mkdirs();
        }

        //Here we get the file extension pra kuan for the naming
        int dotIndex = file.getName().indexOf(".");
        String imgExtension = "";
        if(dotIndex > 0){
            imgExtension = file.getName().substring(dotIndex);
        }

        String newFileName = System.currentTimeMillis() + imgExtension;

        File newFile = new File(dir, file.getName());
        try{
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){

        }

        return "edu/tangingina/thebackroom/mediaIcon/" + newFileName;
    }


}
