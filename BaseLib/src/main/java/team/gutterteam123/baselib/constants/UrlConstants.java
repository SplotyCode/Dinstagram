package team.gutterteam123.baselib.constants;

import lombok.Getter;

public final class UrlConstants {

    @Getter private static String IP_CHECKER_PREFERRED = "http://checkip.amazonaws.com/";

    @Getter private static String[] IP_CHECKERS_OTHERS = new String[]{
            "http://icanhazip.com/",
            "http://www.trackip.net/ip",
            "http://myexternalip.com/raw",
            "http://ipecho.net/plain",
            "http://bot.whatismyipaddress.com",
            "https://ident.me",
            "http://curlmyip.com/" /* Seems to be Offline */
    };
}
