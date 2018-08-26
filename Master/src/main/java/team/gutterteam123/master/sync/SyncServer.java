package team.gutterteam123.master.sync;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import team.gutterteam123.baselib.PortConstants;
import team.gutterteam123.netlib.NetServer;

public class SyncServer extends NetServer {

    public SyncServer() {
        super(PortConstants.getMASTER_SYNC());
    }

    @Override protected void close(ChannelFuture future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {

    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
