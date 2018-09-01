package team.gutterteam123.master.tasks;

import team.gutterteam123.baselib.constants.TimeConstants;
import team.gutterteam123.baselib.tasks.TimerTask;
import team.gutterteam123.master.Master;
import team.gutterteam123.netlib.packets.UpdateMasterStatus;

import java.util.concurrent.ThreadLocalRandom;

public class UpdateStatusTask extends TimerTask implements Runnable {

    public UpdateStatusTask() {
        super(true, null, getTimerDelay(), TimeConstants.getUPLOAD_STATUS_DELAY());
        runnable = this;
    }

    private static long getTimerDelay() {
        return ThreadLocalRandom.current().nextLong(TimeConstants.getMIN_STATUS_UPDATE_INTERVAL(),
                TimeConstants.getMAX_STATUS_UPDATE_INTERVAL());
    }

    @Override
    public void run() {
        UpdateMasterStatus update = new UpdateMasterStatus();
        //TODO fill packet
        Master.getInstance().getClient().sendPacket(update);
    }
}
