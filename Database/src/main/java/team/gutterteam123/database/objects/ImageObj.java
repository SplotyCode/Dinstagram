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
    protected String hash;
    protected String base64;

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
    public enum AccessRule {
        IMAGEID("imageId"),HASH("hash"),BASE64("base64"),ALL("");
        @Getter
        protected final String field;

        AccessRule(String field){
            this.field = field;
        }
        public String[] toArray(AccessRule... rules) {
            String[] list = new String[rules.length];
            for (int i = 0; i < list.length; i++) {
                list[i] = rules[i].field;
            }
            return list;
        }

            public boolean ContainsAll(AccessRule... rules){
                return Arrays.asList(rules).contains(ALL);
            }
    }
    public Document getImageDetails(AccessRule... rules) {
        Document set = new Document();

        for (AccessRule rule : rules) {
            switch (rule) {
                case IMAGEID:
                    set.append("imageId", imageId);
                    break;
                case HASH:
                    set.append("hash", hash);
                    break;
                case BASE64:
                    set.append("base64", base64);
                    break;
            }
        }
        return set;

    }


}

