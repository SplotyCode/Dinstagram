package team.gutterteam123.netlib.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;

public class ProxyBalancer extends NetServer {

    public ProxyBalancer(int port) {
        super(port);
    }

    @Override
    protected void close(ChannelFuture future) {

    }

    @Override
    protected void onStart(ChannelFuture future) {

    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {

    }

    public class BalancerHandler extends SimpleChannelInboundHandler<SerializedPacket> {

    }

    @Override
    protected String getDisplayName() {
        return "Proxy Balancer";
    }
}
