package team.gutterteam123.baselib.objects;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pair<A, B> {

    private A a;
    private B b;

}
