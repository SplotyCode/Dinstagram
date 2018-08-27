package team.gutterteam123.baselib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.cache.SimpleCache;
import team.gutterteam123.baselib.constants.TimeConstants;
import team.gutterteam123.baselib.constants.UrlConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;

public final class NetUtil {

    private static Logger logger = LoggerFactory.getLogger(NetUtil.class);

    private static SimpleCache<String> remoteIp = new SimpleCache<>(TimeConstants.getREMOTE_IP_CACHE(), NetUtil::loadRemoteIp);
    private static SimpleCache<String> localIp = new SimpleCache<>(TimeConstants.getLOCAL_IP_CACHE(), NetUtil::loadLocalIp);

    private static String loadLocalIp() {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (IOException ex) {
            logger.error("Failed to get Local Ip Address", ex);
        }
        return null;
    }

    private static String loadRemoteIp() {
        try {
            return loadRemoteFromSite(UrlConstants.getIP_CHECKER_PREFERRED());
        } catch (IOException ex) {
            logger.warn("Failed to get Remote Ip from Preferred URL: " + UrlConstants.getIP_CHECKER_PREFERRED(), ex);
            for (String url : UrlConstants.getIP_CHECKERS_OTHERS()) {
                try {
                    return loadRemoteFromSite(url);
                } catch (IOException ex2) {
                    logger.warn("Failed to get Remote Ip from URL: " + url);
                }
            }
        }
        return "127.0.0.1";
    }

    private static String loadRemoteFromSite(String url) throws IOException {
        URL website = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
        return in.readLine();
    }

    public static String getRemoteIp() {
        return remoteIp.get();
    }

    public static String getLocalIp() {
        return localIp.get();
    }

}
