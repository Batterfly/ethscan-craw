$(function () {
    refreshAddress();
    // setInterval(function () {
    //     refreshAddress();
    // }, 3000);
    after();
});

var i=5;
function after(){
    if(i<0){
        refreshAddress();
        i=5;
    }
    $("#add_num").empty().append("("+i+")");
    i=i-1;
    setTimeout(function(){
        after();
    },1000);
}

function refreshAddress() {
    $.ajax({
        url: "/address",
        type: "GET",
        cache: false,
        dataType: "json",
        contentType: "application/json",
        // data: JSON.stringify(data),
        success: function (result) {
            $("#address").find("li").remove()
            for (var i = 0; i < result.length; i++) {
                var add = result[i]
                if(i==0){
                    $("#address").append("<li class='active' id="+add.id+"><a href='#'>"+add.address+"</a></li>")
                }else{
                    $("#address").append("<li id="+add.id+"><a href='#'>"+add.address+"</a></li>")
                }
            }
        },
        error: function (result) {
        }
    });
}

/**
 * 保存业务总结
 */
function saveBusinessEvaluate() {
    $('#business-evaluate-btn').attr('disabled', 'disabled');
    var summaryId = $('#summaryId').val();
    var data = [];
    $('#business-evaluate-table').find('tbody').find('tr').each(function (index, dom) {
        var businessEvaluate = {};
        businessEvaluate.completeSituation = $(dom).find('.complete-situation').val();
        var id = $(dom).find('.id').val();
        if (id && id != '') {
            businessEvaluate.id = id;
        }
        businessEvaluate.summaryDetailId = $(dom).find('.summary-detail-id').val();
        businessEvaluate.selfEvaluate = $(dom).find('.business-self-evaluate').val();
        var superiorEvaluate = $(dom).find('.business-superior-evaluate').val();
        if (!superiorEvaluate || superiorEvaluate === '') {
            businessEvaluate.superiorEvaluate = 0;
        } else {
            businessEvaluate.superiorEvaluate = superiorEvaluate;
        }
        businessEvaluate.additionalCase = $(dom).find('.additional-case').val();
        data.push(businessEvaluate);
    });
    if (data.length <= 0) {
        layer.msg('暂无评价项目', {icon: 5});
        return;
    }
    $.ajax({
        url: "/year/evaluate/summary/" + summaryId + "?isSelfEvaluate=true",
        type: "PUT",
        cache: false,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (result) {
            layer.msg('总结评价保存成功', {icon: 1}, function () {
                document.location.reload();
            });
        },
        error: function (result) {
            layer.msg('总结评价保存失败', {icon: 7});
        }
    });
}

