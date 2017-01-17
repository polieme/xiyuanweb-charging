<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <title>优易充运营运营监控中心-充电运营监控中心</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/operation.css">
    <style>
        .forlink{
            color: yellow;
        }

    </style>
</head>
<body>
<div id="xy-page">
    <%@include file="/pages/inc/page_header.jsp" %>
    <div id="xy-body">
        <div id="xy-body-left">
            <div id="xy-row-1">
                <div class="xy-module xy-module-border" id="xy-module-01">
                    <div class="xy-date">0</div>
                    <div class="xy-time">0</div>
                    <div class="xy-small-title">安全运行天数</div>
                    <div class="xy-days">0</div>
                    <div class="xy-days-unit">天</div>
                </div>
                <div class="xy-module xy-module-border" id="xy-module-02">
                    <div class="xy-map-small"></div>
                    <div class="xy-module-row xy-module-row-1">
                        当日充电电量：<span id="xy-module-02-01">0</span> kWh
                    </div>
                    <div class="xy-border-hr xy-border-hr-1"></div>
                    <div class="xy-module-row xy-module-row-2">
                        当日充电次数：<span id="xy-module-02-02">0</span> 次
                    </div>
                    <div class="xy-border-hr xy-border-hr-2"></div>
                    <div class="xy-module-row xy-module-row-3">
                        累计充电电量：<span id="xy-module-02-03">0</span> kWh
                    </div>
                    <div class="xy-border-hr xy-border-hr-3"></div>
                    <div class="xy-module-row xy-module-row-4">
                        累计充电次数：<span id="xy-module-02-04">0</span> 次
                    </div>
                    <div class="xy-border-hr xy-border-hr-4"></div>
                </div>
                <div class="xy-module" id="xy-module-03">
                    <div class="xy-label xy-label-1">充电站数量</div>
                    <div class="xy-count xy-count-1" id="xy-module-03-01">0</div>
                    <div class="xy-label xy-label-2">充电桩数量</div>
                    <div class="xy-count xy-count-2" id="xy-module-03-02">0</div>
                </div>
                <div class="xy-module" id="xy-module-04">
                    <div class="xy-count xy-count-1">0</div>
                    <div class="xy-label xy-label-1">忙碌</div>
                    <div class="xy-count xy-count-2">0</div>
                    <div class="xy-label xy-label-2">空闲</div>
                    <div class="xy-count xy-count-3">0</div>
                    <div class="xy-label xy-label-3">故障</div>
                    <div class="xy-count xy-count-4">0</div>
                    <div class="xy-label xy-label-4">离线</div>
                    <div class="xy-count xy-count-5">0</div>
                    <div class="xy-label xy-label-5">连接</div>
                    <div class="xy-count xy-count-6">0</div>
                    <div class="xy-label xy-label-6">检修</div>
                    <div class="xy-count xy-count-7">0</div>
                    <div class="xy-label xy-label-7">预约</div>
                </div>
                <div class="xy-clear"></div>
            </div>
            <div id="xy-row-2">
                <div id="xy-row-2-left">
                    <div class="xy-module" id="xy-module-05">
                        <div class="xy-title">分布地图</div>
                        <div class="xy-star xy-star-1"></div>
                        <div class="xy-star xy-star-2"></div>
                    </div>
                    <div class="xy-clear"></div>
                </div>
                <div id="xy-row-2-right">
                    <div class="xy-module" id="xy-module-06">
                        <div class="xy-title-left" id="xy-module-fan-01">
                            <span id="fan-tip1">丰台区腾势张仪村优易充小站</span>
                            <a class = 'forlink' id = 'link-fan-1' href="device.jsp?stationId=1014507799978950178">
                                查看
                            </a>
                        </div>
                        <div class="xy-title-right" id="xy-module-fan-02">
                            <span id="fan-tip2">忘归酒店东方远洋店优易充小站</span>
                            <a class = 'forlink' id = 'link-fan-2' href="device.jsp?stationId=1014607105341453524">
                                查看
                            </a>
                        </div>
                        <div class="xy-btn-more" id="xy-module-fan-03">
                            <a target="_blank" href="${website_root}/pages/monitoring/station_list.jsp">
                                <img src="${website_root}/static/imgs/operation/model-06/btn-more.png" alt="更多">
                            </a>
                        </div>

                        <div class="xy-status-group xy-status-group-1">
                            <div class="xy-label">直流桩</div>
                            <div class="xy-value xy-value-1" id="xy-module-06-01">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-2">
                            <div class="xy-label">交流桩</div>
                            <div class="xy-value xy-value-2" id="xy-module-06-02">0</div>
                            <div class="xy-unit">台</div>
                        </div>

                        <div class="xy-status-group xy-status-group-3">
                            <div class="xy-label">直流桩</div>
                            <div class="xy-value xy-value-3" id="xy-module-06-07">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-4">
                            <div class="xy-label">交流桩</div>
                            <div class="xy-value xy-value-4" id="xy-module-06-08">0</div>
                            <div class="xy-unit">台</div>
                        </div>

                        <div class="xy-electric-quantity-group xy-electric-quantity-group-1">
                            <div class="xy-label">当日充电量</div>
                            <div class="xy-value" id="xy-module-06-drcddl-1">0</div>
                            <div class="xy-unit">kWh</div>
                            <div class="xy-clear"></div>
                        </div>
                        <div class="xy-electric-quantity-group xy-electric-quantity-group-2">
                            <div class="xy-label">累计充电量</div>
                            <div class="xy-value" id="xy-module-06-ljcdl-1">0</div>
                            <div class="xy-unit">kWh</div>
                            <div class="xy-clear"></div>
                        </div>

                        <div class="xy-status-group xy-status-group-5">
                            <div class="xy-label">空闲</div>
                            <div class="xy-value xy-value-5" id="xy-module-06-03">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-6">
                            <div class="xy-label">忙碌</div>
                            <div class="xy-value xy-value-6" id="xy-module-06-04">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-7">
                            <div class="xy-label">故障</div>
                            <div class="xy-value xy-value-7" id="xy-module-06-05">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-8">
                            <div class="xy-label">离线</div>
                            <div class="xy-value xy-value-8" id="xy-module-06-06">0</div>
                            <div class="xy-unit">台</div>
                        </div>

                        <div class="xy-electric-quantity-group xy-electric-quantity-group-3">
                            <div class="xy-label">当日充电量</div>
                            <div class="xy-value" id="xy-module-06-drcddl-2">0</div>
                            <div class="xy-unit">kWh</div>
                            <div class="xy-clear"></div>
                        </div>
                        <div class="xy-electric-quantity-group xy-electric-quantity-group-4">
                            <div class="xy-label">累计充电量</div>
                            <div class="xy-value" id="xy-module-06-ljcdl-2">0</div>
                            <div class="xy-unit">kWh</div>
                            <div class="xy-clear"></div>
                        </div>

                        <div class="xy-status-group xy-status-group-9">
                            <div class="xy-label">空闲</div>
                            <div class="xy-value xy-value-9" id="xy-module-06-09">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-10">
                            <div class="xy-label">忙碌</div>
                            <div class="xy-value xy-value-10" id="xy-module-06-10">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-11">
                            <div class="xy-label">故障</div>
                            <div class="xy-value xy-value-11" id="xy-module-06-11">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                        <div class="xy-status-group xy-status-group-12">
                            <div class="xy-label">离线</div>
                            <div class="xy-value xy-value-12" id="xy-module-06-12">0</div>
                            <div class="xy-unit">台</div>
                        </div>
                    </div>
                    <div class="xy-module" id="xy-module-07">
                        <div class="xy-tip" style="display: none">提示：有<b>0</b>处设施发生错误请注意</div>
                        <div class="xy-status-group xy-status-group-1">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：只连接未充电</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                        <div class="xy-status-group xy-status-group-2">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：充电进行中</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                        <div class="xy-status-group xy-status-group-3">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：GPRS通讯中断</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                        <div class="xy-status-group xy-status-group-4">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：空闲桩</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                        <div class="xy-status-group xy-status-group-5">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：检修中</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                        <div class="xy-status-group xy-status-group-6">
                            <div class="xy-status-group-body">
                                <div class="xy-label xy-label-2">轻微</div>
                                <div class="xy-text">直流电桩001号：故障</div>
                                <div class="xy-time">2016-08-31 10 : 25</div>
                            </div>
                        </div>
                    </div>
                    <div class="xy-clear"></div>
                </div>
                <div class="xy-clear"></div>
            </div>
        </div>
        <div id="xy-body-right">
            <div class="xy-module xy-module-border" id="xy-module-08">
                <div class="xy-chat">
                    <div class="xy-chat-left-title">充电电量趋势</div>
                    <div class="xy-chat-right-title xy-chat-right-title-1">电量</div>
                    <div class="xy-chat-content" id="xy-chat-1-content"></div>
                </div>
                <div class="xy-middle-hr"></div>
                <div class="xy-chat">
                    <div class="xy-chat-left-title">充电次数趋势</div>
                    <div class="xy-chat-right-title xy-chat-right-title-3">刷卡</div>
                    <div class="xy-chat-right-title xy-chat-right-title-4">扫码</div>
                    <div class="xy-chat-content" id="xy-chat-2-content"></div>
                </div>
            </div>
        </div>
        <div class="xy-clear"></div>
    </div>
</div>
<script type="text/javascript" src="${website_root}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/amcharts.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/serial.js"></script>
<script type="text/javascript" src="${website_root}/static/js/xiyuanweb.js"></script>
<script type="text/javascript" src="${website_root}/static/js/operation.js"></script>
</body>
</html>