package team.gutterteam123.netlib.packetbase.json;

import team.gutterteam123.netlib.packetbase.Packet;

public interface JsonPacket extends Packet {
  @Getter @Setter
  Packet packet = new Packet(); 

}
