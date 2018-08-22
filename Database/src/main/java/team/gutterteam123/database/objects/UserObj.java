package team.gutterteam123.database.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;

import java.util.Arrays;
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
        lastPost = document.getLong("lastPost");
        posts = (Set<Long>) document.get("posts", Set.class);

    }

    @Override
    public void write(Document document) {
        document.put("name", name);
        document.put("userId", userId);
        document.put("following", following);
        document.put("follower", follower);
        document.put("followerInt", followerInt);
        document.put("followingInt", followingInt);
        document.put("postsInt", postsInt);
        document.put("lastPost", lastPost);
        document.put("posts", posts);


    }
    public enum AccessRule {
        NAME("name"),PASSWORD("password"),USERID("userId"),FOLLOWING("following"),FOLLOWER("follower"),FOLLOWERINT("followerInt"),FOLLOWINGINT("followingInt"),POSTSINT("postsInt"),LASTPOST("lastPost"),POSTS("posts"),ALL("");
        @Getter
        protected final String field;

        AccessRule(String field) {
            this.field = field;
        }
        public String[] toArray(AccessRule... rules){
            String[] list = new String[rules.length];
            for (int i = 0; i < list.length; i++){
                list[i] = rules[i].field;
            }
            return list;
        }
        public boolean ContainsAll(AccessRule... rules){
            return Arrays.asList(rules).contains(ALL);
        }
    }

}

