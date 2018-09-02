package team.gutterteam123.baselib.dinstagram;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RootStats {

    private String address;
    private long freeMemory;
    private int cpuUsage;

}
