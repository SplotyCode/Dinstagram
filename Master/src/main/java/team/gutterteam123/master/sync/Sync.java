package team.gutterteam123.master.sync;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.baselib.constants.TimeConstants;
import team.gutterteam123.baselib.tasks.TaskManager;
import team.gutterteam123.baselib.tasks.TimerTask;
import team.gutterteam123.baselib.util.NetUtil;
import team.gutterteam123.master.Master;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sync {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> addresses = new ArrayList<>();
    private String currentBest;

    @Setter @Getter private SyncClient client;
    @Getter private SyncServer server;

    public Sync() {
        try {
            JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
            JSONArray array = json.getJSONArray("country");
            for (int i = 0; i < array.length(); i++) {
                JSONObject country = array.getJSONObject(i);
                System.out.println("a " + country.toString());
                System.out.println("b " + country.getString("name"));
                if (country.
                        getString("name").
                        equals(Master.
                                getInstance().
                                servergroup)) {
                    JSONArray roots = country.getJSONArray("roots");
                    for (int j = 0; j < roots.length(); j++) {
                        addresses.add(roots.getString(j));
                        logger.info("Added Root " + roots.getString(j));
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error("Could not Parse Json Config!", ex);
        }
        logger.info("Loaded " + addresses.size() + " roots!");

        currentBest = getBestRoot();
        if (currentBest.equals(NetUtil.getRemoteIp())) {
            server = new SyncServer();
            server.start();
        }
        client = new SyncClient(currentBest);
        client.start();

        TaskManager.getInstance().registerTask(new TimerTask(true, () -> {
            String best = getBestRoot();
            if (!best.equals(currentBest)) {
                if (currentBest.equals(NetUtil.getRemoteIp())) {
                    server.shutdown();
                }
                if (best.equals(NetUtil.getRemoteIp())) {
                    server = new SyncServer();
                    server.start();
                }
                client = new SyncClient(best);
                client.start();
                currentBest = best;

                addresses.sort(Comparator.comparingInt(String::hashCode));
                for (String root : addresses) {
                    if (root.hashCode() > currentBest.hashCode() && isOnline(root)) {
                        new DestroyClient(root, best).start();
                    }
                }
            }
        }, TimeConstants.getSYNC_UPDATE(), TimeConstants.getSYNC_UPDATE()));
    }

    private String getBestRoot() {
        addresses.sort(Comparator.comparingInt(String::hashCode));
        for (String root : addresses) {
            if (isOnline(root)) {
                return root;
            }
        }
        return NetUtil.getRemoteIp();
    }

    private boolean isOnline(String host) {
        try {
            new Socket(host, PortConstants.getMASTER_SYNC()).close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

}
