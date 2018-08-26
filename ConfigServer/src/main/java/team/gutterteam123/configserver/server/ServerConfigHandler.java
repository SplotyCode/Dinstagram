package team.gutterteam123.configserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.FileConstants;
import team.gutterteam123.baselib.cache.SimpleCache;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packetbase.UnsupportedPacketException;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ServerConfigHandler extends SimpleChannelInboundHandler<SerializedPacket> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private SimpleCache<String> hashCache = new SimpleCache<>(10 * 1000, this::getConfigHash);
    private SimpleCache<String> configCache = new SimpleCache<>(10 * 1000, () -> {
        try {
            return FileUtils.readFileToString(FileConstants.getCONFIG_SERVER(), Charset.forName("Utf-8"));
        } catch (IOException ex) {
            logger.error("Coult not Read From Json config File", ex);
        }
        return null;
    });

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SerializedPacket packet) throws Exception {
        if (packet instanceof ConfigRequestUpdate) {
            ConfigRequestUpdate request = (ConfigRequestUpdate) packet;
            if (request.getHash().isEmpty() || !request.getHash().equals(hashCache.get())) {
                logger.debug("Uploading config to {}", ctx.channel().remoteAddress().toString());
                ctx.channel().writeAndFlush(new ConfigUpdate(configCache.get()), ctx.voidPromise());
            }
        } else {
            throw new UnsupportedPacketException(packet.getClass().getSimpleName() + " is not supported!");
        }
    }

    private String getConfigHash() {
        try {
            if (FileConstants.getCONFIG_SERVER().exists()) {
                FileInputStream fis = new FileInputStream(FileConstants.getCONFIG_SERVER());
                String hash = DigestUtils.sha1Hex(fis);
                fis.close();
                return hash;
            }
        } catch (IOException ex) {
            logger.error("Could not generate config hash", ex);
        }
        return "";
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Error in Server Config Channel", cause);
    }
}
