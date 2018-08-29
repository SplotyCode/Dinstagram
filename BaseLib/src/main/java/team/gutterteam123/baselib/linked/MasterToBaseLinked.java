package team.gutterteam123.baselib.linked;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

public class MasterToBaseLinked {

    @Getter private static MasterToBaseLinked instance = new MasterToBaseLinked();

    private MasterToBaseLinked() {}

    @Getter @Setter private Supplier<String> serverGroup;
}
