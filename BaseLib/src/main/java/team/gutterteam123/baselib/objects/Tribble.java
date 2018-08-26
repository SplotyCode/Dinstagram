package team.gutterteam123.baselib.objects;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tribble<A, B, C> {

    private A a;
    private B b;
    private C c;

}
