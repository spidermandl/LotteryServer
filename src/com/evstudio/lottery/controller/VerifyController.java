package com.evstudio.lottery.controller;

import com.evstudio.lottery.pojos.TUserDownloads;
import com.jfinal.core.Controller;

/**
 * Created by ericren on 14-8-30.
 */
public class VerifyController extends Controller {
    public void index() {
        render("");
    }

    public void qrverify() {
        String qrcode = "";
        String filePath = "/resource/app_version/1.0/";
        String fileName = "/thefirstlottery.apk";
        String pathName = filePath + fileName;
        qrcode = getPara("id");
        System.out.println( qrcode );

        if (null != qrcode && !qrcode.equals("")) {
            if (qrcode.equals("CH001000128276")
                    || qrcode.equals("CH001000228255")
                    || qrcode.equals("CH001000328290")) {
                pathName = filePath + qrcode + fileName;

            }
        }

        System.out.println( pathName );

//        renderFile(pathName);
        getResponse().setContentType("application/octet-stream");
        redirect(pathName);
    }
}