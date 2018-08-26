package team.gutterteam123.baselib.constants;

import lombok.Getter;

import java.io.File;

public final class FileConstants {

    @Getter private static final File DINSTAGRAM = new File(System.getProperty("user.home"), "Dinstagram/");
    @Getter private static final File REPO = new File(DINSTAGRAM, "repo/");

    @Getter private static final File CONFIG = new File(DINSTAGRAM, "config.json");
    @Getter private static final File CONFIG_SERVER = new File(DINSTAGRAM, "configserver/config.json");

    @Getter private static final File LOG = new File(DINSTAGRAM, "logs/");
    @Getter private static final File MAVEN_LOG = new File(LOG, "mavenbuild.txt");

}
