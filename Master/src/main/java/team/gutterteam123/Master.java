package team.gutterteam123;

import lombok.Getter;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;

public class Master {

    @Getter private static Master instance;

    public static void main(String[] args) {
        instance = new Master(args);
    }

    @Parameter(name = "servergroup", needed = true)
    public String servergroup;

    private Master(String[] args) {
        new ArgumentBuilder().setObject(this).setInput(args).build();

    }

}
