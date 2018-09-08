package team.gutterteam123.database.objects;

import lombok.Getter;
import org.bson.Document;
import sun.security.x509.CertAttrSet;
import team.gutterteam123.database.DatabaseObj;

import java.util.Arrays;

public class Message implements DatabaseObj {

    protected String creationTime;
    private String content;
    private String sender;
    private String receiver;

    @Override
    public void read(Document document) {
    creationTime = document.getString("creationTime");
    content = document.getString("content");
    sender = document.getString("sender");
    receiver = document.getString("receiver");

    }

    @Override
    public void write(Document document) {
    document.put("creationTime",creationTime);
    document.put("content", content);
    document.put("sender",sender);
    document.put("receiver", receiver);
    }
    public enum AccessRule{
        CREATIONTIME("creationTime"),CONTENT("content"),SENDER("sender"),RECEIVER("receiver"),ALL("");
        @Getter
    protected final String field;


        AccessRule(String field){
            this.field = field;
        }
        public String[] toArray(AccessRule... rules){
            String[] list = new String[rules.length];
            for(int i=0;i<rules.length;i++){
                list[i] = rules[i].field;
            }
            return list;

        }
        public boolean ContainsAll(AccessRule... rules){
            return Arrays.asList(rules).contains(ALL);

        }

        }
    public Document getMessageDetails(AccessRule... rules){
        Document set = new Document();
        for(Message.AccessRule rule : rules){
            switch(rule){
                case CREATIONTIME:
                    set.append("creationTime",creationTime);
                    break;
                case CONTENT:
                    set.append("content",content);
                    break;
                case SENDER:
                    set.append("sender",sender);
                    break;
                case RECEIVER:
                    set.append("receiver",receiver);
                    break;
            }
        }
        return set;
    }
}
