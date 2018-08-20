package team.gutterteam123.database;

import org.bson.Document;

public interface DatabaseObj {

    void read(Document document);
    void write(Document document);


}
