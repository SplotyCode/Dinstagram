package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.netlib.packetbase.serialized.PacketSerializer;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProxyBalancerResponse implements SerializedPacket {

    private String address;
    private int port;

    @Override
    public void read(PacketSerializer packet) {
        address = packet.readString();
        port = packet.readVarInt();
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeString(address);
        packet.writeVarInt(port);
    }
}
