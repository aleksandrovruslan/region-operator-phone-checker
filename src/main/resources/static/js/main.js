$(function(){
    var fullNumber;
    var prefix;
    var number;
    var numberJSON;
    var post;
    var comments;
    var regNumber = new RegExp('^[0-9]{10}$');

    $phone = $('#phone');
    $result_form = $('#result_form');
    $region_result = $('#region_result');
    $operator_result = $('#operator_result');
    $comments_point = $('#comments_point');

    $(document).on('click', '#check_button', function () {
        if (!(extractPhoneNumber())) {
            return false;
        }
        checkNumber();
    });

    $(document).on('click', '#set_comment', function(){
        if (!(extractPhoneNumber())) {
            return false;
        }
        extractComment();
        $comments_point.empty();

        $.ajax({
            url: '../api/v1/post/add/',
            type : 'POST',
            contentType : 'application/json',
            data : post,
            success : function(xhr, resp, text) {
                fillComments(text.responseJSON.posts);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    });

    function checkNumber() {
        $comments_point.empty();

        $.ajax({
            url: '../api/v1/number/' + fullNumber,
            type : 'GET',
            dataType : 'json',
            success : function(xhr, resp, text) {
                fillResult(text.responseJSON);
                fillComments(text.responseJSON.posts);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    function extractPhoneNumber() {
        fullNumber = $phone.val().trim();
        if (!(regNumber.test(fullNumber))) {
            $phone.focus();
            console.warn('incorrect number');
            return false;
        }
        prefix = fullNumber.substr(0, 3);
        number = fullNumber.substr(3, 8);
        numberJSON = '{"prefix":"' + prefix +
            '","number":"' + number + '"}';
        return true;
    }

    function extractComment() {
        $text_comment = $('#text_comment');
        $userName = $('#userName');
        var name = '';
        if ($userName.val() !== '') {
            name = ',"userName":"' + $userName.val() + '"';
        }
        post = '{"comment":"' + $text_comment.val() + '","phoneNumber":' +
            numberJSON + name + '}';
        $text_comment.val('');
        $userName.val('');
    }

    function fillResult(phoneNumber) {
        comments = '';
        $result_form.show();
        $('#phone_result').text('Телефонный номер: +7(' +
            phoneNumber.prefix + ')' + phoneNumber.number);
        if (phoneNumber.region === null) {
            phoneNumber.region = "регион не определен.";
        }
        $region_result.text('Регион: ' + phoneNumber.region);
        if(phoneNumber.operator === null) {
            phoneNumber.operator = "оператор не определен.";
        }
        $operator_result.text('Оператор: ' + phoneNumber.operator);
        var time = new Date(phoneNumber.serverTime);
        var timeZoneUTC = phoneNumber.timeZoneUTC;
        if (timeZoneUTC !==null) {
            time.setHours(time.getHours() - 3 + timeZoneUTC);
            $('#numberTime').text('Местное время абонента: ' + time.toLocaleTimeString('ru'));
        }
    }

    function fillComments(postsList) {
        var posts = Array.from(postsList);
        var comment;
        var date;

        posts.sort(function (a, b) {
            var r = 0;
            if (a.dateTime > b.dateTime) { r = 1; }
            if (a.dateTime < b.dateTime) { r = -1; }
            return r;
        })

        posts.forEach(function (entry) {
            comment = $('<div class="alert alert-info" role="alert"><br></div>');
            date = new Date(Date.parse(entry.dateTime));
            comment.prepend(date.toLocaleString('ru'));
            comment.prepend(" ");
            comment.prepend("<b>" + entry.userName + "</b>");
            comment.append(entry.comment);
            $comments_point.prepend(comment);
        });
    }
});