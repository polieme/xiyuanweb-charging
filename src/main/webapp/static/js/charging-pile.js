Xy.Dashboard = {};
Xy.Module01 = {};
Xy.Module02 = {};
Xy.Module03 = {};
/**
 * 计算路径
 * @param v 电压
 */
Xy.Dashboard.counterPath = function (v) {
    v = v || 0;
    var MIN = 292.5;    // 0v 对应的角度
    var MAX = 67.5;     // 500v 对应的角度
    var UNIT = 0.45;    // 1v 对应的角度
    var CENTER_X = 445; // 中心轴x
    var CENTER_Y = 478; // 中心轴y
    var R = 320;        // 指针长度

    var angle = MIN - v * UNIT
    var x = R * Math.sin(2 * Math.PI / 360 * angle);
    var y = R * Math.cos(2 * Math.PI / 360 * angle);
    return 'M' + CENTER_X + ' ' + CENTER_Y + ' l' + x + ' ' + y;
};

/**
 * 创建仪表盘指针
 * @param paper 仪表盘画布
 * @param v 电压
 */
Xy.Dashboard.createPointer = function (paper, v) {
    var pointer = paper.path(Xy.Dashboard.counterPath(v));
    pointer.attr({
        stroke: '#cfd3d5',
        'stroke-width': '5px'
    });
    return pointer;
};

/**
 * 更新仪表盘数值
 * @param pointer 指针
 * @param v 电压
 */
Xy.Dashboard.updateV = function (pointer, v) {
    pointer.attr('path', Xy.Dashboard.counterPath(v));
};

Xy.Dashboard.refresh = function(){
    var paper1 = Raphael(document.getElementById('xy-module-02-dashboard'), 900, 720);
    var paper2 = Raphael(document.getElementById('xy-module-03-dashboard'), 900, 720);

    var pointer1 = Xy.Dashboard.createPointer(paper1, 0.00);
    var pointer2 = Xy.Dashboard.createPointer(paper2, 0.00);

    $('#xy-module-02 .xy-out-voltage span,#xy-module-03 .xy-out-voltage span').html('0.00');

    var requestData = function () {

        Xy.requestApi('/device/get_detail', {id:id}, function (data) {
            $('#xy-module-04 .xy-title').html(data.pile.pile_name);//充电桩名称
            $('#xy-module-05-content .xy-value-3').html(data.pile.curElect);//已充电量
            $('#xy-module-05-content .xy-value-1').html(data.pile.chargingDuration);//充电时长
            $('#xy-module-05-content .xy-value-2').html(data.pile.curElect);//已充电量

            //充电电压
            Xy.Dashboard.updateV(pointer1, data.pile.voltage);
            $('#xy-module-02 .xy-out-voltage span').html(data.pile.voltage);

            //充电电流
            Xy.Dashboard.updateV(pointer2, data.pile.current);
            $('#xy-module-03 .xy-out-voltage span').html(data.pile.current);
        });
    };

    requestData();
    // 更新数据
    window.setInterval(requestData, Xy.INTERVAL_TIME);
}


$(function () {
    // 导航条下高亮线
    $('#xy-header .xy-nav-item-bg').addClass('xy-operation');

    Xy.Dashboard.refresh();

});