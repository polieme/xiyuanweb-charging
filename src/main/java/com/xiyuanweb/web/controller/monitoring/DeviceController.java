package com.xiyuanweb.web.controller.monitoring;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.xiyuanweb.jfinal.controller.XyController;
import com.xiyuanweb.jfinal.validator.annotation.RequestParams;
import com.xiyuanweb.web.controller.Common;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单个充电桩参数
 * Created by hongcy on 2016/9/2.
 */
public class DeviceController extends XyController
{
    /*
    * 输入参数 充电桩id
    * 输出参数
    * pile
    * pile_name,
    * pile_type,桩类型（直流充电桩为“ 1”，交流充电桩为“ 2” ，无线充电桩为“ 3”，换电工位为“ 4”，5：一体桩）
    * run_status 桩运行状态：1：空闲桩 ，2：只连接未充电，3：充电进行中, 4：GPRS通讯中断,5：检修中,6：预约，7：故障
    *  实时充电情况 charging{ 输出电流	current,输出电压	voltage,运行状态	connection_status,充电电量 charge_energy}
    *  充电订单 charging_pay{充电时长 charge_time，充电电量	quantity，充电总价	charge_price}
    *  故障 charging_fault
    * {fault_code故障代码;0:急停故障；1:电表故障；2:接触器故障；3：读卡器故障；4:内部过温故障；5:连接器故障；6:绝缘故障；7:其他；
    * err_code错误代码；0:电流异常；1:电压异常；2:其他
    * err_status错误状态；1:故障；2：错误
    * record_time 故障时间戳}
    * */
    @RequestParams({
        "*String:id"
    })
    public void get_detail(){
        String id = getPara("id");
        Map pileStatusMap = new HashMap();
        String sql = " select pile_name,pile_type,run_status from t_charging_pile" +
                " where del = '0' and id = ?";
        String sqlRt     = " select current,voltage,connection_status,charge_energy from t_charging_pile_rt where pile_id=? ";
        String sqlCharge = "select (end_time-start_time) as charge_time,quantity,charge_price from t_charging " +
                "where pile_id = ? and del = '0' order by create_time";
        String sqlFault = "select t.fault_code,t.err_code,t.err_status,t.record_time from t_fault t " +
                "where t.pile_id = ? and solve_status = '1'";

        //获取充电桩的运行编码，从接口中获取充电桩的充电状态数据
        String getPileCodeSql = "SELECT pile_code,pile_name,pile_type FROM t_charging_pile A WHERE A.id = ?";
        Record pileCodeRd = Db.findFirst(getPileCodeSql,id);
        String pileCode = pileCodeRd.getStr("pile_code");//充电桩运行编码
        String pileName = pileCodeRd.getStr("pile_name");//充电桩名称
        String pileType = pileCodeRd.getInt("pile_type")+"";//充电桩类型
        String pileRslt = Common.getPileStatus(pileCode);
        try {
            JSONObject pileStatusObj = new JSONObject(pileRslt);
            pileStatusMap.put("pile_name",pileName);//充电桩名称
            pileStatusMap.put("pile_type",pileType);//充电桩类型
            pileStatusMap.put("run_status",pileStatusObj.getJSONObject("result").getString("status"));//当前运行状态
            pileStatusMap.put("current",pileStatusObj.getJSONObject("result").getString("current"));//当前电流
            pileStatusMap.put("voltage",pileStatusObj.getJSONObject("result").getString("voltage"));//充电电压
            pileStatusMap.put("chargingDuration",pileStatusObj.getJSONObject("result").getString("chargingDuration"));//当前充电时长
            pileStatusMap.put("curElect",pileStatusObj.getJSONObject("result").getString("curElect"));//当前充电电量
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Record             pile = Db.findFirst(sql,id);
        Record             cdzt   = Db.findFirst(sqlRt,id);
        Record             cddd   = Db.findFirst(sqlCharge,id);
        Record             gzzt   = Db.findFirst(sqlFault,id);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("pile",pileStatusMap);
        result.put("charging",toMapResult(cdzt));
        result.put("charging_pay",toMapResult(cddd));
        result.put("charging_fault",toMapResult(gzzt));
        super.respJsonObject(result);
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
}
