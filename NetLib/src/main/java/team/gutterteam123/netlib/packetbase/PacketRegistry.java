package team.gutterteam123.netlib.packetbase;

import com.google.common.collect.HashBiMap;

public class PacketRegistry<P extends Packet> {

    private HashBiMap<Class<? extends P>, Integer> packetClasses = HashBiMap.create();

    public void register(Class<? extends P> clazz) {
        packetClasses.put(clazz, packetClasses.size());
    }

    public int getIdByPacket(Class<? extends P> clazz) {
        return packetClasses.get(clazz);
    }

    public Class<? extends P> getPacketById(int id) {
        return packetClasses.inverse().get(id);
    }

}
