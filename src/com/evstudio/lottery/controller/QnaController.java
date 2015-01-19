package com.evstudio.lottery.controller;

import com.jfinal.core.Controller;

/**
 * Created by ericren on 14-9-7.
 */
public class QnaController extends Controller {
    public void index(){
        render("/WEB-INF/templates/default/qna/begin.ftl");
    }
}
