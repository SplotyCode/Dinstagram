package team.gutterteam123.baselib;

public enum OperatingSystem {

    WINDOWS,
    LINUX;

    public static OperatingSystem current() {
        String os = System.getProperty("os.name");
        if (os.contains("win")) return WINDOWS;
        return LINUX;
    }

}
