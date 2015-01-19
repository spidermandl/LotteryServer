package com.evstudio.lottery.controller;

import com.jfinal.core.Controller;

/**
 * Created by ericren on 14-8-24.
 */
public class CmFuncsController extends Controller {
    public void index() {
        renderText("");
    }

    public void inbox() {
        render("/WEB-INF/templates/default/commonFunctions/inbox.ftl");
    }

    public void download() {
        String fileName = getPara("filename");
        renderFile("/WEB-INF/attachments/" + fileName);
    }

    public void exhibit() {
        render("/WEB-INF/templates/default/download/downloadList.ftl");
    }
}
