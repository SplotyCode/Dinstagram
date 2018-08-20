package team.gutterteam123.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import team.gutterteam123.database.objects.UserObj;

public class DatabaseConnection {

    @Getter private MongoClient client;
    private MongoDatabase database;

    private MongoCollection<Document> users;

    public DatabaseConnection(String connection) {
        client = new MongoClient(new MongoClientURI(connection));
        database = client.getDatabase("dinstagram");

        users = database.getCollection("users");
    }

    public UserObj getUserById(long id) {
        UserObj user = new UserObj();
        Document document = users.find(Filters.eq("userId", id)).first();
        user.read(document);

        return user;
    }




}
