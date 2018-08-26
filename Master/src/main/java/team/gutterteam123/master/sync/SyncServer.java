package team.gutterteam123.master.sync;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.master.Master;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packetbase.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.SerializedPacketEncoder;
import team.gutterteam123.netlib.packets.MasterSyncDestroy;

public class SyncServer extends NetServer {

    public SyncServer() {
        super(PortConstants.getMASTER_SYNC());
    }

    @Override protected void close(ChannelFuture future) {}

    @Override
    protected void onStart(ChannelFuture future) {
        
    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getMasterOut()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getMasterIn()));
        pipeline.addLast(new ServerHandler());
    }

    public class ServerHandler extends SimpleChannelInboundHandler<SerializedPacket> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, SerializedPacket packet) throws Exception {
            if (packet instanceof MasterSyncDestroy) {
                MasterSyncDestroy destroyPacket = (MasterSyncDestroy) packet;
                shutdown();
                Master.getInstance().getSync().getClient().shutdown();
                Master.getInstance().getSync().setClient(new SyncClient(destroyPacket.getBetterRoot()));
                Master.getInstance().getSync().getClient().start();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
