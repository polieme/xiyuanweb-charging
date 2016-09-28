package com.xiyuanweb.web.controller.monitoring;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import com.xiyuanweb.jfinal.controller.XyController;
import com.xiyuanweb.jfinal.validator.annotation.RequestParams;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hongcy on 2016/9/2.
 *
 */
public class OperationController extends XyController
{
    private static final String cityId = "110000";
    /**
     * 返回参数 充电站充电量 充电人次
     * daily_charge是当日充电情况 all_charge 累计充电情况
     * charge_quantity 充电电量
     * charge_amount 充电人次
     */
    public void get_charge()
    {
        Date             now         = new Date();
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyy-MM-dd");
        String           nowStr      = sdf.format(now);
        StringBuffer sqlDaily = new StringBuffer();
        sqlDaily.append("      Select");
        sqlDaily.append("      	round(SUM(A.quantity / 100), 2) charge_quantity,");
        sqlDaily.append("      	COUNT(*) charge_amount");
        sqlDaily.append("      FROM");
        sqlDaily.append("      	t_charging A");
        sqlDaily.append("      WHERE");
        sqlDaily.append("      	A.start_time LIKE '"+nowStr+"%'");
        sqlDaily.append("      AND A.DEL = 0");
        sqlDaily.append("      AND A.quantity / 100 < 200");
        sqlDaily.append("      AND A.valid = 0");
        sqlDaily.append("      AND A.quantity !=0");
        sqlDaily.append("      AND A.user_id != '3333333333333333'");
        Record           recordDaily = Db.findFirst(sqlDaily.toString());

        StringBuffer sqlAll = new StringBuffer();
        sqlAll.append("       SELECT");
        sqlAll.append("       	round(SUM(A.quantity / 100), 2) charge_quantity,");
        sqlAll.append("       	COUNT(*) charge_amount");
        sqlAll.append("       FROM");
        sqlAll.append("       	t_charging A");
        sqlAll.append("       WHERE");
        sqlAll.append("       A.DEL = 0");
        sqlAll.append("       AND A.quantity / 100 < 200");
        sqlAll.append("       AND A.valid = 0");
        sqlAll.append("       AND A.quantity !=0");
        sqlAll.append("       AND A.user_id != '3333333333333333'");
        Record              recordAll = Db.findFirst(sqlAll.toString());
        Map<String, Object> result    = new HashMap<>();
        if (null != recordDaily)
        {
            result.put("daily_charge_quantity", recordDaily.get("charge_quantity"));
            result.put("daily_charge_amount", recordDaily.get("charge_amount"));
        }
        if (null != recordAll)
        {
            result.put("all_charge_quantity", recordAll.get("charge_quantity"));
            result.put("all_charge_amount", recordAll.get("charge_amount"));
        }

        super.respJsonObject(result);
    }

    /*
    *统计7日内充电量 充电人次
    * 传入参数：type （1统计电量，2统计人次）
     */
    @RequestParams({ "*String:type" })
    public void get_statictis_charge()
    {
        String       type      = getPara("type");
        StringBuffer sqlForStartTime = new StringBuffer();
        sqlForStartTime.append("     SELECT");
        sqlForStartTime.append("     	LEFT (A.start_time, 10) stats_day,");
        sqlForStartTime.append("     	ROUND(SUM(A.QUANTITY/100),2) charge_quantity,");
        sqlForStartTime.append("     	SUM(");
        sqlForStartTime.append("     		CASE");
        sqlForStartTime.append("     		WHEN B.user_name IS NULL");
        sqlForStartTime.append("     		AND quantity IS NOT NULL THEN");
        sqlForStartTime.append("     			1");
        sqlForStartTime.append("     		ELSE");
        sqlForStartTime.append("     			0");
        sqlForStartTime.append("     		END");
        sqlForStartTime.append("     	) card_time,");
        sqlForStartTime.append("     	SUM(");
        sqlForStartTime.append("     		CASE");
        sqlForStartTime.append("     		WHEN B.user_name IS NULL THEN");
        sqlForStartTime.append("     			0");
        sqlForStartTime.append("     		ELSE");
        sqlForStartTime.append("     			1");
        sqlForStartTime.append("     		END");
        sqlForStartTime.append("     	) code_time");
        sqlForStartTime.append("     FROM");
        sqlForStartTime.append("     	t_charging A");
        sqlForStartTime.append("     LEFT JOIN t_user_person B ON A.user_id = B.userid");
        sqlForStartTime.append("     WHERE");
        sqlForStartTime.append("     	A.quantity / 100 < 200");
        sqlForStartTime.append("     AND A.quantity != 0");
        sqlForStartTime.append("     AND A.DEL = 0");
        sqlForStartTime.append("     AND A.VALID = 0");
        sqlForStartTime.append("     AND A.user_id != '3333333333333333'");
        sqlForStartTime.append("     AND LEFT (A.start_time, 10) IN (");
        sqlForStartTime.append("     	DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -1 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -2 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -3 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -4 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -5 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -6 DAY),");
        sqlForStartTime.append("     DATE_ADD(SUBSTR(NOW() FROM 1 FOR 10),INTERVAL -7 DAY)");
        sqlForStartTime.append("     )");
        sqlForStartTime.append("     GROUP BY");
        sqlForStartTime.append("     	LEFT (A.start_time, 10)");
        sqlForStartTime.append("     ORDER BY");
        sqlForStartTime.append("     	LEFT (A.start_time, 10) DESC;");
        List<Record> list = null;
        if(("1").equals(type)){
             list      = Db.find(sqlForStartTime.toString());
        }else if(("2").equals(type)){
             list      = Db.find(sqlForStartTime.toString());
        }
        List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
        if (list != null)
        {
            for (Record record : list)
            {
                Map<String, Object> result  = new HashMap<String, Object>();
                String              statDay = record.get("stats_day").toString();
                result.put("statDay", statDay);
                if ("1".equals(type))
                {
                    String chargeQuantity = record.get("charge_quantity").toString();
                    result.put("chargeQuantity", chargeQuantity);
                } else if ("2".equals(type))
                {
                    String card_time = record.get("card_time").toString();
                    String code_time = record.get("code_time").toString();
                    result.put("card_time", card_time);
                    result.put("code_time",code_time);
                }
                resultlist.add(result);
            }
        }

        super.respJsonObject(resultlist);
    }

    /**
     * 得到充电站和充电桩的数量
     * station_count 充电站数量
     * pile_count 充电站数量
     */
    public void get_count()
    {
        String              sqlCountStation    = "SELECT count(*) count FROM t_charging_station WHERE city_id = '110000' and operator_name = '华商三优'";
        String              sqlCountPile       = "SELECT count(*) count FROM t_charging_pile  WHERE FACTOTRY = '华商三优'  and city_id ='110000' and del=0 and run_status not in (0,-1)" ;
        Record              recordCountStation = Db.findFirst(sqlCountStation);
        Record              recordCountPile    = Db.findFirst(sqlCountPile);
        Map<String, Object> result             = new HashMap<>();
        if (recordCountStation != null)
        {
            result.put("station_count", recordCountStation.get("count"));
        }
        if (recordCountPile != null)
        {
            result.put("pile_count", recordCountPile.get("count"));
        }
        super.respJsonObject(result);
    }

    /*
    *得到所有充电桩状态
    * 桩运行状态：1：空闲桩 ，2：只连接未充电，3：充电进行中, 4：GPRS通讯中断,5：检修中,6：预约，7：故障
    * run_status count
     */
    public void get_pile_status()
    {
        String sqlPile = "select run_status,count(1) count from t_charging_pile " + "where del='0' and city_id='"+cityId+"' and run_status not in ('-1','0') and factotry = '华商三优' group by run_status";
        List<Record> pieStatusList = Db.find(sqlPile);
        List<Map<String,Object>> resultList = toListMap(pieStatusList);
        super.respJsonObject(resultList);
    }

    /*
    *得到故障为结局的充电桩信息
    * pile_name充电桩名称
    * fault_code故障代码;0:急停故障；1:电表故障；2:接触器故障；3：读卡器故障；4:内部过温故障；5:连接器故障；6:绝缘故障；7:其他；
    * err_code错误代码；0:电流异常；1:电压异常；2:其他
    * err_status错误状态；1:故障；2：错误
    * record_time 故障时间戳
    *得到预警信息
    */
    public void get_fault()
    {
        Date             now         = new Date();
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyy-MM-dd");
        String           nowStr      = sdf.format(now);
        String sqlFault = "select s.pile_name,t.fault_code,t.err_code,t.err_status,t.record_time from t_fault t,t_charging_pile s "
                + "where t.pile_id = s.id and solve_status = '1' and s.del='0' and s.city_id='"+cityId+"' and solve_status='1' and t.record_time like '"+nowStr+"%'";
        List<Record> faultList = Db.find(sqlFault);
        List<Map<String,Object>> resultlist = toListMap(faultList);
        super.respJsonObject(resultlist);
    }

    public List<Map<String, Object>> toListMap(List<Record> list)
    {
        List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
        if(list!=null){
            for (Record record : list){
                Map<String, Object> result = new HashMap<String,Object>();
                for(String key:record.getColumnNames()){
                    result.put(key,record.get(key));
                }
                resultlist.add(result);
            }
        }

        return resultlist;
    }

}
