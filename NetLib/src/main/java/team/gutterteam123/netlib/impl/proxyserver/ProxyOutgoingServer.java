package team.gutterteam123.netlib.impl.proxyserver;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import team.gutterteam123.baselib.constants.CharsetConstants;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.handler.ConnectionCounter;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;

public class ProxyOutgoingServer extends NetServer<JsonPacket> {

    private ProxyServer server;
    private ConnectionCounter counter;

    public ProxyOutgoingServer(int port, ProxyServer server) {
        super(port);
        this.server = server;
    }

    @Override protected void onClose(ChannelFuture future) {}

    @Override protected void onStart(ChannelFuture future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(counter);
        pipeline.addLast(new StringEncoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new StringDecoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new ProxyHandler());
    }

    public int getCount() {
        return counter.getCount();
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
