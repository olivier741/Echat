$(function () {
    /**
     * Login button click event
     */
    $("#loginBtn").click(function () {
        console.log("into click loginBtn");
        var replyUrl = $("#replayLoginUrl").val().trim();
        var username = $("#u").val().trim();
        var password = $("#p").val().trim();
        if (username.length == 0 || password.length == 0) {
            sweetAlert("Ouch...something went wrong...", "The username or password is empty, please fill in!", "error");
            return;
        }
        /**
         * Return data type
         * {successed ： true | false} Whether the login is successful
         * {errStatus: 1 |　2 | 3} When successed is false, there will be errStatus error status information
         * Among them 1: The username has not been registered
         * 2: The password is wrong
         * 3: The username or password is empty (to prevent third-party plug-ins from logging in)
         */
        $.ajax({
            type: "POST",
            url: replyUrl,
            data: JSON.stringify({'name': username,'password': password}),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                var successed = data.successed;
                if (successed) {
                    $("#login_form").submit();
                } else {
                    var errStatus = data.errStatus;
                    if (errStatus == 1) {
                        sweetAlert("Ouch...something went wrong...", "The username has not been registered, please go to register~", "error");
                        return;
                    } else if (errStatus == 2) {
                        sweetAlert("Ouch...something went wrong...", "The password is wrong! Please fill in again~", "error");
                        $("#p").focus();
                    } else if (errStatus == 3) {
                        sweetAlert("Ouch...something went wrong...", "Username or password is not filled in~", "error");
                        returne
                    } else {
                        sweetAlert("Oh... something went wrong...", "An unknown error occurred on the server~", "error");
                        return;
                    }
                }
            }
        });
    });

    /**
     * The user name input box loses focus event, ajax judges whether the name has been registered
     */
    $("#user").blur(function () {
        var userName = $("#user").val().trim();
        var sendData = JSON.stringify({'name': userName});
        if (userName.length != 0) {
            /**
             * Return data type
             * {successed ： true | false} Whether the registration is successful
             * {errStatus: 1} Username has been registered

             */
            $.ajax({
                type: "POST",
                url: $("#replyRegistUrl").val().trim(),
                data: sendData,
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log(data);
                    if (data.successed) {
                        $('#user').css({
                            border: "1px solid #D7D7D7",
                            boxShadow: "none"
                        });
                        $("#userCue").html("Welcome to register for EastChat!");
                    }else if(data.errStatus == 1){
                        $('#user').focus().css({
                            border: "1px solid red",
                            boxShadow: "0 0 2px red"
                        });
                        $('#userCue').html("<font color='red'><b>The username has already been registered!</b></font>");
                    }
                }
            })
        }
        $('#user').css({
            border: "1px solid #D7D7D7",
            boxShadow: "none"
        });
        $("#userCue").html("Welcome to register for EastChat!");
    });

    /**
     * Register button click event
     */
    $("#reg").click(function () {
        var userName = $("#user").val().trim();
        var pwd1 = $("#passwd").val().trim();
        var pwd2 = $("#passwd2").val().trim();
        if (userName.length == 0 || pwd1.length == 0 || pwd2.length == 0){
            sweetAlert("Ouch...something went wrong...", "You still have some content missing~", "error");
            return;
        }else if (pwd1 != pwd2){
            sweetAlert("Ouch...something went wrong...", "The two password entries are inconsistent, please fill in again~", "error");
        }else {
            var sendData = JSON.stringify({'name': userName,'password': pwd1});
            $.ajax({
                type: "POST",
                url: $("#replyRegistUrl").val().trim(),
                data: sendData,
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    if (data.successed){
                        sweetAlert("Registration is successful!", "Go log in~ Join the chat~", "success");
                    }else {
                        sweetAlert("Oh... something went wrong...", "Registration failed, please try again~", "error");
                    }
                }
            });
        }

    });
    $('#switch_qlogin').click(function () {
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left: '0px', width: '70px'});
        $('#qlogin').css('display','none');
        $('#web_qr_login').css('display','block');

    });
    $('#switch_login').click(function () {

        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left: '154px', width: '70px'});

        $('#qlogin').css('display','block');
        $('#web_qr_login').css('display','none');
    });
    if (getParam("a") == '0') {
        $('#switch_login').trigger('click');
    }

});

function logintab() {
    scrollTo(0);
    $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
    $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
    $('#switch_bottom').animate({left: '154px', width: '96px'});
    $('#qlogin').css('display','none');
    $('#web_qr_login').css('display','block');

}


//Acquire the parameter according to the parameter name pname is equal to the desired parameter name
function getParam(pname) {
    var params = location.search.substr(1); // Get parameters and remove them?
    var ArrParam = params.split('&');
    if (ArrParam.length == 1) {
        //Only one parameter
        return params.split('=')[1];
    }
    else {
        //The case of multiple parameters
        for (var i = 0; i <ArrParam.length; i++) {
            if (ArrParam[i].split('=')[0] == pname) {
                return ArrParam[i].split('=')[1];
            }
        }
    }
}


