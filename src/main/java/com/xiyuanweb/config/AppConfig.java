package com.xiyuanweb.config;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.xiyuanweb.jfinal.validator.interceptor.ParamValidatorInterceptor;
import com.xiyuanweb.web.controller.IndexController;
import com.xiyuanweb.web.controller.monitoring.DeviceController;
import com.xiyuanweb.web.controller.monitoring.OperationController;
import com.xiyuanweb.web.controller.monitoring.StationController;

/**
 * 项目配置入口
 */
public class AppConfig extends JFinalConfig
{
    @Override
    public void configConstant(Constants constants)
    {
        super.loadPropertyFile("app.config");

        constants.setJsonDatePattern("yyyy-MM-dd HH:mm:ss");
        constants.setViewType(ViewType.JSP);
        constants.setJsonFactory(new FastJsonFactory());
        constants.setDevMode(super.getPropertyToBoolean("dev_mode", true));
    }

    @Override
    public void configRoute(Routes routes)
    {

        routes.add("/", IndexController.class);
        routes.add("/operation", OperationController.class);//第一个页面
        routes.add("/device", DeviceController.class);//第三个个页面
        routes.add("/station", StationController.class);//第一个页面充电站统计，以及第二个页面订单
    }

    @Override
    public void configPlugin(Plugins plugins)
    {
        String url    = super.getProperty("jdbc.url");
        String user   = super.getProperty("jdbc.user");
        String pwd    = super.getProperty("jdbc.password");
        String driver = super.getProperty("jdbc.driver");

        DruidPlugin dp = new DruidPlugin(url, user, pwd, driver);
        plugins.add(dp);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.setShowSql(super.getPropertyToBoolean("show_sql", true));

        plugins.add(arp);

        plugins.add(new EhCachePlugin());
    }

    @Override
    public void configInterceptor(Interceptors interceptors)
    {
        interceptors.add(new ParamValidatorInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers)
    {
        handlers.add(new ContextPathHandler("website_root"));
    }


    @Override
    public void afterJFinalStart()
    {
    }
}
