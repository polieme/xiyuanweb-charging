
function initPileGrid(stationId,type){
    debugger;
    var dataArg;
    //获取充电站下面充电桩的状态
    Xy.requestApiSync('/station/get_alternating_direct', {type: type,stationId:stationId}, function (datas) {
        dataArg = datas;
    })
    var pileObj = $("#xy-row-2-left");
    var pileStatus = "";//充电桩的运行状态
    var letfPx = 10;
    var topPx = 166;
    var fontColor = "";
    //修改充电桩的类型和充电桩的数量和样式（交流桩的显示的为黄色）
    if(type==1){
        $('.xy-title')[0].innerHTML = "直流充电桩";
        fontColor = "#71a1e5";
        $('#xy-row-2')[0].style.color = fontColor;
    }else if (type==2){
        $('.xy-title')[0].innerHTML = "交流充电桩";
        fontColor = "#f1a328";
        $('#xy-row-2')[0].style.color = fontColor;

    }
    $('.xy-sum')[0].innerHTML = "合计:"+dataArg.length+"台";

    for(var i=1;i<dataArg.length+1;i++){
        //通过循环进行列表排序，6个换行
        if(i!=1 && i%6==1){
            letfPx = 10;
            topPx = 116+i/6*498;
        }else if(i!=1){
            letfPx = 10+(i-1)%6*966;
        }

        pileObj.append(" <div class='xy-sub-module' style='left:"+letfPx+"px;top: "+topPx+"px;'>" +
        "<div class='xy-sub-module-content'> <div class='xy-sub-module-title' style='color: "+fontColor+"'>"+dataArg[i-1].pile_name+"</div> <div class='xy-sub-module-icon'> " +
        "<a href='#'><img src='"+basePath+"/static/imgs/device/charging-service-2.png' alt=''></a> </div>" +
        " <div class='xy-sub-status-group xy-sub-status-group-1'> <div class='xy-label'>输出电压</div>" +
        "<div class='xy-value xy-value-1'>"+dataArg[i-1].dy+"</div><div class='xy-unit'>V</div> </div> <div class='xy-sub-status-group xy-sub-status-group-2'>" +
        "<div class='xy-label'>输出电流</div> <div class='xy-value xy-value-2'>"+dataArg[i-1].dl+"</div> <div class='xy-unit'>A</div></div>" +
        "<div class='xy-sub-status-group xy-sub-status-group-3'>  <div class='xy-label'>运行状态</div>" +
        "<div class='xy-value xy-value-3'>忙碌</div></div><div class='xy-sub-status-group xy-sub-status-group-4'>" +
        "<div class='xy-label'>异常报警</div><div class='xy-value xy-dot-green'></div></div>  <div class='xy-progress-bar'>" +
        "<div class='xy-progress-bar-left'></div> <div class='xy-progress-bar-center' style='width:504px;'></div> <div class='xy-progress-bar-right'></div>" +
        "</div> <div class='xy-progress-bar-text'>"+dataArg[i-1].soc+"%</div></div></div>");
        //加载SOC值及充电桩的运行状态
        $('.xy-progress-bar-center')[i-1].style.width = dataArg[i-1].soc/ 100 * 630+'px';
        //加载运行状态及电压电流等信息
        if (dataArg[i-1].status == '1') {
            $('.xy-value-3')[i-1].innerHTML = '空闲';
            $('.xy-value-3')[i-1].style.backgroundColor =  '#5bc35d';

        } else {
            $('.xy-value-3')[i-1].innerHTML = '忙碌';
            $('.xy-value-3')[i-1].style.backgroundColor =  '#c72d3a';
        }


    }

}