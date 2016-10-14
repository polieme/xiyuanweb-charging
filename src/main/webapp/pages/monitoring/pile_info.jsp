<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <title>设备监控中心-充电运营监控中心</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${website_root}/static/css/pile_info.css">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <%
        //充电站ID
        String stationId = request.getParameter("stationId");
        //充电桩类型
        String type = request.getParameter("type");
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    %>
</head>
<body>
<div id="xy-page">
    <%@include file="/pages/inc/page_header.jsp" %>
    <div id="xy-body">
        <div id="xy-row-2">
            <div class="xy-title">直流充电桩</div>
            <div class="xy-sum">合计：3763&nbsp;台</div>
            <div id="xy-row-2-left">
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${website_root}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/amcharts.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/serial.js"></script>
<script type="text/javascript" src="${website_root}/static/amcharts/pie.js"></script>
<script type="text/javascript" src="${website_root}/static/js/xiyuanweb.js"></script>
<script type="text/javascript" src="${website_root}/static/js/pile_info.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
    //获取项目的根路径
    var basePath = "<%=basePath%>";
    //充电站ID，此处有坑请慎重
    var stationId = "<%=stationId%>";
    initPileGrid(stationId,<%=type%>);
</script>
</body>
</html>