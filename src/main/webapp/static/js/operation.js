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
Xy.Module01.refresh = function () {

    var showRunningDays = function () {
        var begin = new Date();
        begin.setFullYear(2015, 11,20 );
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

/**
 * 计算当日充电电量的图表宽度
 * @param val 电量
 */
Xy.Module06.counterChargingWidth = function (val) {
    // 当日充电量最大是10000
    // 当日充电量最大显示宽度是557
    return val / 1000 * 557;
};
/**
 * 修改电量
 * @param obj 显示值得dom对象
 * @param val 值
 */
Xy.Module06.changeChargingQuantity = function (obj, val) {
    obj.width(Xy.Module06.counterChargingWidth(val)).html(val);
};

Xy.Module06.refresh = function () {

    /**
     * 默认站点数据
     * @type {{
     * todayQuantity: number,               // 当日充电量
     * totalQuantity: number,               // 累计充电量
     * dcStationCount: number,              // 直流桩
     * acStationCount: number,              // 交流桩
     * leisureStationCount: number,         // 空闲
     * busyStationCount: number,            // 忙碌
     * malfunctionStationCount: number,     // 故障
     * offLineStationCount: number          // 离线
     * }}
     */
    var defaultStationOption = {
        todayQuantity: 0,
        totalQuantity: 0,
        dcStationCount: 0,
        acStationCount: 0,
        leisureStationCount: 0,
        busyStationCount: 0,
        malfunctionStationCount: 0,
        offLineStationCount: 0
    };
    /**
     * 通过当前登录的运营商判断展示的两个充电站
     */
    var owner = "null";
    getStationCode(owner);
    function getStationCode(owner){
        debugger;
        //若登录人员为运维人员
        if(owner=="null"){
            //运维人员登录默认显示忘归酒店和丰台张仪村两个优易充小站的信息
            getSingleStationInfo('1014507799978950178',1);
            getSingleStationInfo('1014607105341453524',2);
            //更多按钮显示全部站点的信息
            $('#xy-module-fan-03').css('display', 'block');
            $('#fan-tip1').html('丰台区腾势张仪村优易充小站');
            $('#fan-tip2').html('西城区中新佳园二区优易充小站');
            $('#xy-module-fan-01').attr('href','device.jsp?stationId='+1014507799978950178);
            $('#xy-module-fan-02').attr('href','device.jsp?stationId='+1014609431783924297);
        }else{
            //此处进行判断，若该运营商所属的充电站数量小于等于2，则不展示更多按键，并清除更多按钮上的连接信息
            Xy.requestApi('/operation/returnInfo', {owner: owner}, function (data){
                if(data.length==1){
                        //不显示更多
                    getSingleStationInfo(data[0].ID,1);
                    var links = data[0].ID;
                    $('#fan-tip1').html(data[0].station_name);
                    $('.link-fan-1').attr('href','device.jsp?stationId='+data[0].ID);
                    $('#xy-module-fan-03').css('display','none');
                }else if(data.length>=2){
                        //不显示更多
                    //显示站点名称
                    var link  = data[0].ID;
                    var links = data[1].ID
                    $('#fan-tip1').html(data[0].station_name);
                    $('#fan-tip2').html(data[1].station_name);
                    $('#link-fan-1').attr('href','device.jsp?stationId='+data[0].ID);
                    $('#link-fan-2').attr('href','device.jsp?stationId='+data[1].ID);
                    //获取站点信息
                    getSingleStationInfo(data[0].ID,1);
                    getSingleStationInfo(data[1].ID,2);
                    //是否显示更多按钮
                    if(data.length==2) {
                        $('#xy-module-fan-03').css('display', 'none');
                    }else{
                        $('#xy-module-fan-03').css('display', 'block');
                    }
                }
            });
        }
    }

    /**
     * 获取单一站点的充电桩信息和充电信息
     */
    function getSingleStationInfo(id,num){
        Xy.requestApi('/station/get_station_charge', {id: id}, function (data) {
            if (data) {
                // 当日充电电量
                var leisureStationCount = 0, busyStationCount = 0,
                    malfunctionStationCount = 0, offLineStationCount = 0,
                    dcStationCount = 0, acStationCount = 0;
                var pilelist = data.pileStatus;
                for (var i = 0; i < pilelist.length; i++) {
                    var run_status = pilelist[i].run_status;
                    if (run_status == '1') {
                        leisureStationCount = pilelist[i].count || 0;
                    } else if (run_status == '3') {
                        busyStationCount = busyStationCount+pilelist[i].count || 0;
                    } else if (run_status == '7') {
                        malfunctionStationCount = malfunctionStationCount+pilelist[i].count || 0;
                    } else if (run_status == '4') {
                        offLineStationCount = offLineStationCount+pilelist[i].count || 0;
                    } else if (run_status == "8"){
                        busyStationCount = busyStationCount+pilelist[i].count || 0;
                    } else if (run_status == "9"){
                        offLineStationCount = offLineStationCount + pilelist[i].count || 0;
                    } else if (run_status == "2"){
                        busyStationCount = busyStationCount + pilelist[i].count || 0;
                    } else if (run_status == "5"){
                        malfunctionStationCount = malfunctionStationCount + pilelist[i].count || 0;
                    } else if (run_status == "6"){
                        leisureStationCount = leisureStationCount + pilelist[i].count || 0;
                    }}
                var typelist = data.jlstatus;
                for (var i = 0; i < typelist.length; i++) {
                    var pile_type = typelist[i].pile_type;
                    if (pile_type == '1') {
                        dcStationCount = typelist[i].count || 0;
                    } else if (pile_type == '2') {
                        acStationCount = typelist[i].count || 0;
                    }
                }
                var today = data.baseDay;
                var todaysum = today[0].quantity;
                // TODO 渲染数据的方法
                refreshStationInfo({
                    todayQuantity: todaysum||0,
                    totalQuantity: data.base.quantity || 0,
                    dcStationCount: dcStationCount,
                    acStationCount: acStationCount,
                    leisureStationCount: leisureStationCount,
                    busyStationCount: busyStationCount,
                    malfunctionStationCount: malfunctionStationCount,
                    offLineStationCount: offLineStationCount
                },num);
            }
        })
    }

    /**
     * 刷新方法
     */
    function refreshStationInfo(option,num){

        option = $.extend(defaultStationOption, option);
        if(num==1){
            $('#xy-module-06-01').html(option.dcStationCount);
            $('#xy-module-06-02').html(option.acStationCount);
            $('#xy-module-06-03').html(option.leisureStationCount);
            $('#xy-module-06-04').html(option.busyStationCount);
            $('#xy-module-06-05').html(option.malfunctionStationCount);
            $('#xy-module-06-06').html(option.offLineStationCount);
            Xy.Module06.changeChargingQuantity($('#xy-module-06-drcddl-1'), option.todayQuantity);
            $('#xy-module-06-ljcdl-1').html(option.totalQuantity);
        }else if(num==2){
            option = $.extend(defaultStationOption, option);
            $('#xy-module-06-07').html(option.dcStationCount);
            $('#xy-module-06-08').html(option.acStationCount);
            $('#xy-module-06-09').html(option.leisureStationCount);
            $('#xy-module-06-10').html(option.busyStationCount);
            $('#xy-module-06-11').html(option.malfunctionStationCount);
            $('#xy-module-06-12').html(option.offLineStationCount);
            Xy.Module06.changeChargingQuantity($('#xy-module-06-drcddl-2'), option.todayQuantity);
            $('#xy-module-06-ljcdl-2').html(option.totalQuantity);
        }
    }
    /**
     * 刷新腾势（丰台）站数据
     */
    /*var refreshXinShengStation = function (option) {
        option = $.extend(defaultStationOption, option);
        $('#xy-module-06-01').html(option.dcStationCount);
        $('#xy-module-06-02').html(option.acStationCount);
        $('#xy-module-06-03').html(option.leisureStationCount);
        $('#xy-module-06-04').html(option.busyStationCount);
        $('#xy-module-06-05').html(option.malfunctionStationCount);
        $('#xy-module-06-06').html(option.offLineStationCount);
        Xy.Module06.changeChargingQuantity($('#xy-module-06-drcddl-1'), option.todayQuantity);
        $('#xy-module-06-ljcdl-1').html(option.totalQuantity);
    }*/
    /**
     * 刷新忘归站数据
     */
    /*var refreshHuanChengXiLuStation = function (option) {
        option = $.extend(defaultStationOption, option);
        $('#xy-module-06-07').html(option.dcStationCount);
        $('#xy-module-06-08').html(option.acStationCount);
        $('#xy-module-06-09').html(option.leisureStationCount);
        $('#xy-module-06-10').html(option.busyStationCount);
        $('#xy-module-06-11').html(option.malfunctionStationCount);
        $('#xy-module-06-12').html(option.offLineStationCount);
        Xy.Module06.changeChargingQuantity($('#xy-module-06-drcddl-2'), option.todayQuantity);
        $('#xy-module-06-ljcdl-2').html(option.totalQuantity);
    }
    */
    /*
    Xy.requestApi('/station/get_station_charge', {id: idOne}, function (data) {
        if (data) {
            // 当日充电电量
            var leisureStationCount = 0, busyStationCount = 0,
                malfunctionStationCount = 0, offLineStationCount = 0,
                dcStationCount = 0, acStationCount = 0;
            var pilelist = data.pileStatus;
            for (var i = 0; i < pilelist.length; i++) {
                var run_status = pilelist[i].run_status;
                if (run_status == '1') {
                    leisureStationCount = pilelist[i].count || 0;
                } else if (run_status == '3') {
                    busyStationCount = busyStationCount+pilelist[i].count || 0;
                } else if (run_status == '7') {
                    malfunctionStationCount = malfunctionStationCount+pilelist[i].count || 0;
                } else if (run_status == '4') {
                    offLineStationCount = offLineStationCount+pilelist[i].count || 0;
                } else if (run_status == "8"){
                    busyStationCount = busyStationCount+pilelist[i].count || 0;
                } else if (run_status == "9"){
                    offLineStationCount = offLineStationCount + pilelist[i].count || 0;
                } else if (run_status == "2"){
                    busyStationCount = busyStationCount + pilelist[i].count || 0;
                } else if (run_status == "5"){
                    malfunctionStationCount = malfunctionStationCount + pilelist[i].count || 0;
                } else if (run_status == "6"){
                    leisureStationCount = leisureStationCount + pilelist[i].count || 0;
                }}
            var typelist = data.jlstatus;
            for (var i = 0; i < typelist.length; i++) {
                var pile_type = typelist[i].pile_type;
                if (pile_type == '1') {
                    dcStationCount = typelist[i].count || 0;
                } else if (pile_type == '2') {
                    acStationCount = typelist[i].count || 0;
                }
            }
            var today = data.baseDay;
            var todaysum = today[0].quantity;
            // TODO 渲染数据的方法
            refreshXinShengStation({
                todayQuantity: todaysum||0,
                totalQuantity: data.base.quantity || 0,
                dcStationCount: dcStationCount,
                acStationCount: acStationCount,
                leisureStationCount: leisureStationCount,
                busyStationCount: busyStationCount,
                malfunctionStationCount: malfunctionStationCount,
                offLineStationCount: offLineStationCount
            });
        }
    })*/
    /**
     * 1：空闲桩 ，2：只连接未充电，3：充电进行中, 4：GPRS通讯中断,5：检修中,6：预约，7：故障 8：自动充满，9.长期离线
     */
    /**
     * 默认站点数据
     * @type {{
     * todayQuantity: number,               // 当日充电量
     * totalQuantity: number,               // 累计充电量
     * dcStationCount: number,              // 直流桩
     * acStationCount: number,              // 交流桩
     * leisureStationCount: number,         // 空闲
     * busyStationCount: number,            // 忙碌
     * malfunctionStationCount: number,     // 故障
     * offLineStationCount: number          // 离线
     * }}
     */
   /*
    Xy.requestApi('/station/get_station_charge', {id: idTwo}, function (data) {
        if (data) {
            // 当日充电电量
            var leisureStationCount = 0, busyStationCount = 0,
                malfunctionStationCount = 0, offLineStationCount = 0,
                dcStationCount = 0, acStationCount = 0;
            var pilelist = data.pileStatus;
            for (var i = 0; i < pilelist.length; i++) {
                var run_status = pilelist[i].run_status;
                if (run_status == '1') {
                    leisureStationCount = pilelist[i].count || 0;
                } else if (run_status == '3') {
                    busyStationCount = busyStationCount+pilelist[i].count || 0;
                } else if (run_status == '7') {
                    malfunctionStationCount = malfunctionStationCount+pilelist[i].count || 0;
                } else if (run_status == '4') {
                    offLineStationCount = offLineStationCount+pilelist[i].count || 0;
                } else if (run_status == "8"){
                    busyStationCount = busyStationCount+pilelist[i].count || 0;
                } else if (run_status == "9"){
                    offLineStationCount = offLineStationCount + pilelist[i].count || 0;
                } else if (run_status == "2"){
                    busyStationCount = busyStationCount + pilelist[i].count || 0;
                } else if (run_status == "5"){
                    malfunctionStationCount = malfunctionStationCount + pilelist[i].count || 0;
                } else if (run_status == "6"){
                    leisureStationCount = leisureStationCount + pilelist[i].count || 0;
                }
            }
            var typelist = data.jlstatus;
            for (var i = 0; i < typelist.length; i++) {
                var pile_type = typelist[i].pile_type;
                if (pile_type == '1') {
                    dcStationCount = typelist[i].count || 0;
                } else if (pile_type == '2') {
                    acStationCount = typelist[i].count || 0;
                }
            }
            var today = data.baseDay;
            var todaysum = today[0].quantity;
            // TODO 渲染数据的方法
            refreshHuanChengXiLuStation({

                todayQuantity: todaysum||0,
                totalQuantity: data.base.quantity || 0,
                dcStationCount: dcStationCount,
                acStationCount: acStationCount,
                leisureStationCount: leisureStationCount,
                busyStationCount: busyStationCount,
                malfunctionStationCount: malfunctionStationCount,
                offLineStationCount: offLineStationCount
            });
        }
    })
*/
};

Xy.Module07.refresh = function () {
    var mapper = {
        '0': '急停故障',
        '1': '电表故障',
        '2': '接触器故障',
        '3': '读卡器故障',
        '4': '内部过温故障',
        '5': '连接器故障',
        '6': '绝缘故障',
        '7': '其他',
        '8':'离线',
        '13':'其他',
        '20':'无故障',
        '63':'其他',
        '64':'其他',
        '67':'其他',
        '127':'其他'
    };
    Xy.requestApi('/operation/get_fault', {owner_id:"null"}, function (data) {
        $('#xy-module-07 b').html(6);
        for (var i = 0; i < 6; i++) {
            try {
                $('#xy-module-07 .xy-status-group-' + (i + 1) + ' .xy-text').html(data[i].pile_name + ':' + mapper[data[i].fault_code]);
                $('#xy-module-07 .xy-status-group-' + (i + 1) + ' .xy-time').html(data[i].record_time);
            } catch (e) {
            }
        }
        //错误充电桩
    })
};

Xy.Module08.refresh = function () {

    var demoData = [{
        date: '星期一',
        count: 4025,
        sk_count: 3000,
        sm_count: 1025,
        color: '#FF0F00'
    }, {
        date: '星期二',
        count: 1882,
        sk_count: 1000,
        sm_count: 882,
        color: '#FF6600'
    }, {
        date: '星期三',
        count: 1809,
        sk_count: 900,
        sm_count: 909,
        color: '#FF9E01'
    }, {
        date: '星期四',
        count: 1322,
        sk_count: 600,
        sm_count: 722,
        color: '#FCD202'
    }, {
        date: '星期五',
        count: 1122,
        sk_count: 500,
        sm_count: 622,
        color: '#F8FF01'
    }, {
        date: '星期六',
        count: 1114,
        sk_count: 600,
        sm_count: 514,
        color: '#B0DE09'
    }, {
        date: '星期日',
        count: 984,
        sk_count: 584,
        sm_count: 400,
        color: '#04D215'
    }];

    var chart1, chart2;
    /**
     * 刷新图表1
     * @param data 图表数据 [{date: '周一',count: 4025}, {date: '周二',count: 1882}]
     */
    var refreshChart1 = function (data) {
        if (!chart1) {
            chart1 = AmCharts.makeChart("xy-chat-1-content", {
                theme: 'light',
                type: 'serial',
                startDuration: 2,
                valueAxes: [{
                    position: 'left',
                    axisAlpha: 1,
                    gridAlpha: 0,
                    axisColor: '#7eb2e2',
                    color: '#7eb2e2',
                    fontSize: 16
                }],
                graphs: [{
                    balloonText: '[[category]]: <b>[[value]]</b>',
                    fillAlphas: 0.85,
                    lineAlpha: 0.1,
                    type: 'column',
                    topRadius: 1,
                    valueField: 'count',
                    lineColor: '#29BE29'
                }],
                depth3D: 40,
                angle: 30,
                chartCursor: {
                    categoryBalloonEnabled: false,
                    cursorAlpha: 0,
                    zoomable: false
                },
                categoryField: 'date',
                categoryAxis: {
                    gridPosition: 'start',
                    axisAlpha: 0,
                    gridAlpha: 0,
                    color: '#7eb2e2',
                    fontSize: 16

                },
                export: {
                    enabled: false
                }
            });
        }
        if ((!data || data.length == 0) && Xy.DEV) {
            data = demoData;
        }
        if(data) {
            chart1.dataProvider = data;
            chart1.validateNow();
            chart1.validateData();
        }
    }

    /**
     * 刷新图表2
     * @param data 图表数据 [{date: '周一',count: 4025}, {date: '周二',count: 1882}]
     */
    var refreshChart2 = function (data) {
        if (!chart2) {
            chart2 = AmCharts.makeChart("xy-chat-2-content", {
                theme: 'light',
                type: 'serial',
                startDuration: 2,
                valueAxes: [{
                    stackType: 'regular',
                    position: 'left',
                    axisAlpha: 1,
                    gridAlpha: 0,
                    axisColor: '#7eb2e2',
                    color: '#7eb2e2',
                    fontSize: 16
                }],
                graphs: [/*{
                 balloonText: '[[category]]: <b>[[value]]</b>',
                 fillAlphas: 0.85,
                 lineAlpha: 0.1,
                 type: 'column',
                 topRadius: 1,
                 valueField: 'count',
                 lineColor: '#10499C'
                 },*/{
                    balloonText: '[[category]]: <b>[[value]]</b>',
                    fillAlphas: 0.85,
                    lineAlpha: 0.1,
                    type: 'column',
                    topRadius: 1,
                    valueField: 'sk_count',
                    lineColor: '#00ccff'
                },{
                    balloonText: '[[category]]: <b>[[value]]</b>',
                    fillAlphas: 0.85,
                    lineAlpha: 0.1,
                    type: 'column',
                    topRadius: 1,
                    valueField: 'sm_count',
                    lineColor: '#fc00ff'
                }],
                depth3D: 40,
                angle: 30,
                chartCursor: {
                    categoryBalloonEnabled: false,
                    cursorAlpha: 0,
                    zoomable: false
                },
                categoryField: 'date',
                categoryAxis: {
                    gridPosition: 'start',
                    axisAlpha: 0,
                    gridAlpha: 0,
                    color: '#7eb2e2',
                    fontSize: 16

                },
                export: {
                    enabled: false
                }
            });
        }
        /**if ((!data || data.length == 0) && Xy.DEV) {
            data = demoData;
        }*/
        if(data) {
            chart2.dataProvider = data;
            chart2.validateNow();
            chart2.validateData();
        }
    }

    /**var WEEKS = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];

    var getWeek = function (ymd) {
        var ymds = ymd.split('-');
        var date = new Date(ymds[0], parseInt(ymds[1])-1, ymds[2]);
        return WEEKS[date.getDay()];
    }*/

    var initChart = function () {
        var colorArr = ['#FF0F00', '#FF6600', '#FF9E01', '#FCD202', '#F8FF01', '#B0DE09', '#04D215'];
        var chart1Arr = [];
        var chart2Arr = [];
        Xy.requestApi('/operation/get_statictis_charge', {type: '2',owner_id:'null'}, function (data) {
            for (var i = 0; i < data.length; i++) {
                var chartData = {
                    data: '',
                    count: 0,
                    color: ''
                }
                chartData.date = data[i].statDay;
                chartData.count = data[i].code_time+data[i].card_time;
                chartData.sk_count = data[i].card_time;
                chartData.sm_count = data[i].code_time;
                chartData.color = colorArr[i];
                chart2Arr.push(chartData);
            }
            refreshChart2(chart2Arr);
        });
        Xy.requestApi('/operation/get_statictis_charge', {type: '1',owner_id:'null'}, function (data) {
            for (var i = 0; i < data.length; i++) {
                var chartData = {
                    data: '',
                    count: 0,
                    color: ''
                }

                chartData.date = data[i].statDay;
                chartData.count = data[i].chargeQuantity;
                chartData.color = colorArr[i];
                chart1Arr.push(chartData);
            }
            refreshChart1(chart1Arr);
        });
    }

    initChart();
    //refreshChart1(data);
    //refreshChart2(data);

    setInterval(initChart, Xy.INTERVAL_TIME);
};

$(function () {
    // 导航条下高亮线
    $('#xy-header .xy-nav-item-bg').addClass('xy-operation');
    $('#xy-header .xy-operation a').addClass('xy-selected');

    Xy.Module01.refresh();
    Xy.Module02.refresh();
    Xy.Module03.refresh();
    Xy.Module04.refresh();
    Xy.Module06.refresh();
    setInterval(function () {
        Xy.Module02.refresh();
        Xy.Module03.refresh();
        Xy.Module04.refresh();
        Xy.Module06.refresh();
    }, Xy.INTERVAL_TIME);

    Xy.Module07.refresh();
    Xy.Module08.refresh();


    // 指定图表的配置项和数据
    // Xy.requestApi('/operation/get_statictis_charge', {type :'1'}, function (data) {
    //
    //     //统计充电量
    // })
    //
    // Xy.requestApi('/operation/get_statictis_charge', {type :'2'}, function (data) {
    //
    //     //统计充电人次
    // })
    //
    // //xy-module-07
    // {
    //     Xy.requestApi('/operation/get_fault', {}, function (data) {
    //         $('#xy-module-07 b').html(data.length);
    //         //错误充电桩
    //     })
    // }

});