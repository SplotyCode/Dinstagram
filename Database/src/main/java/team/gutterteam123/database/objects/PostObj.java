package team.gutterteam123.database.objects;

import org.bson.conversions.Bson;
import team.gutterteam123.database.DatabaseObj;

public class PostObj implements DatabaseObj {

    protected String messages;
    protected long imageId;


    @Override
    public void read(Bson bson) {

    }

    @Override
    public void write(Bson bson) {

    }
}
