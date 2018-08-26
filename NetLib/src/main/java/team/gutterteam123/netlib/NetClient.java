package team.gutterteam123.netlib;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.util.ThreadUtil;
import team.gutterteam123.netlib.packetbase.Packet;

import java.net.InetSocketAddress;

public abstract class NetClient<P extends Packet> extends Thread {
    
    protected InetSocketAddress address;
    protected boolean epoll;

    Logger logger = LoggerFactory.getLogger(getClass());


    public NetClient(InetSocketAddress address, boolean epoll) {
        this.address = address;
        this.epoll = epoll;
    }

    public NetClient(InetSocketAddress address) {
        this(address, Epoll.isAvailable());
    }

    private EventLoopGroup workerGroup;
    private Channel channel;

    @Getter @Setter protected boolean keepAlive = true;
    @Getter @Setter protected boolean autoReconnect = true;
    @Getter @Setter protected float reconnectMulti = 1.5f;
    @Getter @Setter protected long reconnectMax = 30 * 1000;
    @Getter @Setter protected long reconnectStart = 5 * 1000;

    private long currentReconnect = -1;

    protected abstract void onClose(Future future);
    protected abstract void onChannelCreation(ChannelPipeline pipeline);
    protected abstract String getDisplayName();

    @Override
    public void run() {
        workerGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(epoll ? EpollSocketChannel.class : NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, keepAlive)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            onChannelCreation(pipeline);
                        }
                    });

            channel = bootstrap.connect(address).sync().channel();
            ChannelFuture f = channel.closeFuture();
            f.addListener(future -> workerGroup.shutdownGracefully());
            f.addListener(this::onClose);
            f.sync();

        } catch (Exception ex) {
            logger.error("Exception in Client", ex);
        }

        if (autoReconnect) {
            if (currentReconnect == -1) {
                currentReconnect = reconnectStart;
            } else {
                currentReconnect *= reconnectMulti;
            }
            long reconnectDelay = Math.max(reconnectMax, currentReconnect);
            logger.info(getDisplayName() + " Client is down! Reconnecting in {}secs!", reconnectDelay / 1000);
            ThreadUtil.sleep(reconnectDelay);
            run();
        }
    }



}
