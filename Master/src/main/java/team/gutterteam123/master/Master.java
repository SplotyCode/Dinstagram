package team.gutterteam123.master;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import team.gutterteam123.baselib.FileConstants;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;
import team.gutterteam123.database.DatabaseConnection;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

public class Master {

    @Getter private static Master instance;

    public static void main(String[] args) {
        try {
            instance = new Master(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Parameter(name = "servergroup", needed = true)
    public String servergroup;

    @Getter private DatabaseConnection db;
    @Getter private JSONObject config;


    private Master(String[] args) throws Exception {
        new ArgumentBuilder().setObject(this).setInput(args).build();

        config = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));

        db = new DatabaseConnection(config.getString("mongo"));

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            System.out.println(socket.getLocalAddress().getHostAddress());
        }

    }

}
