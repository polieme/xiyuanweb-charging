//定义全局常量，保证滚动到最下的时候只提醒一次
var isInit = 0;
var initCount = 1;//初始化了多少次
var letfPx = 0;
var topPx = 0;
var i=1;//加载的第几个
var showWhicStation = 1;

//加载充电站列表
initStationList(1);
function initStationCount(stationType){
    Xy.requestApiSync('/station/getStationCount', {stationType:stationType}, function (data) {
        $("#xy-body #xy-row-2 .xy-sum")[0].innerHTML = "合计："+data[0].STATION_COUNT+"&nbsp;座";
    })
}

//stationType 显示哪些充电站  1：所有充电站  2：优易充小站
function initStationList(stationType){
    //加载充电站数量
    initStationCount(stationType);
    isInit = 1;
    showWhicStation = stationType;
    var stationListDiv = $("#xy-row-2-left");//充电站列表外层DIV
    var stationInfoArgs;
    //获取充电站列表
    var dataArg;
    Xy.requestApiSync('/station/getStationList', {initCount:initCount,stationType:stationType}, function (datas) {
        dataArg = datas;
    })

    //循环遍历展示对应的充电站（该地方存在一个问题，当加载的数量不是16个的时候）
    var iMax = dataArg.length==16?(16*initCount+1):(16*initCount+1-(16-dataArg.length));
    for(i;i<iMax;i++) {
        //通过循环进行列表排序，6个换行
        if (i != 1 && i % 4 == 1) {
            letfPx = 0;
            topPx =parseInt(i / 4) * 844;
        } else if (i != 1) {
            letfPx = (i - 1) % 4 * 1370;
        }

        //循环遍历获取充电桩的状态dataArg[i-1].id
        Xy.requestApiSync('/station/getStationInfo', {stationId:dataArg[i-(initCount-1)*16-1].id}, function (data) {
            stationInfoArgs = data;
        })
        addStationDiv(stationListDiv,dataArg,stationInfoArgs)
    }
    isInit = 0;

    //滚动条自动往下滚（第一次加载的时候不下滑）
    if(initCount!=1){
        autoScroll();
    }
    ++initCount;
}

//加载DIV
function addStationDiv(stationListDiv,dataArg,stationInfoArgs){
    var todayQuantity = stationInfoArgs.TODAY_QUANTITY*1;//当日充电电量
    var todayQuantityLen = (todayQuantity>1000?1000:todayQuantity)/1000*557;//当日充电电量条长度
    todayQuantityLen = todayQuantityLen<130?130:todayQuantityLen;
    var totalQuantity = stationInfoArgs.TOTAL_QUANTITY*1;//所有充电电量
    var totalQuantityLen = (totalQuantity>1000?1000:totalQuantity)/1000*557;
    totalQuantityLen = totalQuantityLen<150?150:totalQuantityLen;
    var isShowCamera = dataArg[i-(initCount-1)*16-1].camera_serial==undefined ?"none":"";//摄像头PIC是否显示

    stationListDiv.append("<div style='position: absolute;left:"+letfPx+"px;top:"+topPx+"px'> <div id='xy-module-01' style='background: url(/xiyuanweb-charging/static/css/imgs/station-list/station_list_bg.png) no-repeat left top !important; margin-top: 130px;height: 714px;'>" +
        "<div class='xy-title-left' style='top:160px;left: 20px;'>"+dataArg[i-(initCount-1)*16-1].station_name+"<span>&nbsp;</span><a id = 'forlink' target='_blank' href='device.jsp?stationId="+dataArg[i-(initCount-1)*16-1].id+"'>查看</a></div>" +

        "<div class='xy-station-pic' style='background-image: url(http://yyc.renrenchongdian.com/charge/"+dataArg[i-(initCount-1)*16-1].cover_crop_img+");'></div>"+

        "<div class='xy-camera-pic' style='display: "+isShowCamera+"' onclick='showCameraPci("+dataArg[i-(initCount-1)*16-1].camera_serial+")'></div>"+

        "<div class='xy-status-group xy-status-group-1'> " +
        "<div class='xy-label'>直流桩</div>" +
        "<div class='xy-value xy-value-1'>"+stationInfoArgs.ZL_COUNT+"</div>" +
        " <div class='xy-unit'>台</div>" +
        "</div>" +

        "<div class='xy-status-group xy-status-group-2'>" +
        "<div class='xy-label'>交流桩</div>" +
        "<div class='xy-value xy-value-2'>"+stationInfoArgs.JL_COUNT+"</div>" +
        "<div class='xy-unit'>台</div>" +
        "</div>" +
        "<div class='xy-electric-quantity-group xy-electric-quantity-group-1'>" +
        "<div class='xy-label'>当日充电量</div>" +
        "<div class='xy-value' style='width:"+todayQuantityLen+"px'>"+todayQuantity+"</div>" +
        "<div class='xy-unit'>kWh</div>" +
        "<div class='xy-clear'></div>" +
        "</div>" +
        "<div class='xy-electric-quantity-group xy-electric-quantity-group-2'>" +
        "<div class='xy-label'>累计充电量</div> " +
        "<div class='xy-value' style='width: "+totalQuantityLen+"px'>"+totalQuantity+"</div>" +
        "<div class='xy-unit'>kWh</div>" +
        "<div class='xy-clear'></div>" +
        "</div>" +
        "<div class='xy-status-group xy-status-group-5'>" +
        "<div class='xy-pilestatus-ico' style='background-image:url(/xiyuanweb-charging/static/css/imgs/station-list/free_pile_count.png)'></div>"+
        "<div class='xy-pile-label'>空闲</div>" +
        "<div class='xy-pile-value'>"+stationInfoArgs.FREE_COUNT+"</div>" +
        "<div class='xy-pile-unit'>台</div>" +
        "</div>" +
        "<div class='xy-status-group xy-status-group-6'>" +
        "<div class='xy-pilestatus-ico' style='background-image:url(/xiyuanweb-charging/static/css/imgs/station-list/busy_pile_count.png)'></div>"+
        "<div class='xy-pile-label'>忙碌</div>" +
        "<div class='xy-pile-value'>"+stationInfoArgs.BUSY_COUNT+"</div>" +
        "<div class='xy-pile-unit'>台</div>" +
        "</div>" +
        "<div class='xy-status-group xy-status-group-7'>" +
        "<div class='xy-pilestatus-ico' style='background-image:url(/xiyuanweb-charging/static/css/imgs/station-list/fault_pile_count.png)'></div>"+
        "<div class='xy-pile-label'>故障</div>" +
        "<div class='xy-pile-value'  " +
        ">"+stationInfoArgs.FAULT+"</div>" +
        "<div class='xy-pile-unit'>台</div>" +
        "</div>" +
        "<div class='xy-status-group xy-status-group-8'>" +
        "<div class='xy-pilestatus-ico' style='background-image:url(/xiyuanweb-charging/static/css/imgs/station-list/offline_pile_count.png)'></div>"+
        "<div class='xy-pile-label'>离线</div>" +
        "<div class='xy-pile-value'>"+stationInfoArgs.OFF_LINE+"</div>" +
        "<div class='xy-pile-unit'>台</div>" +
        "</div>" +
        "</div>" +
        "</div>")
}

//判断浏览器是否滑到最底
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if(scrollTop + windowHeight >= scrollHeight-2){
        if(isInit == 0){
            initStationList(showWhicStation);
        }
    }
});

//滚动条自动下滚
function autoScroll(){
    var scrollHeight = $(document).height();//整个页面的高度
    var windowHeight = $(this).height();//能看到的浏览器的高度
    $("html,body").animate({scrollTop:($(document).height()-$(this).height()-200)},2000);
}

//打开摄像头获取图片
function showCameraPci(cameraSerial){
    Xy.requestApiSync('/station/getStationCameraPic', {cameraSerial:cameraSerial}, function (data) {
        data = eval('('+data+')');
        debugger;
        if(data.code!=200){
            alert(data.msg);
        }else{
            //创建DIV显示对应的Picrute
            $(".hiddenDiv")[0].style.display = "block";
            $(".camera-pic-div")[0].style.display = "block";
            $(".camera-pic-div")[0].style.backgroundImage = "url("+data.data.picUrl+")";
            $(".camera-pic-div")[0].style.backgroundRepeat = "no-repeat";

            //alert(data.data.picUrl);
        }

    })
}

//隐藏div
function hiddenDiv(){
    $(".hiddenDiv")[0].style.display = "none";
    $(".camera-pic-div")[0].style.display = "none";
}

//更改（充电站/优易充小站的）背景颜色，并清除DIV中的数据
function chgTitleCorlor(showWhicStation){

    //首先清掉所有标签的背景颜色
    var objs = $("#xy-row-2 div[id^='xy-title']");
    if(objs!=null){
        for( var y=0;y<objs.length;y++){
            objs[y].style.backgroundColor = "transparent";
        }
    }
    //设置选中标签颜色
    $("#xy-row-2 #xy-title-"+showWhicStation+"")[0].style.backgroundColor = "#1C3550";
    //清掉div中的内容
    $("#xy-row-2-left").empty();
    //重置全局变量
    initCount = 1;
    letfPx = 0;
    topPx = 0;
    i=1;
}
