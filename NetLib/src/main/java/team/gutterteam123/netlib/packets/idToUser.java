package team.gutterteam123.netlib.packets;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;

@Data
@AllArgsConstructor
public class idToUser implements JsonPacket {
    long userId;

}
