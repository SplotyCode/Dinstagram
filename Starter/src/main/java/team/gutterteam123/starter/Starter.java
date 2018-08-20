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
        try {
            Runtime.getRuntime().exec("cd " + System.getProperty("user.home") + "/Dinstagram/repo/ & git clone https://github.com/SplotyCode/Dinstagram.git");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File directory = new File(System.getProperty("user.home") + "/Dinstagram/repo/");
        directory.mkdirs();
        builder.directory(directory);
        builder.inheritIO();
        System.out.println(System.getProperty("os.name"));

        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
