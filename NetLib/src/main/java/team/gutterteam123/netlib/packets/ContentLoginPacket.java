package team.gutterteam123.netlib.packets;

import team.gutterteam123.netlib.packetbase.json.JsonPacket;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ContentLoginPacket implements JsonPacket {
  
  private String name, password;

}
