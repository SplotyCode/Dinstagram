package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.gutterteam123.netlib.packetbase.PacketSerializer;
import team.gutterteam123.netlib.packetbase.SerializedPacket;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MasterSyncDestroy implements SerializedPacket {

    private String betterRoot;

    @Override
    public void read(PacketSerializer packet) {
        betterRoot = packet.readString();
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeString(betterRoot);
    }
}
