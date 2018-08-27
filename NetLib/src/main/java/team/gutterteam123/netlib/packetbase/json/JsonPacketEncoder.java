package team.gutterteam123.netlib.packetbase.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import team.gutterteam123.netlib.packetbase.PacketRegistry;

@AllArgsConstructor
public class JsonPacketEncoder extends MessageToByteEncoder<JsonPacket> {

    private static final Gson GSON = new Gson();

    private PacketRegistry<JsonPacket> registry;

    @Override
    protected void encode(ChannelHandlerContext ctx, JsonPacket packet, ByteBuf byteBuf) throws Exception {
        int id = registry.getIdByPacket(packet.getClass());

        String json = GSON.toJson(packet);

        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        obj.addProperty("id", id);

        byteBuf.writeBytes(GSON.toJson(obj).getBytes());
    }

}
