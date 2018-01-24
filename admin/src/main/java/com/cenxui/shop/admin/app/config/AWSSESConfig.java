package com.cenxui.shop.admin.app.config;

public class AWSSESConfig {
    public static final boolean ENABLE = true;

    public static final String FROM = "support@hwangying.com";

    public static final String REGION = "us-east-1";

    public static final String PAY_SUBJECT = "(no reply) 皇飲 收款通知\n";

    public static final String SHIP_SUBJECT = "(no reply) 皇飲 發貨通知";

    
    public static final String HTMLPAY = 
            "<p>親愛的顧客，您好！<br>" +
            "感謝您對皇飲的支持，我們已收到您的款項。我們將於商品出貨時再次發送電子郵件通知。謝謝！<br>" +
            "<br>" +
            "會員信箱：%s<br>" +
            "訂購時間：%s<br>" +
            "訂單金額：NT$ %d<br>" +
            "<br>" +
            "您可至［會員中心-><a href=' https://hwangying.com/user-orders.html'>訂單記錄</a>］" + 
                    "查詢訂單詳細資訊，以及最新狀態。<br>" +
            "如有訂單相關問題，歡迎寄信至support@hwangying.com，或洽0972858256由客服提供您協助。<br>" +
            "<br>" +
            "<br>" +
            "<a href='https://hwangying.com/'>皇飲 Hwang Ying</a><br>" +
            "提供您最優質的台灣高山好茶 <br>https://hwangying.com</p>";
    
    public static final String HTMLSHIP = 
                    "親愛的顧客，您好！<br>" +
                    "感謝您的訂購，我們已將（訂單時間：%s）商品出貨，並請您留意物流狀況。再次感謝您對皇飲的支持，謝謝！<br>" +
                    "<br>" +
                    "您可至［會員中心-><a href=' https://hwangying.com/user-orders.html'>訂單記錄</a>］" +
                            "查詢訂單詳細資訊，以及最新狀態。<br>" +
                    "如有訂單相關問題，歡迎寄信至support@hwangying.com，或洽0972858256由客服提供您協助。<br>" +
                    "<br>" +
                    "<br>" +
                    "<a href='https://hwangying.com/'>皇飲 Hwang Ying</a><br>" +
                    "提供您最優質的台灣高山好茶<br>" +
                    "https://tw.hwangying.com<br>";
   

}
