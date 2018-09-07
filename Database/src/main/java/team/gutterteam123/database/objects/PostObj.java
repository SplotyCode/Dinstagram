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
    protected Set<Long> idOfLikers;
    protected long commentsInt;
    protected List<String> comment;
    protected List<String> authorOfComment;

    @Override
    public void read(Document document) {
        messages = document.getString("messages");
        imageId = document.getInteger("imageId");
        likesInt = document.getInteger("likesInt");
        idOfLikers = (Set<Long>) document.get("idOfLikers");
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
    public enum AccessRule {
        MESSAGES("messages"),IMAGEID("imageId"),LIKESINT("likesInt"),IDOFLIKERS("idOfLikers"),COMMENTSINT("commentsInt"),COMMENT("comment"),AUTHOROFCOMMENT("authorOfComment"),ALL("");
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
    public void AccessRule(AccessRule... rules) {
        Document set = new Document();

        for (AccessRule rule : rules) {
            switch (rule) {
                case MESSAGES:
                    set.append("messages", this.messages);
                    break;
                case IMAGEID:
                    set.append("imageId", this.imageId);
                    break;
                case LIKESINT:
                    set.append("likesInt", this.likesInt);
                    break;
                case IDOFLIKERS:
                    set.append("idOfLikers", this.idOfLikers);
                    break;
                case AUTHOROFCOMMENT:
                    set.append("authorOfComment", this.authorOfComment);
                    break;
                case COMMENT:
                    set.append("comment", this.comment);
                    break;
                case COMMENTSINT:
                    set.append("commentsInt", this.commentsInt);
                    break;
            }
        }


    }
    }

