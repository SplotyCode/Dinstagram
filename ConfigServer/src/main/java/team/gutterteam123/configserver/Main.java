package team.gutterteam123.configserver;

import team.gutterteam123.configserver.server.ConfigServer;

public class Main {

    private static final int PORT = 30000;

    private ConfigServer server = new ConfigServer(PORT);

    private Main() {
        server.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
