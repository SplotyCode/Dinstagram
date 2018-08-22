package team.gutterteam123.starter.process;

import team.gutterteam123.baselib.OperatingSystem;
import team.gutterteam123.baselib.util.ThreadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ProcessThread extends Thread {

    private Process process;

    @Override
    public void run() {
        try {
            startProcess();
            process.waitFor();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(getDisplayName() + " Process Down! Exiting with: " + process.exitValue() + " Restarting in 10s...");
        ThreadUtil.sleep(10 * 1000);
        run();
    }

    protected abstract String getDisplayName();
    protected abstract void startProcess() throws IOException;

    protected void executeProccess(File directory, String... commands) throws IOException {
        List<String> cmds = new ArrayList<>();
        if (OperatingSystem.current() == OperatingSystem.WINDOWS)
            cmds.add("cmd.exe");
        cmds.addAll(Arrays.asList(commands));
        process = new ProcessBuilder(commands).
                inheritIO().
                directory(directory).
                start();
    }
}
