package team.gutterteam123.netlib.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.netlib.packetbase.Packet;

import java.util.Set;

public abstract class RootAuthHandler<P extends Packet> extends SimpleChannelInboundHandler<P> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString().substring(1).split(":")[0];
        if (!getRoots().contains(address)) {
            logger.info("Kicked {}", address);
            ctx.channel().close();
        }
    }

    protected abstract Set<String> getRoots();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Exception cached in last Handler", cause);
    }
}
