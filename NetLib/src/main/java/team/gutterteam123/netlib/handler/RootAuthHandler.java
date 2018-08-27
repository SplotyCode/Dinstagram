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
        System.out.println(ctx.channel().remoteAddress().toString());
    }

    protected abstract Set<String> getRoots();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Exception catched in last Handler", cause);
    }
}
