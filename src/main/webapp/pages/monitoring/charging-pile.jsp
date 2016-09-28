<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String id = request.getParameter("id");
%>
<!doctype html>
<html>
<head>
    <title>运营监控中心-充电运营监控中心</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/charging-pile.css">
    <script>
        id = '<%=id%>'
    </script>
</head>
<body>
    <div id="xy-page">
        <%@include file="/pages/inc/page_header.jsp"%>
        <div id="xy-body">
            <div id="xy-body-row-1">
                <div class="xy-module" id="xy-module-01">
                    <div id="xy-module-01-content">
                        <div class="xy-soc">SOC:100.00</div>
                        <div class="xy-status">充电</div>
                    </div>
                </div>
                <div class="xy-module" id="xy-module-02">
                    <div id="xy-module-02-content">
                        <div id="xy-module-02-dashboard"></div>
                        <div class="xy-out-voltage">输出电压：<span></span> V</div>
                    </div>
                </div>
                <div class="xy-module" id="xy-module-03">
                    <div id="xy-module-03-content">
                        <div id="xy-module-03-dashboard"></div>
                        <div class="xy-out-voltage">输出电流：<span></span> A</div>
                    </div>
                </div>
                <div class="xy-module" id="xy-module-04">
                    <div id="xy-module-04-content">
                        <div class="xy-title">直流充电桩002号</div>
                        <div class="xy-item-1">是否连接电池：<span>是</span></div>
                        <!--<div class="xy-item-2">是否连接电池：<span>是</span></div>-->
                    </div>
                </div>
            </div>
            <div id="xy-body-row-2">
                <div class="xy-module" id="xy-module-05">
                    <div id="xy-module-05-content">
                        <div class="xy-hr xy-hr-1"></div>
                        <div class="xy-hr xy-hr-2"></div>
                        <div class="xy-hr xy-hr-3"></div>
                        <div class="xy-img xy-img-1">
                            <img src="${website_root}/static/imgs/charging-pile/icon-sj.png" alt="时间">
                        </div>
                        <div class="xy-img xy-img-2">
                            <img src="${website_root}/static/imgs/charging-pile/icon-dl.png" alt="电量">
                        </div>
                        <div class="xy-img xy-img-3">
                            <img src="${website_root}/static/imgs/charging-pile/icon-ygzdt.png" alt="有功总电度">
                        </div>
                        <div class="xy-label xy-label-1">充电时长（分钟）：</div>
                        <div class="xy-value xy-value-1">0.00</div>
                        <div class="xy-label xy-label-2">已充电量（kWh）：</div>
                        <div class="xy-value xy-value-2">0.00</div>
                        <div class="xy-label xy-label-3">总电度（kWh）：</div>
                        <div class="xy-value xy-value-3">0.00</div>
                    </div>
                </div>
                <div id="xy-module-06">
                    <div id="xy-module-06-content">
                        <div class="xy-button xy-button-1">急停故障</div>
                        <div class="xy-button xy-button-2">电表故障</div>
                        <div class="xy-button xy-button-3">绝缘故障</div>
                        <div class="xy-button xy-button-4">输入电压欠压</div>
                        <div class="xy-button xy-button-5">输入压力过压</div>
                        <div class="xy-button xy-button-6">接触器故障</div>
                        <div class="xy-button xy-button-7">读卡器故障</div>
                        <div class="xy-button xy-button-8">充电枪未连接告警</div>
                        <div class="xy-button xy-button-9">蓄电池充电过流告警</div>
                        <div class="xy-button xy-button-10">交流输入过压</div>
                        <div class="xy-button xy-button-11">内部过温故障</div>
                        <div class="xy-button xy-button-12">其他</div>
                        <div class="xy-button xy-button-13">蓄电池模块采用点过温告警</div>
                        <div class="xy-button xy-button-14">电池反接故障</div>
                        <div class="xy-button xy-button-15">BMS通讯异常</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${website_root}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${website_root}/static/js/raphael.min.js"></script>
    <script type="text/javascript" src="${website_root}/static/js/xiyuanweb.js"></script>
    <script type="text/javascript" src="${website_root}/static/js/charging-pile.js"></script>
</body>
</html>