package team.gutterteam123.master;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.database.DatabaseConnection;
import team.gutterteam123.master.sync.Sync;

import java.nio.charset.Charset;

public class Master {

    @Getter private static Master instance;

    private static final Logger logger = LoggerFactory.getLogger(Master.class);

    public static void main(String[] args) {
        try {
            instance = new Master(args);
        } catch (Exception ex) {
            logger.error("Failed to Start Master", ex);
        }
    }

    @Parameter(name = "servergroup", needed = true)
    public String servergroup;

    @Getter private DatabaseConnection db;
    @Getter private JSONObject config;

    @Getter private Sync sync;


    private Master(String[] args) throws Exception {
        BasicConfigurator.configure();
        new ArgumentBuilder().setObject(this).setInput(args).build();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Master Stopping Thread"));
        sync = new Sync();

        config = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));

        db = new DatabaseConnection(config.getString("mongo"));
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
