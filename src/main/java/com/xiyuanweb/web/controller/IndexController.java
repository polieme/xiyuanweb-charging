package com.xiyuanweb.web.controller;

import com.xiyuanweb.jfinal.controller.XyController;

/**
 * Created by hongcy on 2016/9/2.
 */
public class IndexController extends XyController
{
    public void index()
    {


        super.redirect("/pages/monitoring/operation.jsp");
    }
}
