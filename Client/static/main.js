const io = require('socket.io-client');
import io from 'socket.io-client';

var Server = {
    ConnectingStanding = false,
    URLConnected = ""

}
function ConnectToServer(URL){
var connectionList = [URL.length];
connectionList.clear();
connectionList.concat(URL);
var socket = io.connect(connectionList.pop(RandomNumber(connectionList.length)))
Server.ConnectingStanding = true;
Server.URLConnected = URL;
}
function ReadFile(Filepath, ConnetionServer){
    ConnectToServer(ConnetionServer);
    fs.ReadFile(Filepath, function read(err,data){
        if(err){
            throw err;
        }
        return data;
    })



}

function RandomNumber(max){
 return Math.random()*max; 
}
