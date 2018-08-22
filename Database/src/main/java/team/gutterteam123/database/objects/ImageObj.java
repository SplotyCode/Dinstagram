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
public class ImageObj implements DatabaseObj  {
    protected long imageId;
    protected long likesInt;
    protected Set<Long> idOfLikers;
    protected List<String> comment, authorOfComment;
    protected long commentsInt;

    @Override
    public void read(Document document) {
        imageId = document.getLong("imageId");
        likesInt = document.getLong("likesInt");
        idOfLikers = (Set<Long>) document.get("idOfLikers");
        commentsInt = document.getLong("commentsInt");
        comment = (List<String>)document.get("comment");
        comment = (List<String>)document.get("authorOfComment");
    }

    

    @Override
    public void write(Document document) {

    }
    public enum AccessRule {
        IMAGEID("imageId"),LIKESINT("likesInt"),IDOFLIKERS("following"),COMMENTSINT("commentsInt"),COMMENT("comment"),AUTHOROFCOMMENT("authorOfComment"),ALL("");
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

