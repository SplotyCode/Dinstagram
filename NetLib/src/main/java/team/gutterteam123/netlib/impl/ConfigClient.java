package team.gutterteam123.netlib.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.netlib.NetClient;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketEncoder;
import team.gutterteam123.netlib.packetbase.UnsupportedPacketException;
import team.gutterteam123.netlib.packets.ConfigNoUpdate;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class ConfigClient extends NetClient {

    public ConfigClient(InetSocketAddress address) {
        super(address);
    }

    @Override protected void onClose(Future future) {}

    @Setter private Consumer<String> onConfigChange;

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getConfigIn()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getConfigOut()));
        pipeline.addLast(new ConfigClientHandler());
    }

    @Override
    protected String getDisplayName() {
        return "Config Client";
    }

    public class ConfigClientHandler extends SimpleChannelInboundHandler<SerializedPacket> {

        Logger logger = LoggerFactory.getLogger(getClass());


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("Exception in Client Channel", cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Config Client Channel Active... sending Hash!");
            String hash = "";
            if (FileConstants.getCONFIG().exists()) {
                FileInputStream fis = new FileInputStream(FileConstants.getCONFIG());
                hash = DigestUtils.sha1Hex(fis);
                fis.close();
            }
            ctx.channel().writeAndFlush(new ConfigRequestUpdate(hash));
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, SerializedPacket packet) throws Exception {
            if (packet instanceof ConfigUpdate) {
                ConfigUpdate update = (ConfigUpdate) packet;
                onConfigChange.accept(update.getConfig());
                FileUtils.write(FileConstants.getCONFIG(), update.getConfig(), Charset.forName("Utf-8"));
                ctx.channel().close();
            } else if (packet instanceof ConfigNoUpdate) {
                ctx.channel().close();
            } else {
                throw new UnsupportedPacketException(packet.getClass().getSimpleName() + " is not supported!");
            }
        }
    }
}
