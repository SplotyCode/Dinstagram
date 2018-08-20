package team.gutterteam123.baselib.argparser;

public class BuilderException extends RuntimeException {

    public BuilderException() {
    }

    public BuilderException(String s) {
        super(s);
    }

    public BuilderException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BuilderException(Throwable throwable) {
        super(throwable);
    }

    public BuilderException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
