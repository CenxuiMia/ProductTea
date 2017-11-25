/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(productsAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );

    queryProductsData();
});

function queryProductsData() {

    $.get(productEndpoint, function(jsonData, status){
        console.info("Data: " + jsonData + "\nStatus: " + status);

        let data = JSON.parse(jsonData);
        let count = data.products.length;
        let productList = document.getElementsByClassName("prod-list")[0];
        for (let i=0; i<count; i++) {
            let queryString = "https://tw.hwangying.com/product.html?name=" +
                data.products[i].productName + "&version=" + data.products[i].version;
            productList.innerHTML +=
                "<div class='prod-wrapper'>" +
                    "<a href=" + queryString + ">" +
                        "<img src='https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg'" +//TODO 改圖 data.products[i].smallImage;
                        " alt='茶品項' style='width: 100%'>" +
                        "<div class='info'>" +
                            "<p><b>" + data.products[i].productName + "</b><br>" + data.products[i].introduction + "</p>" +
                            "<div class='price'>$" + data.products[i].price + "</div>" +
                        "</div>" +
                    "</a>" +
                "</div>";
        }
    });
}
