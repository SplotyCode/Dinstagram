package team.gutterteam123.baselib.constants;

import lombok.Getter;

public final class TimeConstants {

    @Getter private static final long REMOTE_IP_CACHE = /* 2 Minutes */ 1000 * 60 * 2;
    @Getter private static final long LOCAL_IP_CACHE = /* 1 Minute */ 1000 * 60;

    @Getter private static final long CONFIG_HASH_CACHE = /* 10 Seconds */ 10 * 1000;
    @Getter private static final long CONFIG_CACHE = /* 10 Seconds */ 10 * 1000;

    @Getter private static final long SYNC_UPDATE = /* 30 Seconds */ 30 * 1000;

}
