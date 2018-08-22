package team.gutterteam123.netlib.packetbase;

public class UnsupportedPacketException extends RuntimeException {

    public UnsupportedPacketException() {
    }

    public UnsupportedPacketException(String s) {
        super(s);
    }

    public UnsupportedPacketException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnsupportedPacketException(Throwable throwable) {
        super(throwable);
    }

    public UnsupportedPacketException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
