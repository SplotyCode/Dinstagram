package team.gutterteam123.database.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;

import java.util.*;

@Getter
@Setter
public class ImageObj implements DatabaseObj  {
    protected long imageId;
    protected long likesInt;
    protected Set<Integer> idOfLikers;
    protected List<String> comment, authorOfComment;
    protected long commentsInt;

    @Override
    public void read(Document document) {
        imageId = document.getInteger("imageId");
        likesInt = document.getInteger("likesInt");
        idOfLikers = (Set<Integer>) document.get("idOfLikers");
        commentsInt = document.getInteger("commentsInt");
        comment = (List<String>)document.get("comment");
        comment = (List<String>)document.get("authorOfComment");
    }

    

    @Override
    public void write(Document document) {
        document.put("imageId",imageId);
        document.put("likesInt",likesInt);
        document.put("idOfLikers",idOfLikers);
        document.put("commentsInt",commentsInt);
        document.put("comment",comment);
        document.put("authorOfComment",authorOfComment);

    }
    public enum AccessRuleInt {
        IMAGEID(0),LIKESINT(0),COMMENTSINT(0),ALL(0);
        @Getter
        protected final long field;

        AccessRuleInt(int field){
            this.field = field;
        }
        public Long[] toArray(AccessRuleInt... rules) {
            Long[] list = new Long[rules.length];
            for (int i = 0; i < list.length; i++) {
                list[i] = rules[i].field;
            }
            return list;
        }

            public boolean ContainsAll(AccessRuleInt... rules){
                return Arrays.asList(rules).contains(ALL);
            }
    }
    public enum AccessRuleSetString{
        COMMENT(null),AUTHOROFCOMMENT(null),ALL(null);

        @Getter
        protected final Set<String> field;

        AccessRuleSetString(Set<String> field){
           this.field = field;
        }


        public boolean ContainsAll(){
            return field.contains(ALL);
        }

    }
    public enum AccessRuleSetInt{
        IDOFLIKERS(null),ALL(null);
        @Getter
        protected final Set<Integer> field;

        AccessRuleSetInt(Set<Integer> field){
            this.field = field;
        }
        public boolean ContainsAll(AccessRuleSetInt rules){
            return field.contains(ALL);
        }
    }


}

