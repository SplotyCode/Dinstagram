package team.gutterteam123.netlib.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;
import team.gutterteam123.netlib.packetbase.json.JsonPacketDecoder;
import team.gutterteam123.netlib.packetbase.json.JsonPacketEncoder;

public class ProxyBalancer extends NetServer {

    public ProxyBalancer(int port) {
        super(port);
    }

    @Override
    protected void onClose(ChannelFuture future) {

    }

    @Override
    protected void onStart(ChannelFuture future) {

    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new JsonPacketEncoder(Registrys.getInstance().getBalancerIn()));
        pipeline.addLast(new JsonPacketDecoder(Registrys.getInstance().getBalancerOut()));
        pipeline.addLast(new BalancerHandler());
    }

    public class BalancerHandler extends SimpleChannelInboundHandler<JsonPacket> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override protected void channelRead0(ChannelHandlerContext channelHandlerContext, JsonPacket jsonPacket) throws Exception {}

    }

    @Override
    protected String getDisplayName() {
        return "Proxy Balancer";
    }
}
