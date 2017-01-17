Xy.Module01 = {
    status: 'stopped'
};
Xy.Module02 = {};
Xy.Module03 = {};
Xy.Module04 = {};
Xy.Module05 = {};
Xy.Module06 = {};
Xy.Module07 = {};
Xy.Module08 = {};

function generateChartData() {

}

// function generateChartData() {
//     var chartData = [];
//     var firstDate = new Date();
//     firstDate.setDate(firstDate.getDate() - 100);
//
//     var day = 1000 * 60 * 60 * 24;
//     var now = new Date();
//
//
//
//     for (var i = 30; i > 0; i--) {
//         var tempDate = new Date(now.getTime() - i * day);
//         chartData.push({
//             date: tempDate.getMonth() + '-' + tempDate.getDate(),
//             cdrc: randomNumber(150, 250)
//         });
//     }
//     return chartData;
// }


Xy.Module01.refresh = function () {

    var showRunningDays = function () {
        var begin = new Date();
        begin.setFullYear(2015, 11, 20);
        var now = new Date();
        $('#xy-module-01 .xy-days').html(parseInt((now.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24));
    };

    var startClock = function () {
        if (Xy.Module01.status == 'running') {
            return;
        }
        Xy.Module01.status = 'running';

        var weeks = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];

        var $date = $('#xy-module-01 .xy-date');
        var $time = $('#xy-module-01 .xy-time');

        // 格式化
        var formatter = function (n) {
            return n > 9 ? n : '0' + n;
        };

        var currentDay = new Date().getDate();

        var render = function () {
            var now = new Date();
            var dateStr = now.getFullYear() + '年';
            dateStr += formatter(now.getMonth() + 1) + '月';
            dateStr += formatter(now.getDate()) + '日'
            dateStr += '&nbsp;&nbsp;'
            dateStr += weeks[now.getDay()];
            $date.html(dateStr);

            dateStr = formatter(now.getHours()) + '&nbsp;:&nbsp;';
            dateStr += formatter(now.getMinutes()) + '&nbsp;:&nbsp;';
            dateStr += formatter(now.getSeconds());
            $time.html(dateStr);

            if (currentDay < now.getDate()) {
                showRunningDays();
            }
        }
        window.setInterval(render, 1000)
    };

    startClock();
    showRunningDays();
};
Xy.Module02.refresh = function () {
    Xy.requestApi('/operation/get_charge', {owner_id:"null"}, function (data) {
        if (data) {
            // 当日充电电量
            $('#xy-module-02-01').html(data.daily_charge_quantity || 0);
            // 当日充电次数
            $('#xy-module-02-02').html(data.daily_charge_amount || 0);
            // 累计充电电量
            $('#xy-module-02-03').html(data.all_charge_quantity || 0);
            // 累计充电次数
            $('#xy-module-02-04').html(data.all_charge_amount || 0);
        }
    })
};

Xy.Module03.refresh = function () {
    Xy.requestApi('/operation/get_count', {owner_id:"null"}, function (data) {
        if (data) {
            // 充电站数量
            $('#xy-module-03-01').html(data.station_count || 0);
            // 充电桩数量
            $('#xy-module-03-02').html(data.pile_count || 0);
        }
    })
};

Xy.Module04.refresh = function () {
    Xy.requestApi('/operation/get_pile_status', {owner_id:"null"}, function (data) {
        // 1：空闲桩 ，2：只连接未充电，3：充电进行中, 4：GPRS通讯中断,5：检修中,6：预约，7：故障
        // 1: 忙碌, 2：空闲，3：故障，4：离线，5：连接，6：检修，7：预约
        // 索引返回的数据，值页面class序号
        if (data) {
            //此处不进行动态赋值，由于涉及几百个桩的运行状态异常以及脏数据，
            var busyCount = 0 ;//充电中
            var freeCount = 0 ;//空闲
            var wrongCount = 0 ;//故障
            var offLineCount = 0 ;//离线
            var connectCount  = 0 ;//连接未充电
            var repairCount = 0 ;//检修
            var orderCount = 0 ;//预约
            for(var i = 0;i<data.length;i++){
                if(data[i].run_status=="1"){
                    freeCount = freeCount+data[i].count;
                }else if(data[i].run_status=="2"){
                    connectCount = connectCount +data[i].count;
                }else if(data[i].run_status=="3"){
                    busyCount = busyCount +data[i].count;
                }else if(data[i].run_status=="4"){
                    offLineCount = offLineCount +data[i].count;
                }else if(data[i].run_status=="5"){
                    repairCount = repairCount +data[i].count;
                }else if(data[i].run_status=="6"){
                    orderCount = orderCount +data[i].count;
                }else if(data[i].run_status=="7"){
                    wrongCount = wrongCount +data[i].count;
                }else if(data[i].run_status=="8"){
                    busyCount = busyCount +data[i].count;
                }else if(data[i].run_status=="9"){
                    offLineCount = offLineCount +data[i].count;
                }
            }
            $('#xy-module-04 .xy-count-1').html(busyCount);
            $('#xy-module-04 .xy-count-2').html(freeCount);
            $('#xy-module-04 .xy-count-3').html(wrongCount);
            $('#xy-module-04 .xy-count-4').html(offLineCount);
            $('#xy-module-04 .xy-count-5').html(connectCount);
            $('#xy-module-04 .xy-count-6').html(repairCount);
            $('#xy-module-04 .xy-count-7').html(orderCount);
        }
    })
}
Xy.Module05.refresh = function () {


    //  fault_code故障代码;0:急停故障；1:电表故障；2:接触器故障；3：读卡器故障；4:内部过温故障；5:连接器故障；6:绝缘故障；7:其他；
    /**
     * 常规模块信息
     * @param $subModule 子模块
     * @param percentage 电量百分比
     * @param faultCode 故障代码
     */
    var changeSubModule = function($subModule, percentage, faultCode) {
        percentage = percentage || 0;
        $('.xy-progress-bar-center', $subModule).width(percentage / 100 * 630);
        $('.xy-progress-bar-text', $subModule).html(percentage + '%');
        if (faultCode) {
            $('.xy-sub-status-group-4 .xy-value', $subModule).removeClass('xy-dot-green').addClass('xy-dot-red');
            $('.xy-sub-module-icon a img', $subModule).attr('src', Xy.absPath('/static/imgs/device/charging-service-1.png'));
        } else {
            $('.xy-sub-status-group-4 .xy-value', $subModule).removeClass('xy-dot-red').addClass('xy-dot-green');
            $('.xy-sub-module-icon a img', $subModule).attr('src', Xy.absPath('/static/imgs/device/charging-service-2.png'));
        }
    }

    var render = function($module, type,stationId){
        Xy.requestApi('/station/get_alternating_direct', {type: type,stationId:stationId}, function (datas) {
            var count = datas.length > 4 ? 4 : datas.length;
            for (var i = 0; i < count; i++) {
                var $subModule = $('.xy-sub-module-' + (1+i), $module);
                $subModule[0].style.display="";//如果充电桩少于四个的时候，可以正常显示对应数量的充电桩
                var data = datas[i];
                try {
                    $('.xy-sub-module-title', $subModule).html(data.pile_name);
                    $('.xy-value-1', $subModule).html(data.dy || 0);
                    $('.xy-value-2', $subModule).html(data.dl || 0);
                    $('.xy-sub-module-icon a', $subModule).attr('href', Xy.absPath('/pages/monitoring/charging-pile.jsp?id=' + data.id));
                    if (data.status == '1') {
                        $('.xy-value-3', $subModule).html('空闲');
                        $('.xy-value-3', $subModule).css('background-color', '#5bc35d')

                    } else {
                        $('.xy-value-3', $subModule).html('忙碌');
                        $('.xy-value-3', $subModule).css('background-color', '#c72d3a')
                    }

                    // 电量百分比
                    var percentage = data.soc;
                    // 故障代码
                    var faultCode = data.charging_fault.fault_code;

                    changeSubModule($subModule, percentage, faultCode);
                } catch (e) {
                    console.error(e)
                }
            }
        })
    }

    render($('#xy-row-2-left'), 1,getQueryString("stationId"));
    render($('#xy-row-2-right'), 2,getQueryString("stationId"));
}

Xy.Module08.refresh = function () {

    var chart1, chart2;

    var refreshChart1 = function (data) {
        if (!chart1) {
            chart1 = AmCharts.makeChart("xy-chat-1-content", {
                "type": "pie",
                "theme": "light",
                "valueField": "value",
                "titleField": "type",
                "outlineAlpha": 0.4,
                "depth3D": 15,
                "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
                "angle": 30,
                "export": {
                    "enabled": true
                },
                color: "#7eb2e2",
                fontSize: 16,
                innerRadius: 60,
                depth3D: 30
            });
        }
        chart1.dataProvider = data;
        chart1.validateNow();
        chart1.validateData();
        chart1.clickSlice(1);
    }

    var refreshChart2 = function (data) {
        var chartData = [];
        var firstDate = new Date();
        firstDate.setDate(firstDate.getDate() - 100);

        var day = 1000 * 60 * 60 * 24;
        var now = new Date();
        var stationId = getQueryString("stationId");
        Xy.requestApiSync('/station/get_30days_chargetimes', {stationId:stationId}, function (data) {

            if (data) {
                for (var i = 29; i >= 0; i--) {
                    chartData.push({
                        date: data[i].datedays,//data[i].skcdrc||0,
                        skcdrc: data[i].skcdrc,//data[i].skcdrc||0,
                        smcdrc: data[i].smcdrc,//data[i].smcdrc||0,
                        yxdd: data[i].yxdd,
                        cdsr: data[i].cdsr,
                        wjsfy: data[i].wjsfy,
                    });
                }
            }
        })

        if (!chart2) {
            chart2 = AmCharts.makeChart('xy-chat-2-content', {
                type: 'serial',
                theme: "light",
                legend: {
                    color: '#10499C',
                    useGraphSettings: true
                },
                fontSize: 16,
                color: '#7eb2e2',
                synchronizeGrid: true,
                valueAxes: [{
                    id: 'left_value_axes',
                    axisColor: '#FF6600',
                    axisThickness: 2,
                    axisAlpha: 1,
                    position: 'left'
                }, {
                    id: 'right_value_axes',
                    axisColor: '#B0DE09',
                    axisThickness: 2,
                    gridAlpha: 0,
                    axisAlpha: 1,
                    position: 'right'
                }],
                graphs: [{
                    valueAxis: 'left_value_axes',
                    lineColor: '#29BE29',
                    bullet: 'round',
                    bulletBorderThickness: 1,
                    hideBulletsCount: 30,
                    title: '刷卡充电人次',
                    valueField: 'skcdrc',
                    fillAlphas: 0.4
                }, {
                    hidden: true,
                    valueAxis: 'left_value_axes',
                    lineColor: '#fff800',
                    bullet: 'square',
                    bulletBorderThickness: 1,
                    hideBulletsCount: 30,
                    title: '扫码充电人次',
                    valueField: 'smcdrc',
                    fillAlphas: 0.4
                }, {
                    hidden: true,
                    valueAxis: 'left_value_axes',
                    lineColor: '#00b43b',
                    bullet: 'triangleUp',
                    bulletBorderThickness: 1,
                    hideBulletsCount: 30,
                    title: '有效订单',
                    valueField: 'yxdd',
                    fillAlphas: 0.4
                }, {
                    hidden: false,
                    valueAxis: 'right_value_axes',
                    lineColor: '#00eaff',
                    bullet: 'bubble',
                    bulletBorderThickness: 1,
                    hideBulletsCount: 30,
                    title: '充电收入',
                    valueField: 'cdsr',
                    fillAlphas: 0.4
                }, {
                    hidden: true,
                    valueAxis: 'right_value_axes',
                    lineColor: '#ff12ca',
                    bullet: 'triangleLeft',
                    bulletBorderThickness: 1,
                    hideBulletsCount: 30,
                    title: '未结算费用',
                    valueField: 'wjsfy',
                    fillAlphas: 0.4
                }],
                chartScrollbar: {
                    enabled: false
                },
                chartCursor: {
                    cursorPosition: 'mouse'
                },
                categoryField: 'date',
                categoryAxis: {
                    parseDates: false,
                    axisColor: '#DADADA',
                    minorGridEnabled: true
                }
            });
        }
        chart2.dataProvider = chartData;
        chart2.validateNow();
        chart2.validateData();
    }

    // /**
    //  * 刷新图表：近30天营收分析
    //  * @param data 图表数据
    //  */
    // var refreshChart2 = function (data) {
    //     if (!chart2) {
    //         chart2 = AmCharts.makeChart('xy-chat-2-content', {
    //             type: 'serial',
    //             theme: "light",
    //             legend: {
    //                 color: '#10499C',
    //                 useGraphSettings: true
    //             },
    //             fontSize: 16,
    //             color: '#7eb2e2',
    //             synchronizeGrid: true,
    //             valueAxes: [{
    //                 id: 'left_value_axes',
    //                 axisColor: '#7eb2e2',
    //                 axisThickness: 2,
    //                 axisAlpha: 1,
    //                 position: 'left'
    //             }],
    //             graphs: [{
    //                 valueAxis: 'left_value_axes',
    //                 lineColor: '#7eb2e2',
    //                 bullet: 'round',
    //                 bulletBorderThickness: 1,
    //                 hideBulletsCount: 30,
    //                 title: '充电人次',
    //                 valueField: 'cdrc',
    //                 fillAlphas: 0
    //             }],
    //             chartScrollbar: {
    //                 enabled: false
    //             },
    //             chartCursor: {
    //                 cursorPosition: 'mouse'
    //             },
    //             categoryField: 'date',
    //             categoryAxis: {
    //                 parseDates: false,
    //                 axisColor: '#DADADA',
    //                 minorGridEnabled: true
    //             }
    //         });
    //     }
    //     chart2.dataProvider = data;
    //     chart2.validateNow();
    //     chart2.validateData();
    // }

    var initChartPie = function () {
        var stationId = getQueryString("stationId");
        Xy.requestApi('/station/get_30days_order', {stationId:stationId}, function (data) {
            var effective = 0;
            var invalid = 0;
            for (var i = 0; i < data.length; i++) {
                if (data[i].order_state == '4' || data[i].order_state == '5' || data[i].order_state == '7') {
                    effective = effective + data[i].count;
                } else if(data[i].order_state == '2'||data[i].order_state == '3'||data[i].order_state == '6'||data[i].order_state == '9'||data[i].order_state == '10'){
                    invalid = invalid + data[i].count;
                }
            }
            refreshChart1([{
                type: "有效订单",
                value: effective || 1
            }, {
                type: "无效订单",
                value: invalid || 3
            }]);
        })
    }
    initJzlCount();//初始化交直流充电桩数量
    initChartPie();
    refreshChart2(generateChartData());
    setInterval(function () {
        // TODO coding......
        initChartPie();
        refreshChart2(generateChartData());
    }, Xy.INTERVAL_TIME);
}

$(function () {
    $('#xy-header .xy-nav-item-bg').addClass('xy-device');
    $('#xy-header .xy-device a').addClass('xy-selected');

    Xy.Module01.refresh();
    Xy.Module02.refresh();
    Xy.Module03.refresh();
    Xy.Module04.refresh();
    Xy.Module05.refresh();
    Xy.Module08.refresh();

    setInterval(function () {
        Xy.Module01.refresh();
        Xy.Module02.refresh();
        Xy.Module03.refresh();
        Xy.Module04.refresh();
        Xy.Module05.refresh();
    }, Xy.INTERVAL_TIME);

});

//加载直流充电桩和交流充电桩数量
var initJzlCount = function(){
    var stationId = getQueryString("stationId");
    Xy.requestApi('/station/initJzlCount', {stationId:stationId}, function (data) {
        var jzLObj = $(".xy-sum");
        if(jzLObj){
            jzLObj[0].innerHTML = "合计："+data[0].zlCount+"&nbsp;台";
            jzLObj[1].innerHTML = "合计："+data[0].jlCount+"&nbsp;台";
            //判断如果充电桩的数量少于5台的时候不显示更多按钮
            debugger;
            if(data[0].zlCount <5 && $("#xy-row-2-left .xy-more")){
                $("#xy-row-2-left .xy-more")[0].innerHTML = "";
            }
            if(data[0].jlCount <5 && $("#xy-row-2-right .xy-more")){
                $("#xy-row-2-right .xy-more")[0].innerHTML = "";
            }
        }

    })
}



//获取url中的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}