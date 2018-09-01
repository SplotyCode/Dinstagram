package team.gutterteam123.netlib.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class ConnectionCounter extends SimpleChannelInboundHandler {

    private AtomicInteger counter = new AtomicInteger();

    public int getCount() {
        return counter.get();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        counter.incrementAndGet();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        counter.decrementAndGet();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {}
}
