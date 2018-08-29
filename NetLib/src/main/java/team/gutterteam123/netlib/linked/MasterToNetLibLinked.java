package team.gutterteam123.netlib.linked;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.function.Supplier;

public class MasterToNetLibLinked {

    @Getter private static MasterToNetLibLinked instance = new MasterToNetLibLinked();

    private MasterToNetLibLinked() {}

    @Getter @Setter private Supplier<Set<String>> roots;

}
