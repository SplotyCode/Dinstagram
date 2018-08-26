package team.gutterteam123.master.sync;

import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.Future;
import team.gutterteam123.baselib.PortConstants;
import team.gutterteam123.netlib.NetClient;

import java.net.InetSocketAddress;

public class SyncClient extends NetClient {

    public SyncClient(String address) {
        super(new InetSocketAddress(address, PortConstants.getMASTER_SYNC()));
    }

    @Override
    protected void onClose(Future future) {

    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {

    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
