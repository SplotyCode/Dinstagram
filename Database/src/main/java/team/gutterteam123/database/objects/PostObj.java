package team.gutterteam123.database.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostObj implements DatabaseObj {

    protected String messages;
    protected long imageId;
    protected long likesInt;
    protected static Set<Integer> idOfLikers;
    protected long commentsInt;
    protected static List<String> comment;
    protected static List<String> authorOfComment;

    @Override
    public void read(Document document) {
        messages = document.getString("messages");
        imageId = document.getInteger("imageId");
        likesInt = document.getInteger("likesInt");
        idOfLikers = (Set<Integer>) document.get("idOfLikers");
        commentsInt = document.getInteger("commentsInt");
        comment = (List<String>)document.get("comment");
        comment = (List<String>)document.get("authorOfComment");
    }

    @Override
    public void write(Document document) {
        document.put("messages",messages);
        document.put("imageId",imageId);
        document.put("likesInt",likesInt);
        document.put("idOfLikers",idOfLikers);
        document.put("commentsInt",commentsInt);
        document.put("comment",comment);
        document.put("authorOfComment",authorOfComment);

    }
    public enum AccessRuleString {
        MESSAGES("messages"),IDOFLIKERS("following"),COMMENT("comment"),AUTHOROFCOMMENT("authorOfComment"),ALL("");
        @Getter
        protected final String field;

        AccessRuleString(String field) {
            this.field = field;
        }
        public String[] toArray(AccessRuleString... rules){
            String[] list = new String[rules.length];
            for (int i = 0; i < list.length; i++){
                list[i] = rules[i].field;
            }
            return list;
        }
        public boolean ContainsAll(AccessRuleString... rules){
            return Arrays.asList(rules).contains(ALL);
        }
    }
    public enum AccessRuleInt{
        IMAGEID(0),LIKESINT(0),COMMENTSINT(0),ALL(0);
        @Getter
        protected final int field;

        AccessRuleInt(int field){
            this.field = field;

        }
        public Integer[] toArray(AccessRuleInt... rules){
            Integer[] list = new Integer[rules.length];
            for (int i = 0;i< list.length; i++){
                list[i] = rules[i].field;
            }
            return list;

        }
        public boolean ContainsAll(AccessRuleInt... rules){
            return Arrays.asList(rules).contains(ALL);
        }

    }
    public enum AccessRuleSetInt{
        IDOFLIKERS(idOfLikers),ALL(null);
        @Getter
        protected final Set<Integer> field;

        AccessRuleSetInt(Set<Integer> field){
            this.field = field;
        }
        public boolean ContainsAll(ImageObj.AccessRuleSetInt rules){
            return field.contains(ALL);
        }
    }
    public enum AccessRuleSetString{
        COMMENT(comment),AUTHOROFCOMMENT(authorOfComment),ALL(null);

        @Getter
        protected final List<String> field;

        AccessRuleSetString(List<String> field){
            this.field = field;
        }


        public boolean ContainsAll(){
            return field.contains(ALL);
        }

    }

}
