<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <title>设备监控中心-充电运营监控中心</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/station_list.css">
</head>
<%
    //获取项目路径
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
<body style="overflow-x: hidden">
<div id="xy-page">
    <%@include file="/pages/inc/page_header.jsp" %>
    <div id="xy-body">
        <div id="xy-row-2">
            <div id="xy-title-1" class="xy-title-1" onclick="chgTitleCorlor(1);initStationList(1)">充电站</div>
            <div id="xy-title-2" class="xy-title-2" onclick="chgTitleCorlor(2);initStationList(2)">优易充小站</div>
            <div class="xy-sum">合计：0&nbsp;座</div>
            <div id="xy-row-2-left">

            </div>
        </div>
    </div>
</div>
<div class="hiddenDiv" onclick="hiddenDiv()"></div>
<div class="camera-pic-div" onclick="hiddenDiv()"></div>
<script type="text/javascript" src="${website_root}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/amcharts.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/serial.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/pie.js"></script>
<script type="text/javascript" src="${website_root}/static/js/xiyuanweb.js"></script>
<script type="text/javascript" src="${website_root}/static/js/station_list.js"></script>
<script type="text/javascript">
</script>
</body>
</html>