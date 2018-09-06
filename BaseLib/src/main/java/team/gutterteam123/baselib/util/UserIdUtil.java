package Dinstagram.BaseLib.src.main.java.team.gutterteam123.baselib.util;

public class UserIdUtil{
public long lastId = 0;
public long userId;
    public long newUserId(){
        userId = lastId++;
        lastId  = userId;
        return userId;
    }
    }
