$(function () {
    $word_search = $('#word_search');
    $result_panel = $('#result_panel');

    $(document).on('click', '#search_button', function () {
        $result_panel.empty();
        searchRegions($word_search.val());
    });
    
    function searchRegions(searchString) {
        $.ajax({
            url:'../api/v1/admin/region/search/' + searchString,
            type:'GET',
            dataType:'json',
            success : function(result) {
                fillRegions(result);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    function fillRegions(regions) {
        $regions_table = $('#regions_table');
        $regions_table = '<table id="regions_table" class="table table-striped table-bordered" style="width:100%">';
        $regions_table += '<thead><tr><td>id</td><td>region</td><td>time UTC</td></tr></thead>';
        $result_panel.append($regions_table);
        $('#regions_table').DataTable({
            data: regions,
            columns: [
                {data: 'id'},
                {data: 'name'},
                {data: 'timeZoneUTC'}
            ]
        });
    }
})