package team.gutterteam123.database.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserObj implements DatabaseObj {

    protected String name, password;
    protected long userId;
    protected Set<Long> following;
    protected Set<Long> follower;
    protected long followerInt, followingInt, postsInt;
    protected long lastPost;
    protected Set<Long> posts;


    @Override
    public void read(Document document) {
        name = document.getString("name");
        password = document.getString("password");
        userId = document.getLong("userId");
        following = (Set<Long>) document.get("following", Set.class);
        follower = (Set<Long>) document.get("follower", Set.class);
        followerInt = document.getLong("followerInt");
        followingInt = document.getLong("followingInt");
        postsInt = document.getLong("postsInt");
        lastPost = document.getLong("lastPosts");
        posts = (Set<Long>) document.get("posts", Set.class);

    }

    @Override
    public void write(Document document) {
        document.put("name", name);
        document.put("userId", userId);
        document.put("following",following);
        document.put("follower", follower);
        document.put("followerInt", followerInt);
        document.put("followingInt", followingInt);
        document.put("postsInt", postsInt);
        document.put("lastPost", lastPost);
        document.put("posts", posts);



    }

}
