$(function () {
    $region_search = $('#region_search');
    $result_panel = $('#result_panel');

    $(document).on('click', '#search_button', function () {
        searchRegions($region_search.val());
    });
    
    $(document).on('keydown', '#region_search', function (e) {
        if (e.which === 13 || e.keyCode === 13) {
            searchRegions($region_search.val());
            return false;
        }
    });
    
    function searchRegions(searchString) {
        $result_panel.empty();
        $.ajax({
            type:'GET',
            url:'../api/v1/admin/region/search/' + searchString,
            dataType:'json',
            success : function(data) {
                fillRegions(data);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    function fillRegions(regions) {
        $result_panel.append('<p><button class="btn btn-primary" id="save">Save</button></p>');
        var container = document.getElementById('result_panel');
        var hot = new Handsontable(container, {
            data: regions,
            rowHeaders: true,
            colHeaders: true,
            filters: true,
            dropdownMenu: true,
            columnSorting: true,
            contextMenu: true,
            colHeaders: [
                'id',
                'Region',
                'UTC'
            ],
            columns: [
                {
                    readOnly: true,
                    data: 'id'
                },
                {
                    readOnly: true,
                    data: 'name'
                },
                {
                    readOnly: false,
                    data: 'timeZoneUTC'
                }
            ]
        });

        //TODO fix adding only changed data
        $('#save').on('click', function () {
            var rawData = hot.getData();
            rawData.forEach(function (entry) {
                var region = JSON.stringify({"id": entry[0], "name": entry[1],"timeZoneUTC": entry[2]});
                $.ajax({
                    type: 'PUT',
                    url: '../api/v1/admin/region/' + entry[0],
                    data: region,
                    contentType : 'application/json',
                    success: function (data) {
                        console.log(data);
                    },
                    error: function (xhr, resp, text) {
                        console.log('Save error.');
                        console.log(xhr, resp, text);
                    }
                });
            })
        });
    }
});