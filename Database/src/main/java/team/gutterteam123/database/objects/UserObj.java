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
    protected int followerInt, followingInt, postsInt;
    protected long lastPost;
    protected Set<Long> posts;


    @Override
    public void read(Document document) {
        name = document.getString("name");
        password = document.getString("password");
        userId = document.getInteger("userId");
        following = (Set<Long>) document.get("following", Set.class);
        follower = (Set<Long>) document.get("follower", Set.class);
        followerInt = document.getInteger("followerInt");
        followingInt = document.getInteger("followingInt");
        postsInt = document.getInteger("postsInt");
        lastPost = document.getInteger("lastPost");
        posts = (Set<Long>) document.get("posts", Set.class);

    }

    @Override
    public void write(Document document) {
        document.put("name", name);
        document.put("password", password)
        document.put("userId", userId);
        document.put("following", following);
        document.put("follower", follower);
        document.put("followerInt", followerInt);
        document.put("followingInt", followingInt);
        document.put("postsInt", postsInt);
        document.put("lastPost", lastPost);
        document.put("posts", posts);


    }
    public enum AccessRuleString {
        NAME("name"),IDOFLIKERS("following"),COMMENT("comment"),AUTHOROFCOMMENT("authorOfComment"),PASSWORD("password"),USERID("userId"),FOLLOWER("follower"),FOLLOWING("following")FOLLOWERINT("followerint"),FOLLOWINGINT("followingint"),ALL("");
        @Getter
        protected final String field;

        AccessRuleString(String field) {
            this.field = field;
        }
        public String[] toArray(PostObj.AccessRuleString... rules){
            String[] list = new String[rules.length];
            for (int i = 0; i < list.length; i++){
                list[i] = rules[i].field;
            }
            return list;
        }
        public boolean ContainsAll(PostObj.AccessRuleString... rules){
            return Arrays.asList(rules).contains(ALL);
        }
    }


    }



}

