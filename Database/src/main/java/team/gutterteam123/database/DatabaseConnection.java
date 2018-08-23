package team.gutterteam123.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import team.gutterteam123.database.objects.ImageObj;
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
        users.replaceOne(Filters.eq("userId", UserObj.AccessRuleInt.USERID.getField()), document);
    }

    public void deleteUser(UserObj user){
        users.deleteOne(Filters.eq("userId", UserObj.AccessRuleInt.USERID.getField()));
    }

    public void deleteUsers(UserObj... user){
        Bson[] filters = new Bson[user.length];
        for (int i = 0; i < user.length; i++) {
            filters[i] = Filters.eq("userId", UserObj.AccessRuleInt.USERID.getField());
        }
        users.deleteMany(Filters.or(filters));
    }
    public String getNameById(long id){
        UserObj user = getUserById(id);
        return UserObj.AccessRuleString.NAME.getField();
    }
    public long getLikesOfImage(){
        return ImageObj.AccessRuleInt.LIKESINT.getField();

    }

}
