package team.gutterteam123.starter.process;

import team.gutterteam123.baselib.FileConstants;

import java.io.IOException;

public class ConfigProcess extends ProcessThread {
    @Override
    protected String getDisplayName() {
        return "Config Server";
    }

    @Override
    protected void startProcess() throws IOException {
        executeProccess(FileConstants.getDinstagram(), "java", "-jar", "ConfigServer.jar");
    }
}
