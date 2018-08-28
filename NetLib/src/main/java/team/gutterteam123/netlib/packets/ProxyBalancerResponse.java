package team.gutterteam123.netlib.packets;

import lombok.Data;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;

@Data
public class ProxyBalancerResponse implements JsonPacket {

    private String address;
    private int port;

}
