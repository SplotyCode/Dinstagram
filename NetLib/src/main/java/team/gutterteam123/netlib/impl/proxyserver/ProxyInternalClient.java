package team.gutterteam123.netlib.impl.proxyserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import lombok.EqualsAndHashCode;
import team.gutterteam123.baselib.constants.CharsetConstants;
import team.gutterteam123.netlib.NetClient;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;

import java.net.InetSocketAddress;

@EqualsAndHashCode(callSuper = true)
public class ProxyInternalClient extends NetClient<JsonPacket> {

    private ProxyServer server;

    public ProxyInternalClient(InetSocketAddress address, ProxyServer server) {
        super(address);
        this.server = server;
    }

    @Override protected void onClose(Future future) {}

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new StringEncoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new StringDecoder(CharsetConstants.getUTF_8()));
        pipeline.addLast(new ProxyHandler());
    }

    private class ProxyHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String buf) throws Exception {
            server.getConnections().inverse().get(address).writeAndFlush(buf);
        }

    }

    @Override
    protected String getDisplayName() {
        return "Internal Proxy Client";
    }
}
