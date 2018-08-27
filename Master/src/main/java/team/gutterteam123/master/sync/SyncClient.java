package team.gutterteam123.master.sync;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.netlib.NetClient;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketEncoder;

import java.net.InetSocketAddress;

public class SyncClient extends NetClient {

    public SyncClient(String address) {
        super(new InetSocketAddress(address, PortConstants.getMASTER_SYNC()));
    }

    @Override
    protected void onClose(Future future) {

    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getMasterIn()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getMasterOut()));
        pipeline.addLast(new ClientHandler());
    }

    public class ClientHandler extends SimpleChannelInboundHandler<SerializedPacket> {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, SerializedPacket packet) throws Exception {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("Exception in Destroy Client Channel", cause);
        }
    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
