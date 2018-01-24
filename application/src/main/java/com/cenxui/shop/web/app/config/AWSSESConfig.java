package com.cenxui.shop.web.app.config;

public class AWSSESConfig {
    //todo

    public static final boolean ENABLE = true;

    public static final String FROM = "support@hwangying.com";

    public static final String REGION = "us-east-1";

    public static final String HTMLBODY =
            "    <p>親愛的顧客，您好！<br>" +
                    "感謝您對皇飲的支持，我們已收到您的訂單。我們將於收到款項時再次發送電子郵件通知，並儘速寄出貨物。<br>" +
                    "<br>" +
                    "信箱：%s<br>" +
                    "訂購時間：%s<br>" +
                    "訂單金額：NT$ %d<br>" +
                    "可以使用ATM轉帳，或是匯款至<br>" +
                    "銀行：台灣銀行（代碼004）<br>" +
                    "戶名：戰車聯有限公司<br>" +
                    "帳號：087001-022022<br>" +
                    "<br>" +
                    "您可至［會員中心-><a href=' https://tw.hwangying.com/user-orders.html'>訂單記錄</a>］" +
                    "查詢訂單詳細資訊，以及最新狀態。<br>" +
                    "如有訂單相關問題，歡迎寄信至support@hwangying.com，或洽0972858256由客服提供您協助。<br>" +
                    "此郵件由系統發送，僅供您再次核對，並非代表交易已完成。<br>" +
                    "<br>" +
                    "<br>" +
                    "<a href='https://tw.hwangying.com/'>皇飲 Hwang Ying</a><br>" +
                    "提供您最優質的台灣高山好茶<br>https://tw.hwangying.com</p>";

    public static final String SUBJECT = "(no reply) 皇飲 訂單通知";


}
