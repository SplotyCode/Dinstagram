package team.gutterteam123.netlib;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import team.gutterteam123.netlib.packetbase.Packet;

import java.net.InetAddress;

public abstract class NetClient<P extends Packet> {
    
    protected InetAddress address;

    private EventLoopGroup workerGroup;
    private Channel channel;

    private Runnable close;

    protected abstract void start(ChannelHandlerContext context);


}
