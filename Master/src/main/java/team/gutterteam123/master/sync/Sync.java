package team.gutterteam123.master.sync;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.FileConstants;
import team.gutterteam123.baselib.PortConstants;
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

    private SyncClient client;
    private SyncServer server;

    public Sync() {
        try {
            JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
            JSONArray array = json.getJSONArray("country");
            for (int i = 0; i < array.length(); i++) {
                JSONObject country = array.getJSONObject(i);
                if (country.getString("name").equals(Master.getInstance().servergroup)) {
                    JSONArray roots = country.getJSONArray("roots");
                    for (int j = 0; j < roots.length(); j++) {
                        addresses.add(roots.getString(j));
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error("Could not Parse Json Config!", ex);
        }

    }

    private String getBestRoot() {
        addresses.sort(Comparator.comparingInt(String::hashCode));
        for (String root : addresses) {
            if (isOnline(root)) {
                return root;
            }
        }
        return null;
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
