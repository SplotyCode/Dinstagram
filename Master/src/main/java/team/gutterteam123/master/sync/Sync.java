package team.gutterteam123.master.sync;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import team.gutterteam123.Master;
import team.gutterteam123.baselib.FileConstants;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class Sync {

    private Set<String> addresses = new HashSet<>();

    public Sync() {
        try {
            JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
            JSONArray array = json.getJSONArray("country");
            for (int i = 0; i < array.length(); i++) {
                JSONObject country = array.getJSONObject(i);
                if (country.getString("name").equals(Master.getInstance().servergroup)) {

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
