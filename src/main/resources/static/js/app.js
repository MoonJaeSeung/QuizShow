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
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected   : ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

var nick = null;

window.onload = function() {
    axios.get('/getNick')
        .then(response => {
            nick = response.data.nick;
            // nick 값을 사용하여 필요한 작업 수행
            console.log(nick);
        })
        .catch(error =>{
            console.error(error);
        });


};


function sendMessage() {

    // 현재 시간 가져오기
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    const currentTime = `${hours}:${minutes}:${seconds}`;

    const messageData = {
        'nick': nick,
        'message': $("#message").val(),
        'time': currentTime  // 시간 값 추가
    };

    stompClient.send("/app/hello", {}, JSON.stringify(messageData));
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


// function getCookieValue(cookieName) {
//     const name = cookieName + "=";
//     const decodedCookie = decodeURIComponent(document.cookie);
//     const cookieArray = decodedCookie.split(';');
//
//     for (let i = 0; i < cookieArray.length; i++) {
//         let cookie = cookieArray[i];
//         while (cookie.charAt(0) === ' ') {
//             cookie = cookie.substring(1);
//         }
//         if (cookie.indexOf(name) === 0) {
//             return cookie.substring(name.length, cookie.length);
//         }
//     }
//
//     return "";
// }
//
// const nick = getCookieValue("nick");
// console.log(nick);


