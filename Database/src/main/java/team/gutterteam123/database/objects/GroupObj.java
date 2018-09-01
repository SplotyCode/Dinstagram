package team.gutterteam123.database.objects;

import org.bson.Document;
import team.gutterteam123.database.DatabaseObj;

import java.util.Map;

public class GroupObj implements DatabaseObj {

    private String name, decription;
    private long id, creationTime, owner, profileImage;
    private Integer lastMessage;

    /* UserId - Last Received Message */
    private Map<Long, Integer> members;

    @Override
    public void read(Document document) {

    }

    @Override
    public void write(Document document) {

    }
}
