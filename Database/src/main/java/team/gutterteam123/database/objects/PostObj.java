package team.gutterteam123.database.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;
import team.gutterteam123.database.DatabaseObj;
@Getter
@Setter
public class PostObj implements DatabaseObj {

    protected String messages;
    protected long imageId;

    @Override
    public void read(Document document) {

    }

    @Override
    public void write(Document document) {

    }
}
