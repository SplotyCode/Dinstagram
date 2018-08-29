package team.gutterteam123.master;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;
import team.gutterteam123.baselib.config.ConfigHelper;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.linked.MasterToBaseLinked;
import team.gutterteam123.database.DatabaseConnection;
import team.gutterteam123.master.config.Config;
import team.gutterteam123.master.sync.Sync;
import team.gutterteam123.netlib.linked.MasterToNetLibLinked;

import java.nio.charset.Charset;

public class Master {

    @Getter private static Master instance;

    private static final Logger logger = LoggerFactory.getLogger(Master.class);

    public static void main(String[] args) {
        try {
            new Master(args);
        } catch (Exception ex) {
            logger.error("Failed to Start Master", ex);
        }
    }

    @Parameter(name = "servergroup", needed = true)
    public String serverGroup;

    @Getter private DatabaseConnection db;

    @Getter private JSONObject rawConfig;
    @Getter private Config config;
    @Getter private ConfigHelper configHelper;

    @Getter private Sync sync;


    private Master(String[] args) throws Exception {
        Thread.currentThread().setName("Master - Main Thread");
        instance = this;
        setupLibLinks();

        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

        new ArgumentBuilder().setObject(this).setInput(args).build();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Master Stopping Thread"));

        rawConfig = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
        config = new Config(rawConfig);
        configHelper = new ConfigHelper(serverGroup);

        sync = new Sync();

        db = new DatabaseConnection(rawConfig.getString("mongo"));
    }

    private void setupLibLinks() {
        MasterToNetLibLinked.getInstance().setRoots(() -> config.getRoots());
        MasterToBaseLinked.getInstance().setServerGroup(() -> serverGroup);
    }

    private void stop() {
        if (sync != null) {
            if (sync.getClient() != null) {
                sync.getClient().shutdown();
            }
            if (sync.getServer() != null) {
                sync.getServer().shutdown();
            }
        }
    }

}
