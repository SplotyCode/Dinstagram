package team.gutterteam123.baselib;

import lombok.Getter;

import java.io.File;

public final class FileConstants {

    @Getter private static final File dinstagram = new File(System.getProperty("user.home"), "Dinstagram/");
    @Getter private static final File repo = new File(dinstagram, "repo/");

    @Getter private static final File config = new File(dinstagram, "config.json");
    @Getter private static final File configServer = new File(dinstagram, "configserver/config.json");

}
