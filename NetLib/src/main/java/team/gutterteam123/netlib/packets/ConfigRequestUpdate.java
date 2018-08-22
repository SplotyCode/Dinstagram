package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.netlib.packetbase.PacketSerializer;
import team.gutterteam123.netlib.packetbase.SerializedPacket;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ConfigRequestUpdate implements SerializedPacket {

    private String hash;

    @Override
    public void read(PacketSerializer packet) {
        hash = packet.readString();
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeString(hash);
    }
}
