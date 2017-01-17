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
     * param:owner_id 运营商ID，前台取出杰升传递的owner_id
     */
    @RequestParams({ "*String:owner_id" })
    public void get_charge()
    {
        String owner_id = getPara("owner_id");
        Date             now         = new Date();
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyy-MM-dd");
        String           nowStr      = sdf.format(now);
        StringBuffer sqlDaily = new StringBuffer();
        //当日充电次数和充电电量已修改-fan-1115
        if(("null").equals(owner_id)||(owner_id).equals(null)){
            sqlDaily.append("     SELECT");
            sqlDaily.append("     	round(SUM(A.quantity / 100), 2) charge_quantity,");
            sqlDaily.append("     	COUNT(*) charge_amount");
            sqlDaily.append("     FROM");
            sqlDaily.append("     	t_charging A");
            sqlDaily.append("     LEFT JOIN t_charging_pile P ON A.pile_id = P.id");
            sqlDaily.append("     LEFT JOIN t_charging_station S ON P.station_id = S.id");
            sqlDaily.append("     WHERE");
            sqlDaily.append("     	S.id IN (");
            sqlDaily.append("     		'Z-000337',");
            sqlDaily.append("     		'1014696943217282473',");
            sqlDaily.append("     		'Z-000289',");
            sqlDaily.append("     		'1014609464013426237',");
            sqlDaily.append("     		'1014582909654734175',");
            sqlDaily.append("     		'1014780645902374216',");
            sqlDaily.append("     		'1014812448114063690',");
            sqlDaily.append("     		'1014544638314972650',");
            sqlDaily.append("     		'1014674383383633496',");
            sqlDaily.append("     		'1014609431783924297',");
            sqlDaily.append("     		'1014609436352157280',");
            sqlDaily.append("     		'Z-000331',");
            sqlDaily.append("     		'1014628483992201542',");
            sqlDaily.append("     		'1014722110543366598',");
            sqlDaily.append("     		'1014817007082332851',");
            sqlDaily.append("     		'1014791031104019850',");
            sqlDaily.append("     		'1014839399215720581',");
            sqlDaily.append("     		'1014830021655280136',");
            sqlDaily.append("     		'1014816999718162630',");
            sqlDaily.append("     		'1014670845618151937',");
            sqlDaily.append("     		'1014829942212615613',");
            sqlDaily.append("     		'1014715012122169150',");
            sqlDaily.append("     		'1014522283054749078',");
            sqlDaily.append("     		'1014640560287229153',");
            sqlDaily.append("     		'1014816974435515978',");
            sqlDaily.append("     		'1014730543156142703',");
            sqlDaily.append("     		'1014817005934923204',");
            sqlDaily.append("     		'1014683933545541360',");
            sqlDaily.append("     		'1014818882031941384',");
            sqlDaily.append("     		'1014816975805482158',");
            sqlDaily.append("     		'1014750354794653029',");
            sqlDaily.append("     		'1014616365142027109',");
            sqlDaily.append("     		'1014752066194649321',");
            sqlDaily.append("     		'1014558544933932365',");
            sqlDaily.append("     		'1014778133328150324',");
            sqlDaily.append("     		'1014746163138559560',");
            sqlDaily.append("     		'1014738389703066130',");
            sqlDaily.append("     		'Z-000081',");
            sqlDaily.append("     		'1014673588906892481',");
            sqlDaily.append("     		'1014800795090896408',");
            sqlDaily.append("     		'1014627768363174690',");
            sqlDaily.append("     		'1014607105341453524',");
            sqlDaily.append("     		'1014812516337077840',");
            sqlDaily.append("     		'1014647446570233596',");
            sqlDaily.append("     		'1014816976398338260',");
            sqlDaily.append("     		'1014543146999497053',");
            sqlDaily.append("     		'1014758939799736352',");
            sqlDaily.append("     		'1014673596526483518',");
            sqlDaily.append("     		'1014675059706467548',");
            sqlDaily.append("     		'1014673588392823481',");
            sqlDaily.append("     		'1014734107495034162',");
            sqlDaily.append("     		'1014816980741925148',");
            sqlDaily.append("     		'1014834051944684061',");
            sqlDaily.append("     		'1014585250436986045',");
            sqlDaily.append("     		'1014579236432938736',");
            sqlDaily.append("     		'1014557676904133174',");
            sqlDaily.append("     		'1014822862655085147',");
            sqlDaily.append("     		'1014510228200401306',");
            sqlDaily.append("     		'1014633902263181569',");
            sqlDaily.append("     		'1014836739974852410',");
            sqlDaily.append("     		'1014570991163685901',");
            sqlDaily.append("     		'1014804026936744803',");
            sqlDaily.append("     		'1014665798432883760',");
            sqlDaily.append("     		'1014733204352388921',");
            sqlDaily.append("     		'1014523250522881568',");
            sqlDaily.append("     		'1014652613374412351',");
            sqlDaily.append("     		'1014648357148278051',");
            sqlDaily.append("     		'1014576929793786051',");
            sqlDaily.append("     		'1014840200031374078',");
            sqlDaily.append("     		'1014507799978950178',");
            sqlDaily.append("     		'1014784899515223057',");
            sqlDaily.append("     		'1014781624510658971',");
            sqlDaily.append("     		'1014784906081499387'");
            sqlDaily.append("     	)");
            sqlDaily.append("     AND A.start_time LIKE '"+nowStr+"%'");
            sqlDaily.append("     AND A.DEL = 0");
            sqlDaily.append("     AND A.quantity / 100 < 200");
            sqlDaily.append("     AND A.valid = 0");
            sqlDaily.append("     AND A.quantity != 0");
            sqlDaily.append("     AND A.user_id != '3333333333333333'");
        }else{
            sqlDaily.append("      SELECT");
            sqlDaily.append("      	round(SUM(A.quantity / 100), 2) charge_quantity,");
            sqlDaily.append("      	COUNT(*) charge_amount");
            sqlDaily.append("      FROM");
            sqlDaily.append("      	t_charging A");
            sqlDaily.append("      LEFT JOIN t_charging_pile B ON A.pile_id = B.id");
            sqlDaily.append("      LEFT JOIN t_charging_station C ON B.station_id = C.id");
            sqlDaily.append("      WHERE");
            sqlDaily.append("      	A.start_time LIKE '"+nowStr+"%'");
            sqlDaily.append("      AND A.DEL = 0");
            sqlDaily.append("      AND A.quantity / 100 < 200");
            sqlDaily.append("      AND A.valid = 0");
            sqlDaily.append("      AND A.quantity != 0");
            sqlDaily.append("      AND A.user_id != '3333333333333333'");
            sqlDaily.append("      AND C.operator_id = '"+owner_id+"'");
        }

        Record           recordDaily = Db.findFirst(sqlDaily.toString());
        //全部充电次数和充电电量已修改-fan-1115
        StringBuffer sqlAll = new StringBuffer();
        if(("null").equals(owner_id)||(owner_id).equals(null)){
            sqlAll.append("        SELECT");
            sqlAll.append("        	round(SUM(A.quantity / 100), 2) charge_quantity,");
            sqlAll.append("        	COUNT(*) charge_amount");
            sqlAll.append("        FROM");
            sqlAll.append("        	t_charging A");
            sqlAll.append("        LEFT JOIN t_charging_pile P ON A.pile_id = P.id");
            sqlAll.append("        LEFT JOIN t_charging_station S ON P.station_id = S.id");
            sqlAll.append("        WHERE");
            sqlAll.append("        	S.id IN (");
            sqlAll.append("        		'Z-000337',");
            sqlAll.append("        		'1014696943217282473',");
            sqlAll.append("        		'Z-000289',");
            sqlAll.append("        		'1014609464013426237',");
            sqlAll.append("        		'1014582909654734175',");
            sqlAll.append("        		'1014780645902374216',");
            sqlAll.append("        		'1014812448114063690',");
            sqlAll.append("        		'1014544638314972650',");
            sqlAll.append("        		'1014674383383633496',");
            sqlAll.append("        		'1014609431783924297',");
            sqlAll.append("        		'1014609436352157280',");
            sqlAll.append("        		'Z-000331',");
            sqlAll.append("        		'1014628483992201542',");
            sqlAll.append("        		'1014722110543366598',");
            sqlAll.append("        		'1014817007082332851',");
            sqlAll.append("        		'1014791031104019850',");
            sqlAll.append("        		'1014839399215720581',");
            sqlAll.append("        		'1014830021655280136',");
            sqlAll.append("        		'1014816999718162630',");
            sqlAll.append("        		'1014670845618151937',");
            sqlAll.append("        		'1014829942212615613',");
            sqlAll.append("        		'1014715012122169150',");
            sqlAll.append("        		'1014522283054749078',");
            sqlAll.append("        		'1014640560287229153',");
            sqlAll.append("        		'1014816974435515978',");
            sqlAll.append("        		'1014730543156142703',");
            sqlAll.append("        		'1014817005934923204',");
            sqlAll.append("        		'1014683933545541360',");
            sqlAll.append("        		'1014818882031941384',");
            sqlAll.append("        		'1014816975805482158',");
            sqlAll.append("        		'1014750354794653029',");
            sqlAll.append("        		'1014616365142027109',");
            sqlAll.append("        		'1014752066194649321',");
            sqlAll.append("        		'1014558544933932365',");
            sqlAll.append("        		'1014778133328150324',");
            sqlAll.append("        		'1014746163138559560',");
            sqlAll.append("        		'1014738389703066130',");
            sqlAll.append("        		'Z-000081',");
            sqlAll.append("        		'1014673588906892481',");
            sqlAll.append("        		'1014800795090896408',");
            sqlAll.append("        		'1014627768363174690',");
            sqlAll.append("        		'1014607105341453524',");
            sqlAll.append("        		'1014812516337077840',");
            sqlAll.append("        		'1014647446570233596',");
            sqlAll.append("        		'1014816976398338260',");
            sqlAll.append("        		'1014543146999497053',");
            sqlAll.append("        		'1014758939799736352',");
            sqlAll.append("        		'1014673596526483518',");
            sqlAll.append("        		'1014675059706467548',");
            sqlAll.append("        		'1014673588392823481',");
            sqlAll.append("        		'1014734107495034162',");
            sqlAll.append("        		'1014816980741925148',");
            sqlAll.append("        		'1014834051944684061',");
            sqlAll.append("        		'1014585250436986045',");
            sqlAll.append("        		'1014579236432938736',");
            sqlAll.append("        		'1014557676904133174',");
            sqlAll.append("        		'1014822862655085147',");
            sqlAll.append("        		'1014510228200401306',");
            sqlAll.append("        		'1014633902263181569',");
            sqlAll.append("        		'1014836739974852410',");
            sqlAll.append("        		'1014570991163685901',");
            sqlAll.append("        		'1014804026936744803',");
            sqlAll.append("        		'1014665798432883760',");
            sqlAll.append("        		'1014733204352388921',");
            sqlAll.append("        		'1014523250522881568',");
            sqlAll.append("        		'1014652613374412351',");
            sqlAll.append("        		'1014648357148278051',");
            sqlAll.append("        		'1014576929793786051',");
            sqlAll.append("        		'1014840200031374078',");
            sqlAll.append("        		'1014507799978950178',");
            sqlAll.append("        		'1014784899515223057',");
            sqlAll.append("        		'1014781624510658971',");
            sqlAll.append("        		'1014784906081499387'");
            sqlAll.append("        	)");
            sqlAll.append("        AND A.DEL = 0");
            sqlAll.append("        AND A.quantity / 100 < 200");
            sqlAll.append("        AND A.valid = 0");
            sqlAll.append("        AND A.quantity != 0");
            sqlAll.append("        AND A.user_id != '3333333333333333'");

        }else{
            sqlAll.append("       SELECT");
            sqlAll.append("       	round(SUM(A.quantity / 100), 2) charge_quantity,");
            sqlAll.append("       	COUNT(*) charge_amount");
            sqlAll.append("       FROM");
            sqlAll.append("       	t_charging A");
            sqlAll.append("      LEFT JOIN t_charging_pile B ON A.pile_id = B.id");
            sqlAll.append("      LEFT JOIN t_charging_station C ON B.station_id = C.id");
            sqlAll.append("       WHERE");
            sqlAll.append("       A.DEL = 0");
            sqlAll.append("       AND A.quantity / 100 < 200");
            sqlAll.append("       AND A.valid = 0");
            sqlAll.append("       AND A.quantity !=0");
            sqlAll.append("       AND A.user_id != '3333333333333333'");
            sqlAll.append("      AND C.operator_id = '"+owner_id+"'");
        }
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
    @RequestParams({ "*String:type","*String:owner_id" })
    public void get_statictis_charge()
    {
        String       type      = getPara("type");
        String       owner_id = getPara("owner_id");
        StringBuffer sqlForStartTime = new StringBuffer();
        if(("null").equals(owner_id)||(owner_id).equals(null)){
            sqlForStartTime.append("       SELECT");
            sqlForStartTime.append("       	LEFT (A.start_time, 10) stats_day,");
            sqlForStartTime.append("       	ROUND(SUM(A.QUANTITY / 100), 2) charge_quantity,");
            sqlForStartTime.append("       	SUM(");
            sqlForStartTime.append("       		CASE");
            sqlForStartTime.append("       		WHEN B.user_name IS NULL");
            sqlForStartTime.append("       		AND quantity IS NOT NULL THEN");
            sqlForStartTime.append("       			1");
            sqlForStartTime.append("       		ELSE");
            sqlForStartTime.append("       			0");
            sqlForStartTime.append("       		END");
            sqlForStartTime.append("       	) card_time,");
            sqlForStartTime.append("       	SUM(");
            sqlForStartTime.append("       		CASE");
            sqlForStartTime.append("       		WHEN B.user_name IS NULL THEN");
            sqlForStartTime.append("       			0");
            sqlForStartTime.append("       		ELSE");
            sqlForStartTime.append("       			1");
            sqlForStartTime.append("       		END");
            sqlForStartTime.append("       	) code_time");
            sqlForStartTime.append("       FROM");
            sqlForStartTime.append("       	t_charging A");
            sqlForStartTime.append("       LEFT JOIN t_user_person B ON A.user_id = B.userid");
            sqlForStartTime.append("       LEFT JOIN t_charging_pile C ON A.pile_id = C.id");
            sqlForStartTime.append("       LEFT JOIN t_charging_station S ON C.station_id = S.id");
            sqlForStartTime.append("       WHERE");
            sqlForStartTime.append("       	S.id IN (");
            sqlForStartTime.append("       		'Z-000337',");
            sqlForStartTime.append("       		'1014696943217282473',");
            sqlForStartTime.append("       		'Z-000289',");
            sqlForStartTime.append("       		'1014609464013426237',");
            sqlForStartTime.append("       		'1014582909654734175',");
            sqlForStartTime.append("       		'1014780645902374216',");
            sqlForStartTime.append("       		'1014812448114063690',");
            sqlForStartTime.append("       		'1014544638314972650',");
            sqlForStartTime.append("       		'1014674383383633496',");
            sqlForStartTime.append("       		'1014609431783924297',");
            sqlForStartTime.append("       		'1014609436352157280',");
            sqlForStartTime.append("       		'Z-000331',");
            sqlForStartTime.append("       		'1014628483992201542',");
            sqlForStartTime.append("       		'1014722110543366598',");
            sqlForStartTime.append("       		'1014817007082332851',");
            sqlForStartTime.append("       		'1014791031104019850',");
            sqlForStartTime.append("       		'1014839399215720581',");
            sqlForStartTime.append("       		'1014830021655280136',");
            sqlForStartTime.append("       		'1014816999718162630',");
            sqlForStartTime.append("       		'1014670845618151937',");
            sqlForStartTime.append("       		'1014829942212615613',");
            sqlForStartTime.append("       		'1014715012122169150',");
            sqlForStartTime.append("       		'1014522283054749078',");
            sqlForStartTime.append("       		'1014640560287229153',");
            sqlForStartTime.append("       		'1014816974435515978',");
            sqlForStartTime.append("       		'1014730543156142703',");
            sqlForStartTime.append("       		'1014817005934923204',");
            sqlForStartTime.append("       		'1014683933545541360',");
            sqlForStartTime.append("       		'1014818882031941384',");
            sqlForStartTime.append("       		'1014816975805482158',");
            sqlForStartTime.append("       		'1014750354794653029',");
            sqlForStartTime.append("       		'1014616365142027109',");
            sqlForStartTime.append("       		'1014752066194649321',");
            sqlForStartTime.append("       		'1014558544933932365',");
            sqlForStartTime.append("       		'1014778133328150324',");
            sqlForStartTime.append("       		'1014746163138559560',");
            sqlForStartTime.append("       		'1014738389703066130',");
            sqlForStartTime.append("       		'Z-000081',");
            sqlForStartTime.append("       		'1014673588906892481',");
            sqlForStartTime.append("       		'1014800795090896408',");
            sqlForStartTime.append("       		'1014627768363174690',");
            sqlForStartTime.append("       		'1014607105341453524',");
            sqlForStartTime.append("       		'1014812516337077840',");
            sqlForStartTime.append("       		'1014647446570233596',");
            sqlForStartTime.append("       		'1014816976398338260',");
            sqlForStartTime.append("       		'1014543146999497053',");
            sqlForStartTime.append("       		'1014758939799736352',");
            sqlForStartTime.append("       		'1014673596526483518',");
            sqlForStartTime.append("       		'1014675059706467548',");
            sqlForStartTime.append("       		'1014673588392823481',");
            sqlForStartTime.append("       		'1014734107495034162',");
            sqlForStartTime.append("       		'1014816980741925148',");
            sqlForStartTime.append("       		'1014834051944684061',");
            sqlForStartTime.append("       		'1014585250436986045',");
            sqlForStartTime.append("       		'1014579236432938736',");
            sqlForStartTime.append("       		'1014557676904133174',");
            sqlForStartTime.append("       		'1014822862655085147',");
            sqlForStartTime.append("       		'1014510228200401306',");
            sqlForStartTime.append("       		'1014633902263181569',");
            sqlForStartTime.append("       		'1014836739974852410',");
            sqlForStartTime.append("       		'1014570991163685901',");
            sqlForStartTime.append("       		'1014804026936744803',");
            sqlForStartTime.append("       		'1014665798432883760',");
            sqlForStartTime.append("       		'1014733204352388921',");
            sqlForStartTime.append("       		'1014523250522881568',");
            sqlForStartTime.append("       		'1014652613374412351',");
            sqlForStartTime.append("       		'1014648357148278051',");
            sqlForStartTime.append("       		'1014576929793786051',");
            sqlForStartTime.append("       		'1014840200031374078',");
            sqlForStartTime.append("       		'1014507799978950178',");
            sqlForStartTime.append("       		'1014784899515223057',");
            sqlForStartTime.append("       		'1014781624510658971',");
            sqlForStartTime.append("       		'1014784906081499387'");
            sqlForStartTime.append("       	)");
            sqlForStartTime.append("       AND A.quantity / 100 < 200");
            sqlForStartTime.append("       AND A.quantity != 0");
            sqlForStartTime.append("       AND A.DEL = 0");
            sqlForStartTime.append("       AND A.VALID = 0");
            sqlForStartTime.append("       AND A.user_id != '3333333333333333'");
            sqlForStartTime.append("       AND LEFT (A.start_time, 10) IN (");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 1 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 2 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 3 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 4 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 5 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 6 DAY");
            sqlForStartTime.append("       	),");
            sqlForStartTime.append("       	DATE_ADD(");
            sqlForStartTime.append("       		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("       		INTERVAL - 7 DAY");
            sqlForStartTime.append("       	)");
            sqlForStartTime.append("       )");
            sqlForStartTime.append("       GROUP BY");
            sqlForStartTime.append("       	LEFT (A.start_time, 10)");
            sqlForStartTime.append("       ORDER BY");
            sqlForStartTime.append("       	LEFT (A.start_time, 10) DESC;");
        }else{
            sqlForStartTime.append("    SELECT");
            sqlForStartTime.append("    	LEFT (A.start_time, 10) stats_day,");
            sqlForStartTime.append("    	ROUND(SUM(A.QUANTITY / 100), 2) charge_quantity,");
            sqlForStartTime.append("    	SUM(");
            sqlForStartTime.append("    		CASE");
            sqlForStartTime.append("    		WHEN B.user_name IS NULL");
            sqlForStartTime.append("    		AND quantity IS NOT NULL THEN");
            sqlForStartTime.append("    			1");
            sqlForStartTime.append("    		ELSE");
            sqlForStartTime.append("    			0");
            sqlForStartTime.append("    		END");
            sqlForStartTime.append("    	) card_time,");
            sqlForStartTime.append("    	SUM(");
            sqlForStartTime.append("    		CASE");
            sqlForStartTime.append("    		WHEN B.user_name IS NULL THEN");
            sqlForStartTime.append("    			0");
            sqlForStartTime.append("    		ELSE");
            sqlForStartTime.append("    			1");
            sqlForStartTime.append("    		END");
            sqlForStartTime.append("    	) code_time");
            sqlForStartTime.append("    FROM");
            sqlForStartTime.append("    	t_charging A");
            sqlForStartTime.append("    LEFT JOIN t_charging_pile p on A.pile_id = p.id");
            sqlForStartTime.append("    LEFT JOIN t_charging_station s on p.station_id = s.id");
            sqlForStartTime.append("    LEFT JOIN t_user_person B ON A.user_id = B.userid");
            sqlForStartTime.append("    WHERE");
            sqlForStartTime.append("    	A.quantity / 100 < 200");
            sqlForStartTime.append("    AND A.quantity != 0");
            sqlForStartTime.append("    AND A.DEL = 0");
            sqlForStartTime.append("    AND s.operator_id = '001'");
            sqlForStartTime.append("    AND A.VALID = 0");
            sqlForStartTime.append("    AND A.user_id != '3333333333333333'");
            sqlForStartTime.append("    AND LEFT (A.start_time, 10) IN (");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 1 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 2 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 3 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 4 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 5 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 6 DAY");
            sqlForStartTime.append("    	),");
            sqlForStartTime.append("    	DATE_ADD(");
            sqlForStartTime.append("    		SUBSTR(NOW() FROM 1 FOR 10),");
            sqlForStartTime.append("    		INTERVAL - 7 DAY");
            sqlForStartTime.append("    	)");
            sqlForStartTime.append("    )");
            sqlForStartTime.append("    GROUP BY");
            sqlForStartTime.append("    	LEFT (A.start_time, 10)");
            sqlForStartTime.append("    ORDER BY");
            sqlForStartTime.append("    	LEFT (A.start_time, 10) DESC");
        }

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
    @RequestParams({ "*String:owner_id" })
    public void get_count()
    {
        /**
         * 充电站、桩显示对应运行商--完成修改-fan1115
         * */
        String owner_id = getPara("owner_id");
        String sqlCountStation = "";
        StringBuffer sqlCountPile = new StringBuffer();
        if(("null").equals(owner_id)||(owner_id).equals(null)){
            sqlCountStation    = "SELECT count(*) count FROM t_charging_station WHERE city_id = '110000' and operator_name = '华商三优'";
            sqlCountPile.append("   SELECT");
            sqlCountPile.append("   	count(*) count");
            sqlCountPile.append("   FROM");
            sqlCountPile.append("   	t_charging_pile A");
            sqlCountPile.append("   LEFT JOIN t_charging_station B ON A.station_id = B.id");
            sqlCountPile.append("   WHERE");
            sqlCountPile.append("   A.del = 0");
            sqlCountPile.append("   AND A.run_status NOT IN (0 ,- 1)");
            sqlCountPile.append("   AND B.id IN (");
            sqlCountPile.append("   	'Z-000337',");
            sqlCountPile.append("   	'1014696943217282473',");
            sqlCountPile.append("   	'Z-000289',");
            sqlCountPile.append("   	'1014609464013426237',");
            sqlCountPile.append("   	'1014582909654734175',");
            sqlCountPile.append("   	'1014780645902374216',");
            sqlCountPile.append("   	'1014812448114063690',");
            sqlCountPile.append("   	'1014544638314972650',");
            sqlCountPile.append("   	'1014674383383633496',");
            sqlCountPile.append("   	'1014609431783924297',");
            sqlCountPile.append("   	'1014609436352157280',");
            sqlCountPile.append("   	'Z-000331',");
            sqlCountPile.append("   	'1014628483992201542',");
            sqlCountPile.append("   	'1014722110543366598',");
            sqlCountPile.append("   	'1014817007082332851',");
            sqlCountPile.append("   	'1014791031104019850',");
            sqlCountPile.append("   	'1014839399215720581',");
            sqlCountPile.append("   	'1014830021655280136',");
            sqlCountPile.append("   	'1014816999718162630',");
            sqlCountPile.append("   	'1014670845618151937',");
            sqlCountPile.append("   	'1014829942212615613',");
            sqlCountPile.append("   	'1014715012122169150',");
            sqlCountPile.append("   	'1014522283054749078',");
            sqlCountPile.append("   	'1014640560287229153',");
            sqlCountPile.append("   	'1014816974435515978',");
            sqlCountPile.append("   	'1014730543156142703',");
            sqlCountPile.append("   	'1014817005934923204',");
            sqlCountPile.append("   	'1014683933545541360',");
            sqlCountPile.append("   	'1014818882031941384',");
            sqlCountPile.append("   	'1014816975805482158',");
            sqlCountPile.append("   	'1014750354794653029',");
            sqlCountPile.append("   	'1014616365142027109',");
            sqlCountPile.append("   	'1014752066194649321',");
            sqlCountPile.append("   	'1014558544933932365',");
            sqlCountPile.append("   	'1014778133328150324',");
            sqlCountPile.append("   	'1014746163138559560',");
            sqlCountPile.append("   	'1014738389703066130',");
            sqlCountPile.append("   	'Z-000081',");
            sqlCountPile.append("   	'1014673588906892481',");
            sqlCountPile.append("   	'1014800795090896408',");
            sqlCountPile.append("   	'1014627768363174690',");
            sqlCountPile.append("   	'1014607105341453524',");
            sqlCountPile.append("   	'1014812516337077840',");
            sqlCountPile.append("   	'1014647446570233596',");
            sqlCountPile.append("   	'1014816976398338260',");
            sqlCountPile.append("   	'1014543146999497053',");
            sqlCountPile.append("   	'1014758939799736352',");
            sqlCountPile.append("   	'1014673596526483518',");
            sqlCountPile.append("   	'1014675059706467548',");
            sqlCountPile.append("   	'1014673588392823481',");
            sqlCountPile.append("   	'1014734107495034162',");
            sqlCountPile.append("   	'1014816980741925148',");
            sqlCountPile.append("   	'1014834051944684061',");
            sqlCountPile.append("   	'1014585250436986045',");
            sqlCountPile.append("   	'1014579236432938736',");
            sqlCountPile.append("   	'1014557676904133174',");
            sqlCountPile.append("   	'1014822862655085147',");
            sqlCountPile.append("   	'1014510228200401306',");
            sqlCountPile.append("   	'1014633902263181569',");
            sqlCountPile.append("   	'1014836739974852410',");
            sqlCountPile.append("   	'1014570991163685901',");
            sqlCountPile.append("   	'1014804026936744803',");
            sqlCountPile.append("   	'1014665798432883760',");
            sqlCountPile.append("   	'1014733204352388921',");
            sqlCountPile.append("   	'1014523250522881568',");
            sqlCountPile.append("   	'1014652613374412351',");
            sqlCountPile.append("   	'1014648357148278051',");
            sqlCountPile.append("   	'1014576929793786051',");
            sqlCountPile.append("   	'1014840200031374078',");
            sqlCountPile.append("   	'1014507799978950178',");
            sqlCountPile.append("   	'1014784899515223057',");
            sqlCountPile.append("   	'1014781624510658971',");
            sqlCountPile.append("   	'1014784906081499387'");
            sqlCountPile.append("   )");

        }else{
            StringBuffer sql = new StringBuffer();
            sql.append("    SELECT");
            sql.append("    	count(*) count");
            sql.append("    FROM");
            sql.append("    	t_charging_pile A");
            sql.append("    LEFT JOIN t_charging_station B ON A.station_id = B.id");
            sql.append("    WHERE");
            sql.append("    	B.operator_id = '"+owner_id+"'");
            sql.append("    AND A.del = 0");
            sql.append("    AND A.run_status NOT IN (0 ,- 1);");
            sqlCountStation    = "SELECT count(*) count FROM t_charging_station WHERE operator_id = '"+owner_id+"'";
            //sqlCountPile       = sql.toString();
        }
        Record              recordCountStation = Db.findFirst(sqlCountStation);
        Record              recordCountPile    = Db.findFirst(sqlCountPile.toString());
        Map<String, Object> result             = new HashMap<>();
        if (recordCountStation != null)
        {
            result.put("station_count", 73);
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
    @RequestParams({ "*String:owner_id" })
    public void get_pile_status()
    {
        /**
         * 运营商充电桩状态-fan-1115
         */
        String owner_id = getPara("owner_id");
        StringBuffer sqlPile = new StringBuffer();
        if(("null").equals(owner_id)||(owner_id).equals(null)){
            sqlPile.append("     SELECT");
            sqlPile.append("     	run_status,");
            sqlPile.append("     	count(*) count");
            sqlPile.append("     FROM");
            sqlPile.append("     	t_charging_pile A");
            sqlPile.append("     LEFT JOIN t_charging_station B ON A.station_id = B.id");
            sqlPile.append("     WHERE");
            sqlPile.append("     	A.del = '0'");
            sqlPile.append("     AND A.run_status NOT IN ('-1', '0')");
            sqlPile.append("     AND B.ID IN (");
            sqlPile.append("     	'Z-000337',");
            sqlPile.append("     	'1014696943217282473',");
            sqlPile.append("     	'Z-000289',");
            sqlPile.append("     	'1014609464013426237',");
            sqlPile.append("     	'1014582909654734175',");
            sqlPile.append("     	'1014780645902374216',");
            sqlPile.append("     	'1014812448114063690',");
            sqlPile.append("     	'1014544638314972650',");
            sqlPile.append("     	'1014674383383633496',");
            sqlPile.append("     	'1014609431783924297',");
            sqlPile.append("     	'1014609436352157280',");
            sqlPile.append("     	'Z-000331',");
            sqlPile.append("     	'1014628483992201542',");
            sqlPile.append("     	'1014722110543366598',");
            sqlPile.append("     	'1014817007082332851',");
            sqlPile.append("     	'1014791031104019850',");
            sqlPile.append("     	'1014839399215720581',");
            sqlPile.append("     	'1014830021655280136',");
            sqlPile.append("     	'1014816999718162630',");
            sqlPile.append("     	'1014670845618151937',");
            sqlPile.append("     	'1014829942212615613',");
            sqlPile.append("     	'1014715012122169150',");
            sqlPile.append("     	'1014522283054749078',");
            sqlPile.append("     	'1014640560287229153',");
            sqlPile.append("     	'1014816974435515978',");
            sqlPile.append("     	'1014730543156142703',");
            sqlPile.append("     	'1014817005934923204',");
            sqlPile.append("     	'1014683933545541360',");
            sqlPile.append("     	'1014818882031941384',");
            sqlPile.append("     	'1014816975805482158',");
            sqlPile.append("     	'1014750354794653029',");
            sqlPile.append("     	'1014616365142027109',");
            sqlPile.append("     	'1014752066194649321',");
            sqlPile.append("     	'1014558544933932365',");
            sqlPile.append("     	'1014778133328150324',");
            sqlPile.append("     	'1014746163138559560',");
            sqlPile.append("     	'1014738389703066130',");
            sqlPile.append("     	'Z-000081',");
            sqlPile.append("     	'1014673588906892481',");
            sqlPile.append("     	'1014800795090896408',");
            sqlPile.append("     	'1014627768363174690',");
            sqlPile.append("     	'1014607105341453524',");
            sqlPile.append("     	'1014812516337077840',");
            sqlPile.append("     	'1014647446570233596',");
            sqlPile.append("     	'1014816976398338260',");
            sqlPile.append("     	'1014543146999497053',");
            sqlPile.append("     	'1014758939799736352',");
            sqlPile.append("     	'1014673596526483518',");
            sqlPile.append("     	'1014675059706467548',");
            sqlPile.append("     	'1014673588392823481',");
            sqlPile.append("     	'1014734107495034162',");
            sqlPile.append("     	'1014816980741925148',");
            sqlPile.append("     	'1014834051944684061',");
            sqlPile.append("     	'1014585250436986045',");
            sqlPile.append("     	'1014579236432938736',");
            sqlPile.append("     	'1014557676904133174',");
            sqlPile.append("     	'1014822862655085147',");
            sqlPile.append("     	'1014510228200401306',");
            sqlPile.append("     	'1014633902263181569',");
            sqlPile.append("     	'1014836739974852410',");
            sqlPile.append("     	'1014570991163685901',");
            sqlPile.append("     	'1014804026936744803',");
            sqlPile.append("     	'1014665798432883760',");
            sqlPile.append("     	'1014733204352388921',");
            sqlPile.append("     	'1014523250522881568',");
            sqlPile.append("     	'1014652613374412351',");
            sqlPile.append("     	'1014648357148278051',");
            sqlPile.append("     	'1014576929793786051',");
            sqlPile.append("     	'1014840200031374078',");
            sqlPile.append("     	'1014507799978950178',");
            sqlPile.append("     	'1014784899515223057',");
            sqlPile.append("     	'1014781624510658971',");
            sqlPile.append("     	'1014784906081499387'");
            sqlPile.append("     )");
            sqlPile.append("     GROUP BY");
            sqlPile.append("     	A.run_status");
        }else{

            StringBuffer sql = new StringBuffer();
            sql.append("   SELECT");
            sql.append("   	A.run_status,");
            sql.append("   	count(1) count");
            sql.append("   FROM");
            sql.append("   	t_charging_pile A");
            sql.append("   LEFT JOIN t_charging_station B");
            sql.append("   ON A.station_id = B.id");
            sql.append("   WHERE");
            sql.append("   A.del = '0'");
            sql.append("   AND A.run_status NOT IN ('-1', '0')");
            sql.append("   AND B.operator_id = '"+owner_id+"'");
            sql.append("   GROUP BY");
            sql.append("   	A.run_status");
            //sqlPile = sql.toString();
        }

        List<Record> pieStatusList = Db.find(sqlPile.toString());
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
    @RequestParams({ "*String:owner_id" })
    public void get_fault()
    {
        String owner_id = getPara("owner_id");
        Date             now         = new Date();
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyy-MM-dd");
        String           nowStr      = sdf.format(now);
        StringBuffer sqlFault = new StringBuffer();
        if(("null").equals(owner_id)||owner_id.equals(null)){
            sqlFault.append("    SELECT");
            sqlFault.append("    	s.pile_name,");
            sqlFault.append("    	t.fault_code,");
            sqlFault.append("    	t.err_code,");
            sqlFault.append("    	t.err_status,");
            sqlFault.append("    	t.record_time");
            sqlFault.append("    FROM");
            sqlFault.append("    	t_fault t");
            sqlFault.append("    LEFT JOIN t_charging_pile s ON t.pile_id = s.id");
            sqlFault.append("    LEFT JOIN t_charging_station c ON s.station_id = c.id");
            sqlFault.append("    WHERE");
            sqlFault.append("    	t.solve_status = '1'");
            sqlFault.append("    AND s.del = '0'");
            sqlFault.append("    AND c.id IN (");
            sqlFault.append("    	'Z-000337',");
            sqlFault.append("    	'1014696943217282473',");
            sqlFault.append("    	'Z-000289',");
            sqlFault.append("    	'1014609464013426237',");
            sqlFault.append("    	'1014582909654734175',");
            sqlFault.append("    	'1014780645902374216',");
            sqlFault.append("    	'1014812448114063690',");
            sqlFault.append("    	'1014544638314972650',");
            sqlFault.append("    	'1014674383383633496',");
            sqlFault.append("    	'1014609431783924297',");
            sqlFault.append("    	'1014609436352157280',");
            sqlFault.append("    	'Z-000331',");
            sqlFault.append("    	'1014628483992201542',");
            sqlFault.append("    	'1014722110543366598',");
            sqlFault.append("    	'1014817007082332851',");
            sqlFault.append("    	'1014791031104019850',");
            sqlFault.append("    	'1014839399215720581',");
            sqlFault.append("    	'1014830021655280136',");
            sqlFault.append("    	'1014816999718162630',");
            sqlFault.append("    	'1014670845618151937',");
            sqlFault.append("    	'1014829942212615613',");
            sqlFault.append("    	'1014715012122169150',");
            sqlFault.append("    	'1014522283054749078',");
            sqlFault.append("    	'1014640560287229153',");
            sqlFault.append("    	'1014816974435515978',");
            sqlFault.append("    	'1014730543156142703',");
            sqlFault.append("    	'1014817005934923204',");
            sqlFault.append("    	'1014683933545541360',");
            sqlFault.append("    	'1014818882031941384',");
            sqlFault.append("    	'1014816975805482158',");
            sqlFault.append("    	'1014750354794653029',");
            sqlFault.append("    	'1014616365142027109',");
            sqlFault.append("    	'1014752066194649321',");
            sqlFault.append("    	'1014558544933932365',");
            sqlFault.append("    	'1014778133328150324',");
            sqlFault.append("    	'1014746163138559560',");
            sqlFault.append("    	'1014738389703066130',");
            sqlFault.append("    	'Z-000081',");
            sqlFault.append("    	'1014673588906892481',");
            sqlFault.append("    	'1014800795090896408',");
            sqlFault.append("    	'1014627768363174690',");
            sqlFault.append("    	'1014607105341453524',");
            sqlFault.append("    	'1014812516337077840',");
            sqlFault.append("    	'1014647446570233596',");
            sqlFault.append("    	'1014816976398338260',");
            sqlFault.append("    	'1014543146999497053',");
            sqlFault.append("    	'1014758939799736352',");
            sqlFault.append("    	'1014673596526483518',");
            sqlFault.append("    	'1014675059706467548',");
            sqlFault.append("    	'1014673588392823481',");
            sqlFault.append("    	'1014734107495034162',");
            sqlFault.append("    	'1014816980741925148',");
            sqlFault.append("    	'1014834051944684061',");
            sqlFault.append("    	'1014585250436986045',");
            sqlFault.append("    	'1014579236432938736',");
            sqlFault.append("    	'1014557676904133174',");
            sqlFault.append("    	'1014822862655085147',");
            sqlFault.append("    	'1014510228200401306',");
            sqlFault.append("    	'1014633902263181569',");
            sqlFault.append("    	'1014836739974852410',");
            sqlFault.append("    	'1014570991163685901',");
            sqlFault.append("    	'1014804026936744803',");
            sqlFault.append("    	'1014665798432883760',");
            sqlFault.append("    	'1014733204352388921',");
            sqlFault.append("    	'1014523250522881568',");
            sqlFault.append("    	'1014652613374412351',");
            sqlFault.append("    	'1014648357148278051',");
            sqlFault.append("    	'1014576929793786051',");
            sqlFault.append("    	'1014840200031374078',");
            sqlFault.append("    	'1014507799978950178',");
            sqlFault.append("    	'1014784899515223057',");
            sqlFault.append("    	'1014781624510658971',");
            sqlFault.append("    	'1014784906081499387'");
            sqlFault.append("    )");
            sqlFault.append("    LIMIT 6");

        }else {
            StringBuffer sql = new StringBuffer();
            sql.append("  SELECT");
            sql.append("  	s.pile_name,");
            sql.append("  	t.fault_code,");
            sql.append("  	t.err_code,");
            sql.append("  	t.err_status,");
            sql.append("  	t.record_time");
            sql.append("  FROM");
            sql.append("  	t_fault t,");
            sql.append("  	t_charging_pile s");
            sql.append("  LEFT JOIN t_charging_station B");
            sql.append("  ON s.station_id = B.id");
            sql.append("  WHERE");
            sql.append("  	t.pile_id = s.id");
            sql.append("  AND solve_status = '1'");
            sql.append("  AND s.del = '0'");
            sql.append("  AND solve_status = '1'");
            sql.append("  AND t.record_time LIKE '"+nowStr+"%'");
            sql.append("  AND B.operator_id = '001'");
            sql.append("  ORDER BY");
            sql.append("  	RECORD_TIME DESC");
            //sqlFault = sql.toString();
        }

        List<Record> faultList = Db.find(sqlFault.toString());
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
    /**
     *解码
     */

    public String unicode(){
        return null;
    }
    /**
     * 获取运营商下的充电站id及充电站名称
     */
    @RequestParams({ "*String:owner" })
    public void returnInfo(){
        String owner_id = getPara("owner");
        StringBuffer sql = new StringBuffer();
        System.out.print(owner_id+"fan");
        sql.append("    SELECT");
        sql.append("    	A.station_name,");
        sql.append("    	A.ID,");
        sql.append("    	COUNT(*) COUNT");
        sql.append("    FROM");
        sql.append("    	t_charging_station A");
        sql.append("    LEFT JOIN t_charging_pile B ON A.id = B.station_id");
        sql.append("    LEFT JOIN t_order C ON B.id = C.pile_id");
        sql.append("    WHERE");
        sql.append("    	A.operator_id = '"+owner_id+"'");
        sql.append("    AND A.del = 0");
        sql.append("    AND B.del = 0");
        sql.append("    AND C.del = 0");
        sql.append("    GROUP BY");
        sql.append("    	A.id");
        sql.append("    ORDER BY");
        sql.append("    	COUNT DESC");
        sql.append("    LIMIT 3");
        List<Record> faultList = Db.find(sql.toString());
        List<Map<String,Object>> resultlist = toListMap(faultList);
        super.respJsonObject(resultlist);
    }
}
