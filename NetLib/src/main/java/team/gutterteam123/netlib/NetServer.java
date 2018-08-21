package team.gutterteam123.netlib;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sun.misc.ThreadGroupUtils;
import team.gutterteam123.baselib.util.ThreadUtil;
import team.gutterteam123.netlib.packetbase.Packet;

import java.util.InvalidPropertiesFormatException;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public abstract class NetServer<P extends Packet> {

    private EventLoopGroup bossGroup, workerGroup;
    private Channel channel;

    protected abstract void close(ChannelFuture future);
    protected abstract void onChannelCreation(ChannelPipeline pipeline);
    protected abstract String getName();

    protected int port;
    protected boolean epoll;

    public NetServer(int port) {
        this(port, Epoll.isAvailable());
    }

    public NetServer(int port, boolean epoll) {
        this.port = port;
        this.epoll = epoll;
    }

    @Getter @Setter protected boolean keepAlive = true;
    @Getter @Setter protected boolean autoReconnect = true;

    public void startServer() {
        bossGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        workerGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        onChannelCreation(pipeline);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);
        channel = bootstrap.bind(port).channel();
        ChannelFuture future = channel.closeFuture().addListener((ChannelFutureListener) (channelFuture) -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });
        future.addListener((ChannelFutureListener) this::close);

        if (autoReconnect) {
            System.out.println(getName() + " Server is down! Restarting in 500ms");
            ThreadUtil.sleep(500);
            startServer();
        } else {
            System.out.println(getName() + " Server is down! No Reconnecting!");
        }
    }
}
