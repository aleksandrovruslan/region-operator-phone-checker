$(function () {
    var count = 0;
    $word_search = $('#word_search');
    $result_panel = $('#result_panel');

    $(document).on('click', '#search_button', function () {
        $result_panel.empty();
        $result_panel.attr('class', 'alert alert-success');
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
        var temp = $('<div class="row" style="border: 1px solid #000"></div>');
        var region;

        regions.sort(function (a, b) {
            var r = 0;
            if (a.id > b.id) { r = 1; }
            if (a.id < b.id) { r = -1; }
            return r;
        })

        regions.forEach(function (entry) {
            region = temp.clone();
            region.append('<div class="col-11">');
            region.append(entry.id);
            region.append(' ' + entry.name + ' ' + entry.timeZoneUTC + ' ');
            region.append('</div>');
            region.append('<input type="text" class="col-1"/>');
            $result_panel.append(region);
        })

        if (regions.length > 0) {
            $result_panel.prepend('<div class="row" style="border: 1px solid #000"><div class="col-1">id</div><div class="col-11">region</div></div>')
        } else {
            $result_panel.append('Regions not found.');
        }
    }
})