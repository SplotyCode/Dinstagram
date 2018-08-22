package team.gutterteam123.netlib;

import lombok.Getter;
import team.gutterteam123.netlib.packetbase.PacketRegistry;
import team.gutterteam123.netlib.packetbase.SerializedPacket;
import team.gutterteam123.netlib.packets.ConfigRequestUpdate;
import team.gutterteam123.netlib.packets.ConfigUpdate;

public class Registrys {

    @Getter private static Registrys instance = new Registrys();

    @Getter private PacketRegistry<SerializedPacket> configOut = new PacketRegistry<>();
    @Getter private PacketRegistry<SerializedPacket> configIn = new PacketRegistry<>();

    private Registrys() {
        configIn.register(ConfigRequestUpdate.class);

        configOut.register(ConfigUpdate.class);
    }
}
