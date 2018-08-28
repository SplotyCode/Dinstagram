package team.gutterteam123.netlib.linked;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.function.Supplier;

public class MasterLinked {

    @Getter private static MasterLinked instance = new MasterLinked();

    private MasterLinked() {}

    @Getter @Setter private Supplier<Set<String>> roots;

}
