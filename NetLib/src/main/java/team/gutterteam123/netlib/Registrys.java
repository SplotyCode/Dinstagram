package team.gutterteam123.netlib;

import lombok.Getter;
import team.gutterteam123.netlib.packetbase.PacketRegistry;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;
import team.gutterteam123.netlib.packetbase.serialized.SerializedPacket;
import team.gutterteam123.netlib.packets.*;

public class Registrys {

    @Getter private static Registrys instance = new Registrys();

    @Getter private PacketRegistry<SerializedPacket> configOut = new PacketRegistry<>();
    @Getter private PacketRegistry<SerializedPacket> configIn = new PacketRegistry<>();

    @Getter private PacketRegistry<SerializedPacket> masterOut = new PacketRegistry<>();
    @Getter private PacketRegistry<SerializedPacket> masterIn = new PacketRegistry<>();

    @Getter private PacketRegistry<JsonPacket> balancerOut = new PacketRegistry<>();
    @Getter private PacketRegistry<JsonPacket> balancerIn = new PacketRegistry<>();

    @Getter private PacketRegistry<JsonPacket> contentOut = new PacketRegistry<>();
    @Getter private PacketRegistry<JsonPacket> contentIn = new PacketRegistry<>();

    private Registrys() {
        /* Config */
        configIn.register(ConfigRequestUpdate.class);

        configOut.register(ConfigUpdate.class);
        configOut.register(ConfigNoUpdate.class);

        /* Master */
        masterIn.register(MasterSyncDestroy.class);

        /* Balancer */
        balancerOut.register(ProxyBalancerResponse.class);
    }
}
