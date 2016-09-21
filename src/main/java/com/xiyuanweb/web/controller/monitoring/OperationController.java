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
        String           sqlDaily    = "select round(SUM(A.quantity/100),2) charge_quantity,COUNT(*) charge_amount from t_charging A WHERE A.create_time LIKE '"+nowStr+"%' AND A.DEL = 0 AND A.quantity/100<200 AND A.valid = 0  AND A.quantity !=0";
        String           condition   = "city_id='"+cityId+"' and station_type='0'";
        Record           recordDaily = Db.findFirst(sqlDaily);

        String              sqlAll    = "SELECT round(SUM(A.quantity/100),2) charge_quantity,COUNT(*) charge_amount FROM t_charging A WHERE  A.quantity/100 <200 AND A.del = 0 AND A.valid = 0";
        Record              recordAll = Db.findFirst(sqlAll);
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
        String       sql       = "select stats_day,(charge_quantity/100) charge_quantity,charge_amount from t_charging_station_his where city_id='110000' and station_type='0' order by stats_day desc  limit 0,7";
        List<Record> list      = Db.find(sql);

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
                    String chargeAmount = record.get("charge_amount").toString();
                    result.put("chargeAmount", chargeAmount);
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
        String              sqlCountPile       = "SELECT count(*) count FROM t_charging_pile  WHERE FACTOTRY = '华商三优'  and city_id ='110000' and del=0 and run_status";
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
        String sqlPile = "select run_status,count(1) count from t_charging_pile " + "where del='0' and city_id='"+cityId+"' and run_status not in ('-1','0','8','9') and factotry = '华商三优' group by run_status";
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

}
