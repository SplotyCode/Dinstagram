package team.gutterteam123.configserver;

import org.apache.log4j.BasicConfigurator;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.configserver.server.ConfigServer;

public class Main {

    private ConfigServer server = new ConfigServer(PortConstants.getCONFIG_DOWNLOAD());

    private Main() {
        BasicConfigurator.configure();
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
