$(function(){
    var fullNumber;
    var numberJSON;
    var post;
    var comments;
    var regNumber = new RegExp('^[0-9]{10}$');
    var incorrectPhoneSet;

    $phone = $('#phone');
    $search_phone = $('#search_phone');
    $result_form = $('#result_form');
    $phone_result = $('#phone_result');
    $region_result = $('#region_result');
    $operator_result = $('#operator_result');
    $multi_search_phone = $('#multi_search_phone');
    $result_table = $('#result_table');
    $numberTime = $('#numberTime');
    $comments_point = $('#comments_point');

    $(document).on('click', '#one_search', function () {
        $multi_search_phone.hide();
        $result_table.hide();
        $search_phone.show();
    });

    $(document).on('click', '#many_search', function () {
        $multi_search_phone.show();
        $result_table.show();
        $search_phone.hide();
        $result_form.hide();
    });

    $(document).on('click', '#check_button', function () {
        checkNumber();
    });

    $phone.on('keydown', function (e) {
        if (e.which === 13 || e.keyCode === 13) {
            checkNumber();
            return false;
        }
    });

    $(document).on('click', '#set_comment', function(){
        if (!(extractPhoneNumber($phone.val().trim()))) {
            $phone.attr('class', 'form-control is-invalid');
            $phone.focus();
            return false;
        }
        if (!extractComment()) {
            return false;
        }
        $comments_point.empty();

        $.ajax({
            type : 'POST',
            url: '../api/v1/post/add/',
            contentType : 'application/json',
            data : post,
            dataType:'json',
            success : function(data) {
                fillComments(data.posts);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    });

    function checkNumber() {
        $comments_point.empty();
        if (!(extractPhoneNumber($phone.val().trim()))) {
            $result_form.hide();
            $phone.attr('class', 'form-control is-invalid');
            $phone.focus();
            return false;
        }
        $phone.attr('class', 'form-control is-valid');

        $.ajax({
            type : 'GET',
            url: '../api/v1/numbers/' + fullNumber,
            dataType : 'json',
            success : function(data) {
                fillResult(data[0]);
                fillComments(data[0].posts);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    function extractPhoneNumber(rawNumber) {
        fullNumber = rawNumber.replace(/[^0-9]/g, '');
        if (fullNumber.length === 11) {
            fullNumber = fullNumber.substring(1, 11);
        }
        if (!(regNumber.test(fullNumber))) {
            return false;
        }
        numberJSON = '{"prefix":"' + fullNumber.substr(0, 3) +
            '","number":"' + fullNumber.substr(3, 8) + '"}';
        return true;
    }

    function extractComment() {
        $text_comment = $('#text_comment');
        $userName = $('#userName');
        var name = '';
        var comment = $text_comment.val().trim();
        if (comment === '') return false;
        if ($userName.val().trim() !== '') {
            name = ',"userName":"' + $userName.val().trim() + '"';
        }
        post = '{"comment":"' + comment + '","phoneNumber":' +
            numberJSON + name + '}';
        $text_comment.val('');
        $userName.val('');
        return true;
    }

    function fillResult(phoneNumber) {
        comments = '';
        $result_form.show();
        $phone_result.text('Телефонный номер: +7' + phoneNumber.fullNumber);
        if (phoneNumber.region === null) {
            phoneNumber.region = "регион не определен.";
        }
        $region_result.text('Регион: ' + phoneNumber.region);
        if(phoneNumber.operator === null) {
            phoneNumber.operator = "оператор не определен.";
        }
        $operator_result.text('Оператор: ' + phoneNumber.operator);
        correctTime(phoneNumber);
        if (phoneNumber.serverTime !== null) {
            $numberTime.text('Местное время абонента: ' + phoneNumber.serverTime);
        } else {
            $numberTime.text('Местное время абонента: не определено');
        }
    }

    function correctTime(phoneNumber) {
        if (phoneNumber.timeZoneUTC >= 2 && phoneNumber.timeZoneUTC <= 12) {
            var time = new Date(phoneNumber.serverTime);
            var timeZoneUTC = phoneNumber.timeZoneUTC;
            time.setHours(time.getHours() - 3 + timeZoneUTC);
            phoneNumber.serverTime = time.toLocaleTimeString('ru');
        } else {
            phoneNumber.serverTime = null;
        }
        return phoneNumber;
    }

    function fillComments(posts) {
        var comment;
        var date;

        posts.sort(function (a, b) {
            var r = 0;
            if (a.dateTime > b.dateTime) { r = 1; }
            if (a.dateTime < b.dateTime) { r = -1; }
            return r;
        });

        posts.forEach(function (entry) {
            comment = $('<div class="alert alert-info" role="alert"><br></div>');
            date = new Date(Date.parse(entry.dateTime));
            comment.prepend(date.toLocaleString('ru'));
            comment.prepend('<b>' + entry.userName + '</b> ');
            comment.append(entry.comment);
            $comments_point.prepend(comment);
        });
    }

    //Check many numbers
    $(document).on('click', '#check_numbers', function () {
        var phones = $('#phones').val().split('\n');
        var phoneSet = new Set();
        incorrectPhoneSet = new Set();
        var phoneNumbers = '';
        phones.forEach(function (entry) {
            if (extractPhoneNumber(entry)) {
                phoneSet.add(fullNumber);
            }else {
                incorrectPhoneSet.add(JSON.parse('{"fullNumber":"' + entry + '"}'));
            }
        });
        phoneSet.forEach(function (entry) {
            phoneNumbers += (entry + ',');
        });
        checkPhonesCommons(phoneNumbers);
    });

    function checkPhonesCommons(phoneNumbers) {
        $.ajax({
            type : 'GET',
            url: '../api/v1/numbers/' + phoneNumbers,
            dataType : 'json',
            success : function(data) {
                $result_table.empty();
                fillNumbers(data);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    function fillNumbers(numbers) {
        numbers.forEach(function (entry) {
            correctTime(entry);
        });
        incorrectPhoneSet.forEach(function (entry) {
            numbers.push(entry);
        });

        function negativeValueRenderer(instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);

            if (!value || value === '') {
                td.value = 'нет данных';
                td.className = 'make-me-red';
            }
        }

        Handsontable.renderers.registerRenderer('negativeValueRenderer', negativeValueRenderer);

        var container = document.getElementById('result_table');
        var hot = new Handsontable(container, {
            data: numbers,
            rowHeaders: true,
            colHeaders: true,
            filters: true,
            dropdownMenu: true,
            columnSorting: true,
            // colWidths: [100, 223, 155, 80, 40],
            // rowHeights: 55,
            contextMenu: true,
            colHeaders: [
                'Номер',
                'Регион',
                'Оператор',
                'Время',
                'UTC'
            ],
            columns: [
                {
                    data: 'fullNumber'
                },
                {
                    data: 'region'
                },
                {
                    data: 'operator'
                },
                {
                    data: 'serverTime',
                    type: 'time',
                    timeFormat: 'HH:mm:ss'
                },
                {
                    data: 'timeZoneUTC'
                }
            ],
            cells: function (row, col, prop,value) {
                var cellProperties = {};
                cellProperties.renderer = firstRowRenderer;
                return cellProperties;
            }
        });

        function firstRowRenderer(instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if(!value || value === '' || value == null ) {
                td.innerHTML = "нет данных";
                td.style.color = 'red';
            }
        }
    }
});