package team.gutterteam123.configserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import team.gutterteam123.baselib.FileConstants;
import team.gutterteam123.baselib.util.Cache;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packetbase.UnsupportedPacketException;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ConfigHandler extends SimpleChannelInboundHandler<SerializedPacket> {

    private Cache<String> hashCache = new Cache<String>(10 * 1000, this::getConfigHash);
    private Cache<String> configCache = new Cache<String>(10 * 1000, () -> {
        try {
            return FileUtils.readFileToString(FileConstants.getCONFIG_SERVER(), Charset.forName("Utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    });

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SerializedPacket packet) throws Exception {
        System.out.println("Reading " + packet.getClass().getSimpleName());
        if (packet instanceof ConfigRequestUpdate) {
            ConfigRequestUpdate request = (ConfigRequestUpdate) packet;
            if (request.getHash().isEmpty() || !request.getHash().equals(hashCache.get())) {
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
            ex.printStackTrace();
        }
        return "";
    }
}
