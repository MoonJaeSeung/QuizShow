var nick;
var stompClient = null;
let sessionId;


// 페이지 로드 시 닉네임을 가져와 웹소켓 연결
$(function () {
    axios.get('/nick')
        .then(response => {
            nick = response.data;
            console.log(nick);
            connect();
        })
        .catch(error => {
            console.error(error);
        });
});

// 버튼 클릭 이벤트 처리
$(document).ready(function() {
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendMessage);
});



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

function requestSessionId() {
    stompClient.send("/app/requestSessionId");
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({ "nick": nick }, function (frame) {  // Modify this line
        console.log(JSON.stringify(frame.headers, null, 2) + "---------");
        setConnected(true);
        sessionId = frame.headers['session']; // 변경된 코드
        console.log("Connected with session ID: ", sessionId); // 이 줄을 추가합니다.
        requestSessionId();
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/userList', function (userList) {
            showUserList(JSON.parse(userList.body));
        });
        stompClient.subscribe('/queue/challenge', function (challenge) {
            console.log("asfsafsa");
            handleChallenge(JSON.parse(challenge.body).challenger);
        });
        stompClient.send("/app/requestUsers");
    });
}



function handleChallenge(challenger) {
    alert(challenger + " has challenged you!");
}




function showUserList(userList) {
    let userListDiv = $('#userList');
    userListDiv.html('');
    userList.forEach(nick => {
        let nickElement = $('<button>').text(nick).addClass('user-button').click(function(event) {
            // showUserOptions(nick, event);
        });
        userListDiv.append(nickElement);
    });
}

function showUserOptions(nick, event) {
    let userOptions = $('<div>').addClass('user-options');
    let challengeButton = $('<button>').text('대결 신청').addClass('option-button').click(function(event) {
        challengeUser(nick);
        // event.stopPropagation();  // 이벤트 전파를 멈춤
    });

    userOptions.append(challengeButton);
    $('body').append(userOptions);  // HTML 문서에 팝업 추가


    // 클릭한 버튼의 위치와 너비를 가져와 팝업의 위치 설정
    let offset = $(event.target).offset();


    // 팝업을 가운데에 위치시키고 버튼 위에 표시
    userOptions.css({
        top: offset.top,
        left: offset.left
    });

    // 팝업 외부를 클릭하면 팝업 제거
    setTimeout(() => {
        $(document).on('click', function removePopup() {
            userOptions.remove();
            $(document).off('click', removePopup);
        });
    }, 0);

    // 팝업 내부를 클릭하면 이벤트 전파를 멈춤(팝업이 사라지지 않도록)
    userOptions.click(function(event) {
        event.stopPropagation();
    });
}




function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}




function sendMessage(event) {
    event.preventDefault();

    const now = new Date();
    const currentTime = now.getHours().toString().padStart(2, '0') + ":"
        + now.getMinutes().toString().padStart(2, '0') + ":"
        + now.getSeconds().toString().padStart(2, '0');

    const messageData = {
        'nick': nick,
        'message': $("#message").val(),
        'time': currentTime
    };

    stompClient.send("/app/hello", {}, JSON.stringify(messageData));
    $("#message").val("");
}

function showGreeting(message) {
    $("#greetings").append("<div style='margin-top: 10px'>" + message + "</div>");
}



window.addEventListener("beforeunload", function(e) {
    disconnect();
});

