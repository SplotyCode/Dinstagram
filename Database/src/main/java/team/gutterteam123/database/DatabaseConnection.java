package team.gutterteam123.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import team.gutterteam123.database.objects.UserObj;

import java.util.ArrayList;


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

    public UserObj getUserByName(String name){
        UserObj user = new UserObj();
        Document document = users.find(Filters.eq("name", name)).first();
        user.read(document);
        return user;
    }

    public void newUser(UserObj user){
        Document document = new Document();
        user.write(document);
        users.insertOne(document);
    }

    public void updateUser(UserObj user) {
        Document document = new Document();
        user.write(document);
        users.replaceOne(Filters.eq("userId", user.getUserId()), document);
    }

    public void deleteUser(UserObj user){
        users.deleteOne(Filters.eq("userId", user.getUserId()));
    }
    
}
