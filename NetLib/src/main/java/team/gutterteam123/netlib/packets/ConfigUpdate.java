package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.netlib.packetbase.PacketSerializer;
import team.gutterteam123.netlib.packetbase.SerializedPacket;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ConfigUpdate implements SerializedPacket {

    private String config;

    @Override
    public void read(PacketSerializer packet) {
        config = packet.readString();
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeString(config);
    }
}
