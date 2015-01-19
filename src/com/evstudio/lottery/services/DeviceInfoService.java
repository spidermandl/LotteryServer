package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.TUserStartAppInfo;

import java.util.Date;

/**
 * Created by ericren on 14-9-16.
 */
public class DeviceInfoService {
    public static final DeviceInfoService service = new DeviceInfoService();

    private String versionCode;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public void saveRequest(String uri, String method, String content) {
        TUserStartAppInfo appInfo = new TUserStartAppInfo();
        appInfo.set("reqtime", new Date());
        appInfo.set("appversioncode", null == versionCode ? "" : versionCode);
        appInfo.set("modifytime", new Date());
        appInfo.set("requrl", uri);
        appInfo.set("reqmethod", method);
        appInfo.set("reqcontent", content);
        appInfo.set("valid", "1");
        appInfo.save();
    }
}
