package team.gutterteam123.netlib.packetbase;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SerializedPacketDecoder extends ByteToMessageDecoder {


	private PacketRegistry<SerializedPacket> registry;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> output) throws Exception {
		PacketSerializer ps = new PacketSerializer(bytebuf);
		int id = ps.readVarInt();
		Class<? extends SerializedPacket> packetClass = registry.getPacketById(id);
		if(packetClass == null) {
			throw new NullPointerException("Coud not find that Packet");
		}
		SerializedPacket p = packetClass.newInstance();
		p.read(ps);
		output.add(p);
	}

}
