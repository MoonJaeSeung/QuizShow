var nick;

window.onload = function() {
    axios.get('/nick')
        .then(response => {
            nick = response.data;
            console.log(nick);
            // 웹소켓 연결 - 닉네임을 가져온 후에 연결 시작
            connect();
        })
        .catch(error => {
            console.error(error);
        });
};

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({ "nick": nick }, function (frame) {  // Add the nickname to the connect header
        setConnected(true);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/userList', function (userList) {
            showUserList(JSON.parse(userList.body));
        });
    });
}

function showUserList(userList) {
    let userListContainer = document.getElementById("userList");
    userListContainer.innerHTML = "";  // Clear the container
    userList.forEach(user => {
        let userElement = document.createElement("p");
        userElement.textContent = user;
        userListContainer.appendChild(userElement);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}




function sendMessage() {

    // 현재 시간 가져오기
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    const currentTime = `${hours}:${minutes}:${seconds}`;
    let input = document.getElementById("message");


    const messageData = {
        'nick': nick,
        'message': $("#message").val(),
        'time': currentTime  // 시간 값 추가
    };

    stompClient.send("/app/hello", {}, JSON.stringify(messageData));
    input.value="";
}

function showGreeting(message) {
    $("#greetings").append("<div style='margin-top: 10px'>" + message + "</div>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });

    // 웹소켓 연결
    connect();
});

window.addEventListener("beforeunload", function(e) {
    disconnect();
});