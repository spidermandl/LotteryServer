package com.evstudio.lottery.controller;

import com.jfinal.core.Controller;

/**
 * Created by ericren on 14-8-24.
 */
public class BranchController extends Controller {
    public void index(){
        renderText("");
    }

    public void priorityList(){
        render("/WEB-INF/templates/default/branch/oriList.ftl");
    }

    public void priorityApply(){
        render("/WEB-INF/templates/default/branch/priorityApply.ftl");
    }
}
