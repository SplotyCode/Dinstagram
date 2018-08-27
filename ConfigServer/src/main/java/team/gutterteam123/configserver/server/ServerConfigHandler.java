package team.gutterteam123.configserver.server;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.cache.SimpleCache;
import team.gutterteam123.baselib.constants.TimeConstants;
import team.gutterteam123.configserver.Main;
import team.gutterteam123.netlib.handler.RootAuthHandler;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packetbase.UnsupportedPacketException;
import team.gutterteam123.netlib.packets.ConfigNoUpdate;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

public class ServerConfigHandler extends RootAuthHandler<SerializedPacket> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private SimpleCache<String> hashCache = new SimpleCache<>(TimeConstants.getCONFIG_HASH_CACHE(), this::getConfigHash);
    private SimpleCache<String> configCache = new SimpleCache<>(TimeConstants.getCONFIG_CACHE(), () -> {
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
            } else {
                ctx.channel().writeAndFlush(new ConfigNoUpdate());
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
    protected Set<String> getRoots() {
        return Main.getInstance().getRoots();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Error in Server Config Channel", cause);
    }
}
