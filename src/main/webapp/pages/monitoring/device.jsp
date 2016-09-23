<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <title>设备监控中心-充电运营监控中心</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/device.css">
    <%
        String stationId = request.getParameter("stationId");
        System.out.print(stationId);
    %>
</head>
<body>
    <div id="xy-page">
        <%@include file="/pages/inc/page_header.jsp" %>
        <div id="xy-body">
            <div id="xy-body-left">
                <div id="xy-row-1">
                    <div class="xy-module xy-module-border" id="xy-module-01">
                        <div class="xy-date"></div>
                        <div class="xy-time"></div>
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
                        <div class="xy-count xy-count-3">0</div>
                        <div class="xy-label xy-label-1">忙碌（台）</div>
                        <div class="xy-count xy-count-1">0</div>
                        <div class="xy-label xy-label-2">空闲（台）</div>
                        <div class="xy-count xy-count-7">0</div>
                        <div class="xy-label xy-label-3">故障（台）</div>
                        <div class="xy-count xy-count-4">0</div>
                        <div class="xy-label xy-label-4">离线（台）</div>
                        <div class="xy-count xy-count-2">0</div>
                        <div class="xy-label xy-label-5">连接（台）</div>
                        <div class="xy-count xy-count-5">0</div>
                        <div class="xy-label xy-label-6">检修（台）</div>
                        <div class="xy-count xy-count-6">0</div>
                        <div class="xy-label xy-label-7">预约（台）</div>
                    </div>
                    <div class="xy-clear"></div>
                </div>
                <div id="xy-row-2">
                    <div id="xy-row-2-left">
                        <div class="xy-title">直流充电桩</div>
                        <div class="xy-sum">合计：1933&nbsp;台</div>
                        <div class="xy-sub-module xy-sub-module-1" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">直流电桩001号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-1.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">342.1</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">74.7</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">80%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-2" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">直流电桩002号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-2.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">371.4</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">16.6</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">81%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-3" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">直流电桩003号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-1.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">331.3</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">49.4</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">40%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-4" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">直流电桩004号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-2.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">324.2</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">74.9</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">33%</div>
                            </div>
                        </div>
                    </div>
                    <div id="xy-row-2-right">
                        <div class="xy-title">交流充电桩</div>
                        <div class="xy-sum">合计：3763&nbsp;台</div>
                        <div class="xy-sub-module xy-sub-module-1" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">交流电桩001号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-3.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">237.44</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">9.74</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">50%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-2" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">交流电桩002号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-4.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">219.1</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">15.1</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">90%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-3" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">交流电桩003号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-3.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">233.1</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">31.9</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">75%</div>
                            </div>
                        </div>
                        <div class="xy-sub-module xy-sub-module-4" style="display: none">
                            <div class="xy-sub-module-content">
                                <div class="xy-sub-module-title">交流电桩004号</div>
                                <div class="xy-sub-module-icon">
                                    <a href="#"><img src="${website_root}/static/imgs/device/charging-service-3.png" alt=""></a>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-1">
                                    <div class="xy-label">输出电压</div>
                                    <div class="xy-value xy-value-1">220.1</div>
                                    <div class="xy-unit">V</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-2">
                                    <div class="xy-label">输出电流</div>
                                    <div class="xy-value xy-value-2">32</div>
                                    <div class="xy-unit">A</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-3">
                                    <div class="xy-label">运行状态</div>
                                    <div class="xy-value xy-value-3">忙碌</div>
                                </div>
                                <div class="xy-sub-status-group xy-sub-status-group-4">
                                    <div class="xy-label">异常报警</div>
                                    <div class="xy-value xy-dot-green"></div>
                                </div>
                                <div class="xy-progress-bar">
                                    <div class="xy-progress-bar-left"></div>
                                    <div class="xy-progress-bar-center" style="width:504px;"></div>
                                    <div class="xy-progress-bar-right"></div>
                                </div>
                                <div class="xy-progress-bar-text">10%</div>
                            </div>
                        </div>
                    </div>
                    <div class="xy-clear"></div>
                </div>
            </div>
            <div id="xy-body-right">
                <div class="xy-module xy-module-border" id="xy-module-08">
                    <div class="xy-chat xy-chat-1">
                        <div class="xy-chat-left-title">近30天有效订单</div>
                        <div class="xy-chat-content xy-chat-content-1" id="xy-chat-1-content"></div>
                    </div>
                    <div class="xy-middle-hr"></div>
                    <div class="xy-chat xy-chat-2">
                        <div class="xy-chat-left-title">近30天充电次数</div>
                        <div class="xy-chat-content xy-chat-content-2" id="xy-chat-2-content"></div>
                    </div>
                </div>
            </div>
            <div class="xy-clear"></div>
        </div>
    </div>
    <script type="text/javascript" src="${website_root}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${website_root}/static/amcharts/amcharts.js"></script>
    <script type="text/javascript" src="${website_root}/static/amcharts/serial.js"></script>
    <script type="text/javascript" src="${website_root}/static/amcharts/pie.js"></script>
    <script type="text/javascript" src="${website_root}/static/js/xiyuanweb.js"></script>
    <script type="text/javascript" src="${website_root}/static/js/device.js"></script>
</body>
</html>