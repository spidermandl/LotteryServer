package com.evstudio.lottery.thread;

import com.evstudio.lottery.pojos.Sh11x5;
import com.evstudio.lottery.services.Sh11x5Service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ericren on 14-9-9.
 */
public class Sh11x5GooooalTask extends TimerTask {
    public static Sh11x5GooooalTask task = new Sh11x5GooooalTask();

    private String strNextPeriods = "";
    private String strNextTime = "";

    public String getStrNextPeriods() {
        return strNextPeriods;
    }

    public void setStrNextPeriods(String strNextPeriods) {
        this.strNextPeriods = strNextPeriods;
    }

    private String strResult = "";
    private Timer timer;
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public String getStrNextTime() {
        return strNextTime;
    }

    public void setStrNextTime(String strNextTime) {
        this.strNextTime = strNextTime;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        timer = new Timer();
        Date date = new Date();
        java.sql.Timestamp timestamp;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://caipiao.gooooal.com/issue/sh115.lt");

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            String[] strResponse = responseBody.split("\\^");
            System.out.println(strResponse[3]);
            System.out.println(strResponse[7]);

            strNextPeriods = strResponse[4];
            strNextTime = strResponse[7];
            strResult = strResponse[3];

            timestamp = new Timestamp((new Long(strNextTime)).longValue() * 1000 + 20 * 1000);

            if (null != strResult && !"".equals(strResult)) {
                Sh11x5 sh11x5 = Sh11x5Service.service.getLast();
                Sh11x5 newSh11x5 = new Sh11x5();
                String[] strs = strResult.split(":");
                String[] strs2 = strs[1].split(",");
                if (!strs[0].equals(sh11x5.getStr("periods"))) {
                    newSh11x5.set("periods", strs[0]);
                    newSh11x5.set("winningnumber1", strs2[0]);
                    newSh11x5.set("winningnumber2", strs2[1]);
                    newSh11x5.set("winningnumber3", strs2[2]);
                    newSh11x5.set("winningnumber4", strs2[3]);
                    newSh11x5.set("winningnumber5", strs2[4]);
                    try {
                        newSh11x5.set("publishdate", format.parse(strs[0].substring(0, 8)));
                        newSh11x5.set("dayperiods", Integer.parseInt(strs[0].substring(8)));
                        newSh11x5.set("drawyear", strs[0].substring(0, 4));
                        newSh11x5.set("drawdate", strs[0].substring(4, 8));
                        newSh11x5.set("importtime", new Date());
                        newSh11x5.set("modifytime", new Date());
                        newSh11x5.set("valid", "1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newSh11x5.save();
                    Sh11x5Draw draw = new Sh11x5Draw("11", strs[0], strs[1]);
                    draw.run();
                } else {
                    if (((new Long(strNextTime)).longValue() * 1000 - System.currentTimeMillis()) < 1000 * 60)
                        timestamp = new Timestamp(System.currentTimeMillis() + 1000 * 20);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            timestamp = new Timestamp(System.currentTimeMillis() + 1000 * 30);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        date = timestamp;
        Sh11x5GooooalTask.task = new Sh11x5GooooalTask();
        Sh11x5GooooalTask.task.setStrNextPeriods(this.strNextPeriods);
        Sh11x5GooooalTask.task.setStrNextTime(this.strNextTime);
        timer.schedule(Sh11x5GooooalTask.task, date);
    }
}
