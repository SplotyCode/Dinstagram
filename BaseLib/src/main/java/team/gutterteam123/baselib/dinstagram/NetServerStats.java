package team.gutterteam123.baselib.dinstagram;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NetServerStats {

    private int port, connections;
    private String host;

}
