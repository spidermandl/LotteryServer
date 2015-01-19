package com.evstudio.lottery.controller;

import com.jfinal.core.Controller;

/**
 * Created by ericren on 14-6-26.
 */
public class DefaultController extends Controller {
    public void index(){
        System.out.println( "CTX_BASE=" + getAttr("ctx_base"));
        render("/WEB-INF/templates/default/login.ftl");
    }
}
