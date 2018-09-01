package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.gutterteam123.netlib.packetbase.serialized.PacketSerializer;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMasterStatus implements SerializedPacket {

    private int usedRam;
    private int cpuPercentage;
    private HashMap<Integer, Integer> proxyConnections;
    private HashMap<Integer, Integer> contentConnections;


    @Override
    public void read(PacketSerializer packet) {
        usedRam = packet.readVarInt();
        cpuPercentage = packet.readVarInt();

        readMap(proxyConnections, packet);
        readMap(contentConnections, packet);
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeVarInt(usedRam);
        packet.writeVarInt(cpuPercentage);
        proxyConnections = new HashMap<>();
        contentConnections = new HashMap<>();

        writeMap(proxyConnections, packet);
        writeMap(contentConnections, packet);
    }

    private void writeMap(Map<Integer, Integer> map, PacketSerializer packet) {
        packet.writeVarInt(map.size());
        for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
            packet.writeVarInt(entry.getKey());
            packet.writeVarInt(entry.getValue());
        }
    }

    private void readMap(Map<Integer, Integer> map, PacketSerializer packet) {
        int max = packet.readVarInt();
        for (int i = 0; i < max; i++) {
            map.put(packet.readVarInt(), packet.readVarInt());
        }
    }
}
