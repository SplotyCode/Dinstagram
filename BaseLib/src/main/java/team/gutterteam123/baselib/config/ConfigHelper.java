package team.gutterteam123.baselib.config;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.linked.MasterToBaseLinked;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class ConfigHelper {

    private String country;

    private Logger logger = LoggerFactory.getLogger(getClass());
    private JSONObject countryRoot;
    @Getter private String maxRam, minRam;

    public ConfigHelper(String country) throws IOException {
        this.country = country;
        JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
        JSONArray array = json.getJSONArray("country");
        for (int i = 0; i < array.length(); i++) {
            JSONObject countryObj = array.getJSONObject(i);
            if (countryObj.getString("name").equals(country)) {
                countryRoot = countryObj;
            }
        }
        if (countryRoot == null) {
            logger.error("Could not find Country '" + country + "' in config File!");
        }
        minRam = countryRoot.getString("minram");
        maxRam = countryRoot.getString("maxram");
    }

    public Set<String> collectCountryRoots() {
        Set<String> set = new HashSet<>();
        try {
            JSONObject json = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
            JSONArray array = json.getJSONArray("country");
            for (int i = 0; i < array.length(); i++) {
                JSONObject country = array.getJSONObject(i);
                if (country.getString("name").equals(MasterToBaseLinked.getInstance().getServerGroup().get())) {
                    JSONArray roots = country.getJSONArray("roots");
                    for (int j = 0; j < roots.length(); j++) {
                        set.add(roots.getString(j));
                        logger.info("Added Root: " + roots.getString(j));
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error("Could not Parse Json Config!", ex);
        }
        return set;
    }
}
