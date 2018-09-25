var connectionList = [];

function ConnectToServer(URL...){
connectionList.clear();
connectionList.concat(URL);
var socket = new io.Socket();
socket.connect(connectionList.pop(RandomNumber(connectionList.length)));
}


function RandomNumber(max){
 return Math.random()*max; 
}
