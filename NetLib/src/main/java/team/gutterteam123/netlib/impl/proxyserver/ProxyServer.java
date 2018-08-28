package team.gutterteam123.netlib.impl.proxyserver;

import com.google.common.collect.HashBiMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.net.SocketAddress;
import java.util.HashMap;

public class ProxyServer {

    private ProxyOutgoingServer server;

    /* Our Client Ip Address - Proxy Client */
    @Getter private HashMap<SocketAddress, ProxyInternalClient> clients = new HashMap<>();
    /* User Channel - Our Client Ip Address */
    @Getter private HashBiMap<Channel, SocketAddress> connections = HashBiMap.create();

    public ProxyServer(int port) {
        server = new ProxyOutgoingServer(port, this);
    }

    public void resisterClient(ChannelHandlerContext ctx) {

    }

}
