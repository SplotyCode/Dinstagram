package team.gutterteam123.master.sync;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.Getter;
import lombok.Setter;
import team.gutterteam123.baselib.constants.PortConstants;
import team.gutterteam123.master.Master;
import team.gutterteam123.netlib.NetServer;
import team.gutterteam123.netlib.Registrys;
import team.gutterteam123.netlib.handler.RootAuthHandler;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packetbase.SerializedPacketDecoder;
import team.gutterteam123.netlib.packetbase.SerializedPacketEncoder;
import team.gutterteam123.netlib.packets.MasterSyncDestroy;

import java.util.Set;

public class SyncServer extends NetServer {

    public SyncServer() {
        super(PortConstants.getMASTER_SYNC());
    }

    @Getter @Setter private Runnable start;

    @Override protected void close(ChannelFuture future) {}

    @Override
    protected void onStart(ChannelFuture future) {
        if (start != null) {
            start.run();
        }
    }

    @Override
    protected void onChannelCreation(ChannelPipeline pipeline) {
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new SerializedPacketEncoder(Registrys.getInstance().getMasterOut()));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new SerializedPacketDecoder(Registrys.getInstance().getMasterIn()));
        pipeline.addLast(new ServerHandler());
    }

    public class ServerHandler extends RootAuthHandler<SerializedPacket> {

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
        protected Set<String> getRoots() {
            return Master.getInstance().getConfig().getRoots();
        }
    }

    @Override
    protected String getDisplayName() {
        return "Master Sync Server";
    }
}
