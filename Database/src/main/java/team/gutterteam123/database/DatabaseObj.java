package team.gutterteam123.database;

import org.bson.conversions.Bson;

public interface DatabaseObj {

    void read(Bson bson);
    void write(Bson bson);


}
