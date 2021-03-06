package team.gutterteam123.baselib.constants;

import lombok.Getter;

public final class TimeConstants {

    @Getter private static final long REMOTE_IP_CACHE = /* 2 Minutes */ 1000 * 60 * 2;
    @Getter private static final long LOCAL_IP_CACHE = /* 1 Minute */ 1000 * 60;

    @Getter private static final long CONFIG_HASH_CACHE = /* 10 Seconds */ 10 * 1000;
    @Getter private static final long CONFIG_CACHE = /* 10 Seconds */ 10 * 1000;
    @Getter private static final long CONFIG_LOCK_UPDATE = /* Avery 200ms */ 200;

    @Getter private static final long SYNC_UPDATE = /* 45 Seconds */ 45 * 1000;
    @Getter private static final int SYNC_TIMEOUT = /* 3 Seconds */ 3 * 1000;

    @Getter private static final long UPLOAD_STATUS_DELAY = /* 15 Seconds */ 15 * 1000;
    @Getter private static final long MIN_STATUS_UPDATE_INTERVAL = /* 20 Seconds */ 20 * 1000;
    @Getter private static final long MAX_STATUS_UPDATE_INTERVAL = /* 45 Seconds */ 45 * 1000;

    @Getter private static final long UPDATE_ALL_STATUS_DELAY = /* 15 Seconds */ 15 * 1000;
    @Getter private static final long MIN_STATUS_UPDATE_ALL_INTERVAL = /* 25 Seconds */ 25 * 1000;
    @Getter private static final long MAX_STATUS_UPDATE_ALL_INTERVAL = /* 45 Seconds */ 45 * 1000;

}
