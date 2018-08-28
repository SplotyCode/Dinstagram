package team.gutterteam123.netlib.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.handler.ContentHandler;
import team.gutterteam123.netlib.impl.proxyserver.ProxyOutgoingServer;
import team.gutterteam123.netlib.packetbase.json.JsonPacketDecoder;
import team.gutterteam123.netlib.packetbase.json.JsonPacketEncoder;

public class ContentServer extends NetServer {

    public ContentServer(int port) {
        super(port);
    }

    @Override protected void onClose(ChannelFuture future) {}

    @Override protected void onStart(ChannelFuture future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new JsonPacketEncoder(Registrys.getInstance().getContentIn()));
        pipeline.addLast(new JsonPacketDecoder(Registrys.getInstance().getContentOut()));
        pipeline.addLast(new ContentHandler());
    }

    @Override
    protected String getDisplayName() {
        return "Content Server";
    }
}
