$(function () {
    refreshAddress();
    // setInterval(function () {
    //     refreshAddress();
    // }, 3000);
    // after();
    $("ul#address").on("click", "li", function () {      //只需要找到你点击的是哪个ul里面的就行
        $(this).addClass("active").siblings().removeClass("active");
        address_id = $(this).attr('id');
        getTokensByaddressId(address_id)
        initTradeViewAndPrice()
    });
});
var address_id = 0;

var i = 5;

function after() {
    if (i < 0) {
        refreshAddress();
        i = 5;
    }
    $("#add_num").empty().append("(" + i + ")");
    i = i - 1;
    setTimeout(function () {
        after();
    }, 1000);
}

function refreshAddress() {
    $.ajax({
        url: "/address",
        type: "GET",
        cache: false,
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            $("#address").find("li").remove()
            for (var i = 0; i < result.length; i++) {
                var add = result[i]
                if (i == 0) {
                    $("#address").append("<li class='active' id=" + add.id + "><a href='#'>" + add.address + "</a></li>")
                } else {
                    $("#address").append("<li id=" + add.id + "><a href='#'>" + add.address + "</a></li>")
                }
            }
        },
        error: function (result) {
        }
    });
}

function getPrice(token) {
    $.ajax({
        url: "/price/" + token,
        type: "GET",
        cache: false,
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            $(".symbol").text(result.symbol)
            $("#price_usd").text(result.price_usd)
            $("#price_btc").text(result.price_btc)
            $("#24h_volume_usd").text(result.twenty_forth_volume_usd)
            $("#market_cap_usd").text(result.market_cap_usd)
            $("#available_supply").text(result.available_supply)
            $("#total_supply").text(result.total_supply)
            var change = result.percent_change_twenty_forth_h
            if (change < 0) {
                $("#sco").css("color", "#CE0000")
            } else {
                $("#sco").css("color", "#28FF28")
            }
            $("#percent_change_24h").text(result.percent_change_twenty_forth_h + '%')
        },
        error: function (result) {
        }
    });
}

function getTokensByaddressId(address_id) {
    $("#slpk").find("option").not(":first").remove();
    $.ajax({
        url: "/wallet/tokens/" + address_id,
        type: "GET",
        cache: false,
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            if (result.length > 0) {
                $.each(result, function (i) {
                    $('#slpk').append("<option value=" + result[i] + ">" + result[i] + "</option>");
                });
            }
            $('#slpk').selectpicker('refresh');
        },
        error: function (result) {
        }
    });
}

function selectOnchang(obj) {
    var token = obj.options[obj.selectedIndex].value;
    getPrice(token)

    initEchart(token)

}
function initTradeViewAndPrice() {
    $(".symbol").text(0)
    $("#price_usd").text(0)
    $("#price_btc").text(0)
    $("#24h_volume_usd").text(0)
    $("#market_cap_usd").text(0)
    $("#available_supply").text(0)
    $("#total_supply").text(0)
    $("#percent_change_24h").text('')


}
function drawView(xAxisData, seriesList) {
    $("#timeTr").find("td").not(":first").remove();
    $("#in").find("td").not(":first").remove();
    $("#out").find("td").not(":first").remove();
    xAxisData.forEach(function (date) {
        $("#timeTr").append("<td>" + date + "</td>")
    })
    for (var i = 0; i < seriesList.length; i++) {
        seriesList[i].data.forEach(function (d) {
            $("#" + (i == 0 ? "in" : "out")).append("<td>" + d.toLocaleString() + "</td>")
        })
    }

}

function initEchart(symbol) {

    var myChart = echarts.init(document.getElementById('echart'));
    option = {
        title: {
            text: symbol + ' 币转入转出图'
        },
        tooltip: {},
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data: ['转入', '转出']
        },
        xAxis: {
            type: "category",
            data: []
        },
        yAxis: [{
            type: "value",
            axisLabel: {
                margin: 2,
                formatter: function (value, index) {
                    if (value >= 10000 && value < 10000000) {
                        value = value / 10000 + "万";
                    } else if (value >= 10000000) {
                        value = value / 10000000 + "千万";
                    }
                    return value;
                }
            }
        }],

        series: [
            {
                name: "转入",
                type: "bar",
                data: []
            },
            {
                name: "转出",
                type: "bar",
                data: []
            }
        ]
    };
    myChart.showLoading();
    $.ajax({
        type: "get",
        async: true, //异步执行
        url: "chart?" + "address_id=" + address_id + "&symbol=" + symbol,
        dataType: "json", //返回数据形式为json
        success: function (json) {
            myChart.hideLoading();
            myChart.setOption({
                xAxis: {
                    data: json.xAxisData
                },
                series: json.seriesList,
            });
            drawView(json.xAxisData, json.seriesList)
        },
        error: function (errorMsg) {
        }
    });
    myChart.setOption(option);
}

function formatNum(str){
    var newStr = "";
    var count = 0;

    if(str.indexOf(".")==-1){
        for(var i=str.length-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr;
            }
            count++;
        }
        str = newStr + ".00"; //自动补小数点后两位
        console.log(str)
    }
    else
    {
        for(var i = str.indexOf(".")-1;i>=0;i--){
            if(count % 3 == 0 && count != 0){
                newStr = str.charAt(i) + "," + newStr;
            }else{
                newStr = str.charAt(i) + newStr; //逐个字符相接起来
            }
            count++;
        }
        str = newStr + (str + "00").substr((str + "00").indexOf("."),3);
        console.log(str)
    }
}
