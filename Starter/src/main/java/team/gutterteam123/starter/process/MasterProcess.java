package team.gutterteam123.starter.process;

import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.starter.Starter;

import java.io.IOException;

public class MasterProcess extends ProcessThread {
    @Override
    protected String getDisplayName() {
        return "Master";
    }

    @Override
    protected void startProcess() throws IOException {
        executeProccess(FileConstants.getDINSTAGRAM(), "java", "-jar", "-Xms" + Starter.getInstance().getConfigHelper().getMinRam(), "-Xmx" + Starter.getInstance().getConfigHelper().getMaxRam(), "Master.jar", "-servergroup", Starter.getInstance().serverGroup);
    }
}
