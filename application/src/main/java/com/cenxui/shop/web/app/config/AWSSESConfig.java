package com.cenxui.shop.web.app.config;

public class AWSSESConfig {
    //todo

    public static final boolean ENABLE = true;

    public static final String FROM = "support@hwangying.com";

    public static final String REGION = "us-east-1";

    public static final String HTMLBODY =
            " <h3>\n" +
            "      <a href='https://hwangying.com/'>皇飲</a>\n" +
            " </h3>\n" +
            "    <p>親愛的顧客感謝你在皇飲下單NT$%d 戶名：戰車聯有限公司 帳號：臺灣銀行（004）087001-022022</p>";

    public static final String SUBJECT = "(no reply) 皇飲訂單通知";

    public static final String MESSAGE =
            "親愛的顧客感謝你在皇飲下單NT$%d 戶名：戰車聯有限公司 帳號：臺灣銀行（004）087001-022022";

}
