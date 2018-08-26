package team.gutterteam123.netlib;

import lombok.Getter;
import team.gutterteam123.netlib.packetbase.PacketRegistry;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packets.ConfigNoUpdate;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;
import team.gutterteam123.netlib.packets.MasterSyncDestroy;

public class Registrys {

    @Getter private static Registrys instance = new Registrys();

    @Getter private PacketRegistry<SerializedPacket> configOut = new PacketRegistry<>();
    @Getter private PacketRegistry<SerializedPacket> configIn = new PacketRegistry<>();

    @Getter private PacketRegistry<SerializedPacket> masterOut = new PacketRegistry<>();
    @Getter private PacketRegistry<SerializedPacket> masterIn = new PacketRegistry<>();

    private Registrys() {
        /* Config */
        configIn.register(ConfigRequestUpdate.class);

        configOut.register(ConfigUpdate.class);
        configOut.register(ConfigNoUpdate.class);

        masterIn.register(MasterSyncDestroy.class);
    }
}
