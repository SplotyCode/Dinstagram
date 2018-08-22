package team.gutterteam123.starter.process;

import team.gutterteam123.baselib.FileConstants;

import java.io.IOException;

public class MasterProcess extends ProcessThread {
    @Override
    protected String getDisplayName() {
        return "Master";
    }

    @Override
    protected void startProcess() throws IOException {
        executeProccess(FileConstants.getDINSTAGRAM(), "java", "-jar", "Master.jar");
    }
}
