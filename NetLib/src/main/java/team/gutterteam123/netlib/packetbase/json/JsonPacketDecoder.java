package team.gutterteam123.netlib.packetbase.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import team.gutterteam123.netlib.packetbase.PacketRegistry;

import java.nio.charset.Charset;
import java.util.List;

@AllArgsConstructor
public class JsonPacketDecoder extends ByteToMessageDecoder {

    private PacketRegistry<JsonPacket> registry;

    private static final Gson GSON = new Gson();
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String text = new String(bytes, CHARSET);

        JsonObject o = JSON_PARSER.parse(text).getAsJsonObject();

        Class<? extends JsonPacket> clazz = registry.getPacketById(o.get("id").getAsInt());
        if(clazz != null) {
            o.remove("id");

            JsonPacket packet = GSON.fromJson(o, clazz);
            list.add(packet);
        }
    }

}
