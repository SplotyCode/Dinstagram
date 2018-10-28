package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import team.gutterteam123.baselib.dinstagram.NetServerStats;
import team.gutterteam123.baselib.dinstagram.RootStats;
import team.gutterteam123.netlib.packetbase.serialized.PacketSerializer;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;

import java.util.*;

/**
 * TODO: I am pretty sure that we send the host address twice (once as key and then in the NetServerStats Obj)
 */
@Data
@AllArgsConstructor
public class UpdateAllMasters implements SerializedPacket {

    private Set<RootStats> rootStats;
    @Getter private Map<String, NetServerStats> contentStats;
    @Getter private Map<String, NetServerStats> proxyStats;

    @Override
    public void read(PacketSerializer packet) {
        rootStats = new HashSet<>();
        contentStats = new HashMap<>();
        proxyStats = new HashMap<>();

        int roots = packet.readVarInt();
        for (int i = 0; i < roots; i++) {
            rootStats.add(new RootStats(packet.readString(), packet.readLong(), packet.readVarInt()));
        }

        int contentServers = packet.readVarInt();
        for (int i = 0; i < contentServers; i++) {
            contentStats.put(packet.readString(), new NetServerStats(packet.readVarInt(), packet.readVarInt(), packet.readString()));
        }

        int proxyServers = packet.readVarInt();
        for (int i = 0; i < proxyServers; i++) {
            proxyStats.put(packet.readString(), new NetServerStats(packet.readVarInt(), packet.readVarInt(), packet.readString()));
        }
    }

    @Override
    public void write(PacketSerializer packet) {
        packet.writeVarInt(rootStats.size());
        for (RootStats root : rootStats) {
            packet.writeString(root.getAddress());
            packet.writeLong(root.getFreeMemory());
            packet.writeVarInt(root.getCpuUsage());
        }

        packet.writeVarInt(contentStats.size());
        for (Map.Entry<String, NetServerStats> contentServer : contentStats.entrySet()) {
            packet.writeString(contentServer.getKey());
            packet.writeVarInt(contentServer.getValue().getPort());
            packet.writeVarInt(contentServer.getValue().getConnections());
            packet.writeString(contentServer.getValue().getHost());
        }

        packet.writeVarInt(proxyStats.size());
        for (Map.Entry<String, NetServerStats> proxyServer : proxyStats.entrySet()) {
            packet.writeString(proxyServer.getKey());
            packet.writeVarInt(proxyServer.getValue().getPort());
            packet.writeVarInt(proxyServer.getValue().getConnections());
            packet.writeString(proxyServer.getValue().getHost());
        }
    }
}
