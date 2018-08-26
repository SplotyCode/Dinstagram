package team.gutterteam123.starter.process;

import team.gutterteam123.baselib.constants.FileConstants;

import java.io.IOException;

public class ConfigProcess extends ProcessThread {
    @Override
    protected String getDisplayName() {
        return "Config Server";
    }

    @Override
    protected void startProcess() throws IOException {
        executeProccess(FileConstants.getDINSTAGRAM(), "java", "-jar", "ConfigServer.jar");
    }
}
