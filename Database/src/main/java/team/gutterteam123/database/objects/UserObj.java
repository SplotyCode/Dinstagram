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
    protected static int userId;
    protected static Set<Integer> following;
    protected static Set<Integer> follower;
    protected static int followerInt, followingInt, postsInt;
    protected static int lastPost;
    protected static Set<Integer> posts;


    @Override
    public void read(Document document) {
        name = document.getString("name");
        password = document.getString("password");
        userId = document.getInteger("userId");
        following = (Set<Integer>) document.get("following", Set.class);
        follower = (Set<Integer>) document.get("follower", Set.class);
        followerInt = document.getInteger("followerInt");
        followingInt = document.getInteger("followingInt");
        postsInt = document.getInteger("postsInt");
        lastPost = document.getInteger("lastPost");
        posts = (Set<Integer>) document.get("posts", Set.class);

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
    public enum AccessRuleString {
        NAME("name"),IDOFLIKERS("following"),COMMENT("comment"),AUTHOROFCOMMENT("authorOfComment"),ALL("");
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
    public enum AccessRuleInt{
        POSTSINT(postsInt), FOLLOWINGINT(followingInt), FOLLOWERINT(followerInt), LASTPOST(lastPost), USERID(userId),ALL(0);
        @Getter
        protected final int field;

        AccessRuleInt(int field){
            this.field = field;

        }
        public Integer[] toArray(PostObj.AccessRuleInt... rules){
            Integer[] list = new Integer[rules.length];
            for (int i = 0;i< list.length; i++){
                list[i] = rules[i].field;
            }
            return list;

        }
        public boolean ContainsAll(PostObj.AccessRuleInt... rules){
            return Arrays.asList(rules).contains(ALL);
        }


    }

public enum AccessRuleSetInt{
    FOLLOWING(following),FOLLOWER(follower),POSTS(posts),ALL(null);
    @Getter
    protected final Set<Integer> field;

    AccessRuleSetInt(Set<Integer> field){
        this.field = field;
    }
    public boolean ContainsAll(ImageObj.AccessRuleSetInt rules){
        return field.contains(ALL);
    }
}



}

