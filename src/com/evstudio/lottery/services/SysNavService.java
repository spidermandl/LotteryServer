package com.evstudio.lottery.services;

/**
 * Created by ericren on 14-8-6.
 */
public class SysNavService {
    public static SysNavService service = new SysNavService();

    /**
     * <li>
     * <i class="icon-home home-icon"></i>
     * <a href="#">Home</a>
     * </li>
     * <li>
     * <a href="#">Other Pages</a>
     * </li>
     * <li class="active">Blank Page</li>
     * 生成导航栏
     */
    public String generateNavHtml(String[] items) {
        StringBuffer sbReturn = new StringBuffer();
        if (null != items && items.length > 0) {
            for (int i = 0; i < items.length; i++) {
                if (i == items.length - 1) {
                    sbReturn.append("<li class=\"active\">");
                } else
                    sbReturn.append("<li><a href=\"#\">");
                sbReturn.append(items[i]);
                if (i < items.length - 1)
                    sbReturn.append("</a>");
                sbReturn.append("</li>");
            }
        }

        return sbReturn.toString();
    }
}
