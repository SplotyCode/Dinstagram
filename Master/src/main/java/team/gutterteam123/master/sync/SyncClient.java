package team.gutterteam123.master.sync;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.master.Master;
import team.gutterteam123.netlib.NetClient;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.handler.RootAuthHandler;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacketEncoder;

import java.net.InetSocketAddress;
import java.util.Set;

public class SyncClient extends NetClient {

    @Getter private ChannelHandlerContext ctx;

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

    public class ClientHandler extends RootAuthHandler<SerializedPacket> {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            SyncClient.this.ctx = ctx;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, SerializedPacket packet) throws Exception {

        }

        @Override
        protected Set<String> getRoots() {
            return Master.getInstance().getConfig().getRoots();
        }
    }

    public void sendPacket(SerializedPacket packet) {
        ctx.channel().writeAndFlush(packet, ctx.voidPromise());
    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
