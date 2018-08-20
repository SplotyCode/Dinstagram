package team.gutterteam123.database.objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.conversions.Bson;
import team.gutterteam123.database.DatabaseObj;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class UserObj implements DatabaseObj {

    protected String name, password;
    protected long userId;
    protected Set<Long> following;
    protected Set<Long> follower;
    protected long followerInt, followingInt, postsInt;
    protected long lastPost;
    protected Set<Long> posts;



    @Override
    public void read(Bson bson) {

    }

    @Override
    public void write(Bson bson) {

    }
}
