package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.gutterteam123.netlib.packetbase.serialized.PacketSerializer;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
@Data
@AllArgsConstructor
public class userIdPacket implements SerializedPacket {

        long userId;

    @Override
    public void read(PacketSerializer packet) {
        userId = packet.readLong();
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeLong(userId);
    }
}
