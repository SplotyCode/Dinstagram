package team.gutterteam123.baselib.objects;

public enum OperatingSystem {

    WINDOWS,
    LINUX;

    public static OperatingSystem current() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return WINDOWS;
        return LINUX;
    }

}
