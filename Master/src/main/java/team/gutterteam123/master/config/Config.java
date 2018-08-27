package team.gutterteam123.master.config;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Config {

    @Getter private Set<String> roots = new HashSet<>();

    public Config(JSONObject json) {
        JSONArray rootArray = json.getJSONArray("roots");
        for (int i = 0; i < rootArray.length(); i++) {
            roots.add(rootArray.getString(i));
        }
    }

}
