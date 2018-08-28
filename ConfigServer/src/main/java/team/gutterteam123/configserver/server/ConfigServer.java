package team.gutterteam123.configserver.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketEncoder;

public class ConfigServer extends NetServer<SerializedPacket> {

    public ConfigServer(int port) {
        super(port);
    }

    public ConfigServer(int port, boolean epoll) {
        super(port, epoll);
    }

    @Override protected void onClose(ChannelFuture future) {}

    @Override
    protected void onStart(ChannelFuture future) {
        if (FileConstants.getCONFIG_SERVER_LOCK().exists()) {
            FileConstants.getCONFIG_SERVER_LOCK().delete();
        }
    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getConfigOut()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getConfigIn()));
        pipeline.addLast(new ServerConfigHandler());
    }

    @Override
    protected String getDisplayName() {
        return "Config Server";
    }
}
