package team.gutterteam123.configserver;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.configserver.server.ConfigServer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private ConfigServer server = new ConfigServer(PortConstants.getCONFIG_DOWNLOAD());

    @Getter private Set<String> roots = new HashSet<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter private static Main instance;

    private Main() {
        instance = this;
        Thread.currentThread().setName("ConfigServer - Main Thread");

        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Config Server close thread"));

        loadRoots();
        server.start();
    }

    private void loadRoots() {
        try {
            JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG_SERVER(), Charset.forName("Utf-8")));
            JSONArray rootArray = json.getJSONArray("roots");
            for (int i = 0; i < rootArray.length(); i++) {
                roots.add(rootArray.getString(i));
            }
        } catch (IOException ex) {
            logger.error("Failed Reading Config", ex);
        }

    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
