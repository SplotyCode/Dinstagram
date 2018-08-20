package team.gutterteam123.starter;

import java.io.File;
import java.io.IOException;

public class Starter {

    Process master;

    public static void main(String[] args){
        new Starter(args);
    }

    private Starter(String[] args) {
        ProcessBuilder builder = new ProcessBuilder("git", "clone", "https://github.com/SplotyCode/Dinstagram.git");
        File directory = new File(System.getProperty("user.home") + "/Dinstagram/repo/");
        directory.mkdirs();
        builder.directory(directory);
        builder.inheritIO();

        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
