package team.gutterteam123.netlib.handler;

public class HandlerLoadException extends RuntimeException {

    public HandlerLoadException() {
    }

    public HandlerLoadException(String s) {
        super(s);
    }

    public HandlerLoadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HandlerLoadException(Throwable throwable) {
        super(throwable);
    }

    public HandlerLoadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
