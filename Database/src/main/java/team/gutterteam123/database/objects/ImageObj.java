package team.gutterteam123.database.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;

import java.util.*;

@Getter
@Setter
public class ImageObj implements DatabaseObj {

    protected long imageId;
    private String hash;
    private String base64;

    @Override
    public void read(Document document) {
        imageId = document.getInteger("imageId");
        hash = document.getString("hash");
        base64 = document.getString("base64");
    }

    

    @Override
    public void write(Document document) {
        document.put("imageId", imageId);
        document.put("hash", hash);
        document.put("base64", base64);
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

