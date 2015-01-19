package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.ImageMarkLogoUtil;
import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.TQijiNightrideUserFilepath;
import com.evstudio.lottery.pojos.TQijiNightrideUsers;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import org.apache.http.HttpRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by eric on 14/12/24.
 */
public class QijiController extends Controller {

    public static String filePath = "/home/www/tc0.thefirstlottery.com/nightride/userfiles/";

    public static String[] watermarkfiles = {
            "/home/www/tc0.thefirstlottery.com/nightride/images/logo.png",
            "/home/www/tc0.thefirstlottery.com/nightride/images/watermark02.png",
            "/home/www/tc0.thefirstlottery.com/nightride/images/watermark01.png",
            "/home/www/tc0.thefirstlottery.com/nightride/images/watermark03.png"
    };

    public void index() {
        HttpServletRequest request = getRequest();

        Enumeration e = request.getHeaderNames();

        Gson gson = new Gson();
        String string = "{";
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String value = request.getHeader(name);
            string += "'" + name + "':";
            string += "'" + value + "',";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        System.out.println(string);

        TQijiNightrideUsers user = new TQijiNightrideUsers();
        user.set("uid", Util.getNewIdByType(999));
        user.set("useragent", string);
        user.set("createtime", new Date());
        user.set("userip", getRemoteHost(request));
        user.set("valid", "1");
        user.save();

        request.getSession().setAttribute("userid", user.getStr("uid"));

        render("/nightride/index.ftl");
    }

    public void step1() {
        HttpSession session = getSession();
        String uid = (String) session.getAttribute("uid");
        render("/nightride/step1.ftl");
    }


    public void step2() {
        HttpSession session = getSession();
        String uid = (String) session.getAttribute("uid");
        render("/nightride/step2.ftl");
    }

    public void step3() {
        HttpSession session = getSession();
        String uid = (String) session.getAttribute("uid");
        String filedata = getPara("filedata");

        TQijiNightrideUserFilepath filepath = new TQijiNightrideUserFilepath();
        String fileid = Util.getNewIdByType(998);
        String savePath = filePath + fileid + ".jpg";

        filepath.set("fileid", fileid);
        filepath.set("userid", uid);
        filepath.set("orifiledata", filedata);
        filepath.set("orifile", savePath);
        filepath.set("createtime", new Date());
        filepath.set("valid", "1");
        filepath.save();

        filedata = filedata.substring("data:image/jpeg;base64,".length());
        System.out.println(filedata);
        ImageMarkLogoUtil.GenerateImage(filedata, savePath);

        setAttr("userfile", filepath);
        render("/nightride/step3.ftl");
    }

    public void step4() {
        HttpSession session = getSession();
        String uid = (String) session.getAttribute("uid");
        String fid = getPara("fid");
        String sid = getPara("sid");

        if (null == fid || null == sid
                || "".equals(fid) || "".equals(sid)) {
            index();
            return;
        }

        System.out.println(fid);
        System.out.println(sid);

        TQijiNightrideUserFilepath filepath = TQijiNightrideUserFilepath.dao.findFirst(
                "select * from t_qiji_nightride_user_filepath where fileid = ?", fid);
        String filename = filepath.getStr("orifile");

        String wkImageFile = filePath + fid + "_wk.jpg";
        System.out.println(filename);
        System.out.println(sid);
        System.out.println(watermarkfiles[Integer.parseInt(sid)]);
        System.out.println(wkImageFile);

        ImageMarkLogoUtil.positionHeight = 10;
        ImageMarkLogoUtil.positionWidth = 10;
        ImageMarkLogoUtil.markImageByIcon(watermarkfiles[Integer.parseInt(sid)], filename, wkImageFile);

        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            BufferedImage bufferedImg = ImageIO.read(fis);
            int imgWidth = bufferedImg.getWidth();
            int imgHeight = bufferedImg.getHeight();
            ImageMarkLogoUtil.positionWidth = imgWidth - 210;
            ImageMarkLogoUtil.positionHeight = imgHeight - 70;
            ImageMarkLogoUtil.markImageByIcon(watermarkfiles[0], wkImageFile, wkImageFile);
        }catch (Exception e){
            e.printStackTrace();
        }

        filepath.set("filepath", wkImageFile);
        filepath.update();

        setAttr("wkfile", fid + "_wk.jpg");
        render("/nightride/step4.ftl");
    }


    public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
