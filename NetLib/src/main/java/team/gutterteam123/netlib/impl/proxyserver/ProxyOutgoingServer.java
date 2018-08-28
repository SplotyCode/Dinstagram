package team.gutterteam123.netlib.impl.proxyserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import team.gutterteam123.baselib.constants.CharsetConstants;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;
import team.gutterteam123.netlib.packetbase.json.JsonPacketDecoder;
import team.gutterteam123.netlib.packetbase.json.JsonPacketEncoder;

public class ProxyOutgoingServer extends NetServer<JsonPacket> {

    private ProxyServer server;

    public ProxyOutgoingServer(int port, ProxyServer server) {
        super(port);
        this.server = server;
    }

    @Override protected void onClose(ChannelFuture future) {}

    @Override protected void onStart(ChannelFuture future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new StringEncoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new StringDecoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new ProxyHandler());
    }

    @Override
    protected String getDisplayName() {
        return "Outgoing Proxy Server";
    }

    private class ProxyHandler extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            server.resisterClient(ctx);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String buf) throws Exception {
            server.getClients().get(ctx.channel().remoteAddress()).getChannel().write(buf);
        }

    }
}
