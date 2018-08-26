package team.gutterteam123.configserver;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.configserver.server.ConfigServer;

public class Main {

    private ConfigServer server = new ConfigServer(PortConstants.getCONFIG_DOWNLOAD());

    private Main() {
        Thread.currentThread().setName("ConfigServer - Main Thread");

        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Config Server close thread"));

        server.start();
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
