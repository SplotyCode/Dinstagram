package team.gutterteam123.master.tasks;

import team.gutterteam123.baselib.constants.TimeConstants;
import team.gutterteam123.baselib.tasks.TimerTask;
import team.gutterteam123.master.Master;
import team.gutterteam123.netlib.packets.UpdateAllMasters;

import java.util.concurrent.ThreadLocalRandom;

public class UpdateAllStatusTask extends TimerTask implements Runnable {

    public UpdateAllStatusTask() {
        super(true, null, getTimerDelay(), TimeConstants.getUPDATE_ALL_STATUS_DELAY());
        runnable = this;
    }

    private static long getTimerDelay() {
        return ThreadLocalRandom.current().nextLong(TimeConstants.getMIN_STATUS_UPDATE_ALL_INTERVAL(),
                TimeConstants.getMAX_STATUS_UPDATE_ALL_INTERVAL());
    }

    @Override
    public void run() {
        UpdateAllMasters packet = new UpdateAllMasters(Master.getInstance().getRootStats(), Master.getInstance().getContentStats(), Master.getInstance().getProxyStats());
        //TODO send packet to clients
    }
}
