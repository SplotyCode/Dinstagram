package team.gutterteam123.configserver;

import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.configserver.server.ConfigServer;

public class Main {

    private ConfigServer server = new ConfigServer(PortConstants.getCONFIG_DOWNLOAD());

    private Main() {
        server.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
