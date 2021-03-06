package com.xiyuanweb.web.controller.monitoring;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xiyuanweb.jfinal.controller.XyController;
import com.xiyuanweb.jfinal.validator.annotation.RequestParams;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

import com.xiyuanweb.web.controller.Common;
import org.json.JSONObject;

/**
 * 第二个界面
 * Created by chenxl on 2016/9/3.
 */

public class StationController extends XyController
{
    private static final String cityId = "110000";
    /*
    *各个充电站充电电量
    * quantity充电量
    * charging_people 充电人次
    * */

    @RequestParams({
            "*String:id"
    })
    public void get_station_charge(){
        String stationId = getPara("id");
        Date             now         = new Date();
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyy-MM-dd");
        String           nowStr      = sdf.format(now);
        String sql = "SELECT sum(quantity/100) quantity from t_charging A where A.quantity/100<200 and A.pile_id IN (select  id from t_charging_pile a where station_id = '"+stationId+"')";

        String sqlPileCount = "SELECT count(*) count,run_status FROM t_charging_pile where station_id = '"+stationId+"' AND run_status not in('0','-1') AND DEL=0 GROUP BY run_status";

        String sqljlCount = " SELECT count(*) count,pile_type FROM t_charging_pile where station_id = '"+stationId+"' AND DEL = 0 GROUP BY pile_type";

        String sqlForDay = "SELECT SUM(A.quantity/100) quantity FROM t_charging A WHERE A.DEL = 0 AND A.valid = 0  AND create_time LIKE '"+nowStr+"%' AND A.quantity/100 <200 AND A.pile_id IN (" +
                "SELECT ID FROM t_charging_pile  B WHERE B.del=0 AND B.station_id = '"+stationId+"')";
        Record record = Db.findFirst(sql);
        List<Record> dayRecord = Db.find(sqlForDay);
        List<Record> pileCount = Db.find(sqlPileCount);
        List<Record> jlCount = Db.find(sqljlCount);
        Map<String,Object> result = new HashMap<String,Object>();
        if(record!=null){
            result.put("base",toMapResult(record));
        }
        if(dayRecord!=null){
            result.put("baseDay",toListMap(dayRecord));
        }
        if(pileCount!=null){
            result.put("pileStatus",toListMap(pileCount));
        }
        if(jlCount!=null){
            result.put("jlstatus",toListMap(jlCount));
        }
        super.respJsonObject(result);
    }

    public void get_all_station_charge(){
        String sql = "select m.id,m.station_name,sum(quantity) quantity,count(1) charging_people from " +
                "(select * from t_charging_station where del = '0' and city_id="+cityId+") m," +
                "t_station_pile s," +
                "(select * from t_charging where del = '0')t " +
                "where s.pile_id=t.pile_id and m.id=s.station_id";
        Record record = Db.findFirst(sql);
        super.respJsonObject(toMapResult(record));
    }

    /*
    * 得到三十日内订单
    * order_state 订单状态0：待处理 ;1：同意 ;2：拒绝 ;3：取消;4:用户充完电未支付;5:用户充完电并支付;6:订单过期未作任何操作 ;7:已评价；8充电中(gprs桩）
    * */

    @RequestParams(
            "*String:stationId"
    )
    public void get_30days_order(){
        String stationId = getPara("stationId");//充电站的stationId
        Date now = new Date();
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -30);
        Date beginDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer sql = new StringBuffer();
        sql.append("select order_state,count(1) count from t_order where del = '0' and order_time > ? AND station_id!=''");
            if(!"".equals(stationId)) {
                sql.append(" AND station_id = '" + stationId + "'");
            }
        sql.append("group by order_state");

        List<Record> list = Db.find(sql.toString(),sdf.format(beginDate));
        super.respJsonObject(toListMap(list));
    }

    /*
    *得到直流交流充电桩充电详情
    * 桩类型（直流充电桩为“ 1”，交流充电桩为“ 2” ，无线充电桩为“ 3”，换电工位为“ 4”，5：一体桩）
    * */
    @RequestParams({
            "*String:type"
    })
    public void get_alternating_direct(){
        String type = getPara("type");
        String stationId = getPara("stationId");//获取充电站的ID
        String statusStr = "";
        String dy = "";
        String dl = "";
        String soc = "";
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" 	id, ");
        sql.append(" 	pile_name, ");
        sql.append(" 	run_status, ");
        sql.append(" 	pile_code ");
        sql.append(" FROM ");
        sql.append(" 	t_charging_pile ");
        sql.append(" WHERE ");
        sql.append(" 	del = '0' ");
        sql.append(" AND pile_type =? ");
        sql.append(" AND city_id = "+cityId+" ");
        if(!"".equals(stationId)) {
            sql.append(" AND station_id = '" + stationId + "' ");
        }
        sql.append(" AND station_id!=''");
        List<Record> list =  Db.find(sql.toString(),type);
        for(Record record:list){
            String id = record.getStr("id");
            String pileName = record.getStr("pile_name");
            String pile_code = record.getStr("pile_code");
            //使用HTTP请求从M2M中读取数据
            String result1 = "";
            BufferedReader in = null;
            try {
                String urlNameString = "http://192.168.10.18:3152/m2m/cgi-bin/pile/now_status/"+pile_code;
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result1 += line;
                    JSONObject a = new JSONObject(result1);
                    if(a.getJSONObject("result").getString("status")!="-1") {//为-1的时候，表示充电桩状态查询不到
                        dy = a.getJSONObject("result").getString("voltage");//电压
                        dl = a.getJSONObject("result").getString("current");//电流
                        soc = a.getJSONObject("result").getString("soc");//SOC
                        statusStr = a.getJSONObject("result").getString("connectStatus");//充电桩的连接状态
                    }
                }

            } catch (Exception e) {
                System.out.println("发送GET请求出现异常！" + e);
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("id",id);
            result.put("pile_name",pileName);
            result.put("status",statusStr);
            result.put("dy",dy);
            result.put("dl",dl);
            result.put("soc",soc);
            result.put("charging_fault","");
            resultList.add(result);
        }
        super.respJsonObject(resultList);
    }


    /*
    *
    *支付统计 按时间
    * 未付款
    *有效订单
    * 0:未支付；1:支付中；2代表已支付
    * */
    @RequestParams({
            "*String:type"
    })
    public void get_pay_static(){
        String type = getPara("type");
        String sql = "SELECT SUBSTRING(create_time,1,10),COUNT(1) COUNT FROM t_charging " +
                "WHERE pay_flag= ? and del = '0'" +
                "GROUP BY SUBSTRING(create_time,1,10) " +
                "ORDER BY SUBSTRING(create_time,1,10) DESC LIMIT 0,7";
        List<Record> list = Db.find(sql,type);
        super.respJsonObject(toListMap(list));
    }
    /*
    *订单统计 按时间
    * */
    public void get_order_static(){
        String sql = "SELECT SUBSTRING(order_time,1,10),COUNT(1) COUNT FROM t_order " +
                "WHERE del = '0'" +
                "GROUP BY SUBSTRING(order_time,1,10) " +
                "ORDER BY SUBSTRING(order_time,1,10) DESC LIMIT 0,7";
        List<Record> list = Db.find(sql);
        super.respJsonObject(toListMap(list));
    }

    /*
    *金钱统计 按时间
    * */
    public void get_money_static(){
        String sql = "SELECT SUBSTRING(create_time,1,10),sum(charge_price) FROM t_charging " +
                "WHERE pay_flag= '2' and del = '0'" +
                "GROUP BY SUBSTRING(create_time,1,10) " +
                "ORDER BY SUBSTRING(create_time,1,10) DESC LIMIT 0,7";
        List<Record> list = Db.find(sql);
        super.respJsonObject(toListMap(list));
    }

    public List<Map<String, Object>> toListMap(List<Record> list)
    {
        List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
        if(list!=null){
            for (Record record : list)
            {
                Map<String, Object> result = new HashMap<String,Object>();
                for(String key:record.getColumnNames()){
                    result.put(key,record.get(key));
                }
                resultlist.add(result);
            }
        }

        return resultlist;
    }

    public Map<String,Object> toMapResult(Record record){
        Map<String, Object> result = new HashMap<String,Object>();
        if(record!=null){
            for(String key:record.getColumnNames()){
                result.put(key,record.get(key));
            }
        }
        return result;
    }

    /**
     * 获取近30天充电次数
     */
    @RequestParams({
            "*String:stationId"
    })
    public void get_30days_chargetimes(){
        String stationId = getPara("stationId");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" 	TEMP_DATA1.datedays,");
        sql.append(" 	TEMP_DATA1.skcdrc,");
        sql.append(" 	TEMP_DATA1.smcdrc,");
        sql.append(" 	TEMP_DATA2.yxdd,");
        sql.append(" 	TEMP_DATA2.cdsr,");
        sql.append(" 	TEMP_DATA2.wjsfy");
        sql.append(" FROM");
        sql.append(" 	(");
        sql.append(" 		SELECT");
        sql.append(" 			DATE_FORMAT(create_time, '%m-%d') datedays,");
        sql.append(" 			SUM(");
        sql.append(" 				CASE");
        sql.append(" 				WHEN order_id = 0");
        sql.append(" 				AND user_id = 3333333333333333 THEN");
        sql.append(" 					1");
        sql.append(" 				ELSE");
        sql.append(" 					0");
        sql.append(" 				END");
        sql.append(" 			) skcdrc,");
        sql.append(" 			SUM(");
        sql.append(" 				CASE");
        sql.append(" 				WHEN order_id != 0 THEN");
        sql.append(" 					1");
        sql.append(" 				ELSE");
        sql.append(" 					0");
        sql.append(" 				END");
        sql.append(" 			) smcdrc");
        sql.append(" 		FROM");
        sql.append(" 			t_charging");
        sql.append(" 		WHERE");
        sql.append(" 			create_time > DATE_ADD(NOW(), INTERVAL - 30 DAY)");
        sql.append(" 		AND del = 0");
        sql.append(" 		AND valid = 0 AND PILE_ID IN (SELECT id FROM t_charging_pile WHERE station_id = '"+stationId+"')");
        sql.append(" 		GROUP BY");
        sql.append(" 			DATE_FORMAT(create_time, '%m-%d')");
        sql.append(" 	) TEMP_DATA1");
        sql.append(" LEFT JOIN (");
        sql.append(" 	SELECT");
        sql.append(" 		DATE_FORMAT(create_time, '%m-%d') datedays,");
        sql.append(" 		COUNT(1) yxdd,");
        sql.append(" 		SUM(");
        sql.append(" 			CASE");
        sql.append(" 			WHEN A.order_state = 5 THEN");
        sql.append(" 				A.charge_price");
        sql.append(" 			ELSE");
        sql.append(" 				0");
        sql.append(" 			END");
        sql.append(" 		) cdsr,");
        sql.append(" 		SUM(");
        sql.append(" 			CASE");
        sql.append(" 			WHEN A.order_state = 4 THEN");
        sql.append(" 				A.charge_price");
        sql.append(" 			ELSE");
        sql.append(" 				0");
        sql.append(" 			END");
        sql.append(" 		) wjsfy");
        sql.append(" 	FROM");
        sql.append(" 		t_order A");
        sql.append(" 	WHERE");
        sql.append(" 		A.order_state != 8");
        sql.append(" 	AND create_time > DATE_ADD(NOW(), INTERVAL - 30 DAY) AND PILE_ID IN (SELECT id FROM t_charging_pile WHERE station_id = '"+stationId+"')");
        sql.append(" 	GROUP BY");
        sql.append(" 		DATE_FORMAT(create_time, '%m-%d')");
        sql.append(" ) TEMP_DATA2 ON TEMP_DATA1.datedays = TEMP_DATA2.datedays ORDER BY TEMP_DATA1.datedays desc");

        List<Record> list = Db.find(sql.toString());
        super.respJsonObject(toListMap(list));
    }

    /**
     * 获取交直流充电桩的数量
     */
    @RequestParams({
            "*String:stationId"
    })
    public void initJzlCount(){
        String stationId = getPara("stationId");
        stationId = stationId==null?"":stationId;
        StringBuffer qryJzlCountSql = new StringBuffer();
        qryJzlCountSql.append(" SELECT     ");
        qryJzlCountSql.append(" 	ZL.zlCount, ");
        qryJzlCountSql.append(" 	JL.jlCount ");
        qryJzlCountSql.append(" FROM ");
        qryJzlCountSql.append(" 	( ");
        qryJzlCountSql.append(" 		SELECT ");
        qryJzlCountSql.append(" 			COUNT(1) zlCount ");
        qryJzlCountSql.append(" 		FROM ");
        qryJzlCountSql.append(" 			t_charging_pile A ");
        qryJzlCountSql.append(" 		WHERE 1=1");
        if(!"".equals(stationId)) {
            qryJzlCountSql.append("  AND	A.station_id = '" + stationId + "' ");
        }
        qryJzlCountSql.append(" 		AND A.del = 0 ");
        qryJzlCountSql.append(" 		AND A.pile_type = 1 ");
        qryJzlCountSql.append(" 	) ZL ");
        qryJzlCountSql.append(" LEFT JOIN ( ");
        qryJzlCountSql.append(" 	SELECT ");
        qryJzlCountSql.append(" 		COUNT(1) jlCount ");
        qryJzlCountSql.append(" 	FROM ");
        qryJzlCountSql.append(" 		t_charging_pile A ");
        qryJzlCountSql.append(" 	WHERE  1=1");
        if(!"".equals(stationId)) {
            qryJzlCountSql.append("  AND	A.station_id = '" + stationId + "' ");
        }
        qryJzlCountSql.append(" 	AND A.del = 0 ");
        qryJzlCountSql.append(" 	AND A.pile_type = 2 ");
        qryJzlCountSql.append(" ) JL ON 1 = 1 ");

        List<Record> list = Db.find(qryJzlCountSql.toString());
        super.respJsonObject(toListMap(list));
    }

    /*
    *查询充电站列表
    * @param initCount:第几页
    * @date 2016-11-2
    * @author zhangpeng
    * @description 2016-11-15 增加列表显示运营商下面的充电站
    * */
    @RequestParams({
            "*String:initCount",
            "*String:stationType"
    })
    public void getStationList(){
        int initCount = Integer.parseInt(getPara("initCount"));
        String stationType = getPara("stationType");//获取充电站类型
        String operationId = getPara("operationId");//运营商ID

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" 	A.id, ");
        sql.append(" 	station_name, ");
        sql.append(" 	cover_crop_img, ");
        sql.append(" 	camera_serial ");
        sql.append(" FROM ");
        sql.append(" 	t_charging_station A ");
        sql.append(" LEFT JOIN t_pile_camera B ON B.station_id = A.id ");
        sql.append(" WHERE ");
        sql.append(" 	del = 0 ");

        if(operationId!=null) {//增加运营商显示对应的菜单
            sql.append(" AND A.operator_id = '" + operationId + "'");
        }
        sql.append(" AND A.id IN ( ");
        sql.append(" 	SELECT ");
        sql.append(" 		station_id ");
        sql.append(" 	FROM ");
        sql.append(" 		t_charging_pile ");
        sql.append(" ) ");

        //如果选择的是优易充小站
        if(stationType!=null && stationType.equals("2")){
            sql.append(" AND A.type = 4");
        }

        sql.append(" ORDER BY ");
        sql.append(" 	(direct_num + alter_num) ");/*根据系统中录入的交直流桩进行排序*/
        sql.append(" LIMIT "+(initCount-1)*16+",16");
        List<Record> list = Db.find(sql.toString());
        super.respJsonObject(toListMap(list));
    }

    /**
     * 根据充电站的ID获取充电站的数据（交直流充电桩数量、当日充电电量、累计充电电量、空闲、忙碌、故障、离线数量）
     */
    @RequestParams({
            "*String:stationId"
    })
    public void getStationInfo(){
        String stationId =getPara("stationId");
        //查询交直流桩的数量
        String qryJZlPileCountSql = "SELECT (SUM(pile_type)-COUNT(*))JL_COUNT,(2*COUNT(*)-SUM(pile_type))ZL_COUNT FROM t_charging_pile WHERE station_id = '"+stationId+"' AND DEL = 0";
        //查询当日充电站充电电量
        String todayChargeQuantitySql = "SELECT SUM(C.quantity)/100 TODAY_QUANTITY FROM t_charging_pile P LEFT JOIN t_charging C ON P.id = C.pile_id AND C.del = 0 AND C.valid = 0 AND DATE(C.create_time) = DATE(NOW()) WHERE P.del = 0 AND P.station_id = '"+stationId+"'";
        //查询充电站所有充电电量
        String totalChargeQuantitySql = "SELECT SUM(C.quantity)/100 TOTAL_QUANTITY FROM t_charging_pile P LEFT JOIN t_charging C ON P.id = C.pile_id AND C.del = 0 AND C.valid = 0 WHERE P.del = 0 AND P.station_id = '"+stationId+"'";
        //查询充电桩的状态
        StringBuffer qryPileStatusSql = new StringBuffer();
        //空闲充电桩数量
        qryPileStatusSql.append(" SELECT ");
        qryPileStatusSql.append(" 	'FREE_COUNT' PILE_STATUS,COUNT(1) STATUS_COUNT ");
        qryPileStatusSql.append(" FROM ");
        qryPileStatusSql.append(" 	t_charging_pile A ");
        qryPileStatusSql.append(" WHERE ");
        qryPileStatusSql.append(" 	A.station_id = '"+stationId+"' ");
        qryPileStatusSql.append(" 	AND A.run_status = 1 AND A.del = 0 ");
        qryPileStatusSql.append(" UNION ALL ");
        //忙碌数量
        qryPileStatusSql.append(" SELECT ");
        qryPileStatusSql.append(" 	'BUSY_COUNT' PILE_STATUS,COUNT(1) STATUS_COUNT ");
        qryPileStatusSql.append(" FROM ");
        qryPileStatusSql.append(" 	t_charging_pile A ");
        qryPileStatusSql.append(" WHERE ");
        qryPileStatusSql.append(" 	A.station_id = '"+stationId+"' ");
        qryPileStatusSql.append(" 	AND A.run_status IN (2,3,6,8) AND A.del = 0 ");
        qryPileStatusSql.append(" UNION ALL ");
        //离线数量
        qryPileStatusSql.append(" SELECT ");
        qryPileStatusSql.append(" 	'OFF_LINE' PILE_STATUS,COUNT(1) STATUS_COUNT ");
        qryPileStatusSql.append(" FROM ");
        qryPileStatusSql.append(" 	t_charging_pile A ");
        qryPileStatusSql.append(" WHERE ");
        qryPileStatusSql.append(" 	A.station_id = '"+stationId+"' ");
        qryPileStatusSql.append(" 	AND A.run_status IN (0,4) AND A.del = 0 ");
        qryPileStatusSql.append(" UNION ALL ");
        //故障数量
        qryPileStatusSql.append(" SELECT ");
        qryPileStatusSql.append(" 	'FAULT' PILE_STATUS,COUNT(1) STATUS_COUNT ");
        qryPileStatusSql.append(" FROM ");
        qryPileStatusSql.append(" 	t_charging_pile A ");
        qryPileStatusSql.append(" WHERE ");
        qryPileStatusSql.append(" 	A.station_id = '"+stationId+"' ");
        qryPileStatusSql.append(" 	AND A.run_status IN (5,7) AND A.del = 0 ");

        Map<String,Object> resultMap = new HashMap();
        //查询交直流桩的数量
        Record jzlPileCountRec = Db.findFirst(qryJZlPileCountSql);
        if(jzlPileCountRec!=null){
            resultMap.put("JL_COUNT",jzlPileCountRec.getBigDecimal("JL_COUNT")==null?0:jzlPileCountRec.getBigDecimal("JL_COUNT"));
            resultMap.put("ZL_COUNT",jzlPileCountRec.getBigDecimal("ZL_COUNT")==null?0:jzlPileCountRec.getBigDecimal("ZL_COUNT"));
        }
        //查询当日充电站充电电量
        Record todayChargeQuantityRec = Db.findFirst(todayChargeQuantitySql);
        if(todayChargeQuantityRec!=null){
            resultMap.put("TODAY_QUANTITY",todayChargeQuantityRec.getBigDecimal("TODAY_QUANTITY")==null?0:todayChargeQuantityRec.getBigDecimal("TODAY_QUANTITY"));
        }
        //查询充电站总充电电量
        Record totalChargeQuantityRec = Db.findFirst(totalChargeQuantitySql);
        if(totalChargeQuantityRec!=null){
            resultMap.put("TOTAL_QUANTITY",totalChargeQuantityRec.getBigDecimal("TOTAL_QUANTITY")==null?0:totalChargeQuantityRec.getBigDecimal("TOTAL_QUANTITY"));
        }
        //查询充电站中所有充电桩的充电状态
        List<Record> qryPileStatusLst = Db.find(qryPileStatusSql.toString());
        if(qryPileStatusLst!=null && qryPileStatusLst.size()>0){
            resultMap.put("FREE_COUNT",qryPileStatusLst.get(0).get("STATUS_COUNT"));
            resultMap.put("BUSY_COUNT",qryPileStatusLst.get(1).get("STATUS_COUNT"));
            resultMap.put("OFF_LINE",qryPileStatusLst.get(2).get("STATUS_COUNT"));
            resultMap.put("FAULT",qryPileStatusLst.get(3).get("STATUS_COUNT"));
        }
        super.respJsonObject(resultMap);
    }

    /*
    *查询充电站数量
    * @date 2016-11-2
    * @author zhangpeng
    * */
    @RequestParams({
            "*String:stationType"
    })
    public void getStationCount() {
        String stationType = getPara("stationType");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" COUNT(*) STATION_COUNT");
        sql.append(" FROM");
        sql.append(" t_charging_station");
        sql.append(" WHERE");
        sql.append(" del = 0");

        //如果选择的优易充小站，显示对应优易充小站的数量
        if(stationType!=null && stationType.equals("2")){
            sql.append(" AND type = 4");
        }


        List<Record> list = Db.find(sql.toString());
        super.respJsonObject(toListMap(list));
    }

    //获取充电站摄像头的图片信息
    @RequestParams({
            "*String:cameraSerial"
    })
    public void getStationCameraPic(){

        String accessTokenStr = "";
        String imgUrl = "";
        JSONObject imgUrlJson = null;
        String cameraSerial = getPara("cameraSerial");//摄像头的序列号
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("appKey","b11f6c3ce9ca4e8e9b9665ed5456f734");
        paramMap.put("appSecret","ba44857bdc50145adeae21b3c7ccb0fe");
        //获取accessToken
        String accessToken = Common.postAction("https://open.ys7.com/api/lapp/token/get",paramMap);
        try{
            JSONObject accessTokenJson = new JSONObject(accessToken);
            accessTokenStr = accessTokenJson.getJSONObject("data").getString("accessToken");
        }catch (Exception e){
            System.out.print("解析json出现异常");
            e.printStackTrace();
        }


        //根据accessToken获取照片路径
        HashMap<String,String> imgParamMap = new HashMap<String,String>();
        imgParamMap.put("accessToken",accessTokenStr);
        imgParamMap.put("deviceSerial",cameraSerial);
        imgParamMap.put("channelNo","1");
        String imgUrlJsonStr = Common.postAction("https://open.ys7.com/api/lapp/device/capture",imgParamMap);

        try{
            imgUrlJsonStr = new String(imgUrlJsonStr.getBytes("GBK"),"UTF-8");
            imgUrlJson = new JSONObject(imgUrlJsonStr);
        }catch (Exception e){
            System.out.println("解析Json出现异常");
            e.printStackTrace();
        }
        System.out.println(imgUrlJsonStr);
        super.respJsonObject(imgUrlJsonStr);
    }
}
