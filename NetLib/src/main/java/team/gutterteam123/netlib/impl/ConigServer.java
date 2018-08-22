package team.gutterteam123.netlib.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packetbase.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.SerializedPacketEncoder;

public class ConigServer extends NetServer<SerializedPacket> {

    @Override protected void close(ChannelFuture future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getConfigOut()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getConfigIn()));
    }

    @Override
    protected String getDisplayName() {
        return "Config";
    }
}
