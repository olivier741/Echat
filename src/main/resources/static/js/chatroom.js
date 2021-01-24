var stompClient = null;
$(function () {
    connect();
    /**
     * Keyboard enter event, used to send messages
     */
    $("#messageInput").bind("keyup", function (event) {
        if (event.keyCode == 13){
            sendMessage();
        }
    });

    /**
     * Clear all content of the chat window
     */
    $("#clearBtn").click(function () {
        $("#historyMsg").empty();
        $("#messageInput").focus();
    });

    /**
     * Upload picture and send
     */
    $("#sendImage").bind("change", function () {
        if (this.files.length != 0){
            $.ajax({
                url: $("#uploadUrl").val(),
                type:'POST',
                cache: false,
                data: new FormData($('#sendImageForm')[0]),
                processData: false,
                contentType: false
            }).done(function(res) {
                console.log(res);
            }).fail(function(res) {
                console.log(res);
            });
        }
    });
    initEmoji();
    $("#sendImageBtn").click(function () {
        $("#sendImage").trigger("click");
    })

});
/**
 * Pre-loaded emoji pictures
 */
function initEmoji() {
    var emojiContainer = $("#emojiWrapper");
    var documentFragment = document.createDocumentFragment();
    for (var i = 69; i> 0; i--){
        var emojiItem = document.createElement("img");
        emojiItem.src = $("#emojiBaseUri").val().trim() + i + ".gif";
        emojiItem.title = i;
        documentFragment.appendChild(emojiItem);
    }
    emojiContainer.append(documentFragment);

    $("#emoji").click(function (event) {
        emojiContainer.css("display", "block");
        event.stopPropagation(); //Prevent the delivery of events and prevent the body from listening
    });

    $("body").click(function (event) {
        if (event.target != emojiContainer){
            emojiContainer.css("display", "none");
        }
    });
    
    $("#emojiWrapper").click(function (event) {
        var target = event.target;
        if (target.nodeName.toLowerCase() == "img"){
            var messageInput = $("#messageInput");
            messageInput.val(messageInput.val() + "[EMOJI:" + target.title + "]");
            messageInput.focus();
        }
    })
    
}
/**
 * The client connects to the server websocket
 * And subscribe to a series of channels to receive different kinds of messages
 * /app/chat/participants: The message of the current number of people online will only be received once
 * /topic/login: Message of new login user
 * /topic/chat/message: chat content message
 * /topic/logout: The user offline message
 * The server sends back a json instance {"userName":"chris","sendDate":1494664021793,"content":"hello","messageType":"text"}
 * messageType is divided into: text and image
 */
function connect() {
    var socket = new SockJS($("#websocketUrl").val().trim());
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        stompClient.subscribe("/app/chat/participants", function (message) {
            showActiveUserNumber(message.body);
            var user = "System Message";
            var date = null;
            var msg = $("#myName").val() + " Join the chat!";
            showNewMessage(user, date, msg);
        });
        stompClient.subscribe("/topic/login", function (message) {
            showNewUser(message.body);
        });
        stompClient.subscribe("/topic/chat/message", function (message) {
            var json = JSON.parse(message.body);
            var messageType = json.messageType;
            var user = json.userName;
            var date = json.sendDate;
            var msg = json.content;
            if (messageType == "text"){
                showNewMessage(user, date, msg);
            }else if (messageType == "image"){
                showNewImage(user, date, msg);
            }

        })
        stompClient.subscribe("/topic/logout", function (message) {
            showUserLogout(message.body);
        })

    });
}
/**
 * Show user offline messages
 * @param message
 */
function showUserLogout(message) {
    var json = JSON.parse(message);
    var logoutUser = json.name;
    var date = json.logoutDate;
    var user = "System Message";
    var msg = logoutUser + "Leave the chat room~";
    showNewMessage(user, date, msg);
    showSubActiveUserNumber();
}
/**
 * Display the message of new user login
 * @param message
 */
function showNewUser(message) {
    var json = JSON.parse(message);
    var newUser = json.name;
    var date = json.loginDate;
    var user ='System Message';
    var msg = newUser + "Join the chat!";
    showNewMessage(user, date, msg);
    showAddActiveUserNumber();

}
/**
 * Display the current online number
 * @param number
 */
function showActiveUserNumber(number) {
    $("#status").text(number);
}
/**
 * The number of online users plus 1
 */
function showAddActiveUserNumber() {
    var number = parseInt($("#status").text());
    number = number + 1;
    $("#status").text(number);
}
/**
 * The number of online minus 1
 */
function showSubActiveUserNumber() {
    var number = parseInt($("#status").text());
    number = number-1;
    $("#status").text(number);
}
/**
 * Format the time, the parameter is null to display the current client time
 * @param dateTime
 * @returns {string}
 */
function formatDate(dateTime) {
    var date = dateTime == null ? new Date() : new Date(dateTime);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    hour = hour < 10  ? '0'+""+hour : hour;
    var minute = date.getMinutes();
    minute = minute < 10 ? '0'+""+minute : minute;
    var second = date.getSeconds();
    second = second < 10 ? '0'+""+second : second;
    return year + "-" + month + "-" + day +" " + hour + ":" + minute + ":" + second;
}

/**
 * Show new message
 * @param user The user who sent the message or ‘system message’
 * @param date The time when the message was sent (unformatted)
 * @param msg message content
 */
function showNewMessage(user, date, msg) {
    var container = document.getElementById("historyMsg");
    var msgToDisplay = document.createElement('p');
    if (user == "system message"){
        msgToDisplay.style.color ='red';
    }
    var dateTime = formatDate(date);
    msg = showEmoji(msg);
    msgToDisplay.innerHTML ='<span class="timespan">' + dateTime +'</span><br/>[' + user + "]: "+ msg;
    container.append(msgToDisplay);
    container.scrollTop = container.scrollHeight;
}
/**
 * Regular expressions to display emoji pictures in messages
 * @param message
 * @returns {*} Return the message after adding emoji picture tag
 */
function showEmoji(message) {
    var result = message,
        regrex = /\[EMOJI:\d+\]/g,
        match;
    while (match = regrex.exec(message)){
        var emojiIndex = match[0].slice(7, -1);
        var emojiUrl = $("#emojiBaseUri").val().trim() + emojiIndex + ".gif";
        result = result.replace(match[0],'<img src="' + emojiUrl +'"/>');
    }
    return result;
}

/**
 * Display pictures sent by users
 * @param user username
 * @param date The time sent by the user (unformatted)
 * @param url picture url
 */
function showNewImage(user, date, url) {
    var container = document.getElementById("historyMsg");
    var msgToDisplay = document.createElement('p');
    var dateTime = formatDate(date);
    msgToDisplay.innerHTML ='<span class="timespan">' + dateTime +'</span><br/>[' + user +']: <br/>' +
        '<img class="img-thumbnail" src="' + url +'"/>';
    container.append(msgToDisplay);
    container.scrollTop = container.scrollHeight;
}
/**
 * Send the information in the input box
 */
function sendMessage() {
    var content = $("#messageInput").val();
    if (content.trim().length != 0){
        $("#messageInput").val('');
        stompClient.send("/app/chat/message", {}, JSON.stringify({
            'userName': $("#myName").val(),
            'content': content
        }));
    }
}