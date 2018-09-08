package team.gutterteam123.baselib.util;

import org.bson.Document;

public class UserIdUtil{
long lastId;
long userId;
    public long newUserId(){
        Document lastUserIdSafe = new Document();
        lastUserIdSafe.put("lastId", lastId);
        lastId = lastUserIdSafe.getLong("lastId");
        lastId++;
        lastId  = userId;
        return userId;
    }
    }
