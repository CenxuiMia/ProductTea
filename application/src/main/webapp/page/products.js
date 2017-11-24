/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
function onLoad() {
    // Initiatlize CognitoAuth object
    setUp(productsAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );

    queryProductsData();
}

function queryProductsData() {

    $.get(productEndpoint, function(jsonData, status){
        console.info("Data: " + jsonData + "\nStatus: " + status);

        let data = JSON.parse(jsonData);
        let count = data.products.length;
        let productList = document.getElementsByClassName("prod-list")[0];
        for (let i=0; i<count; i++) {
            let wrapper = document.createElement("div");
            wrapper.className = "prod-wrapper";
            let link = document.createElement("a");
            link.href = "https://tw.hwangying.com/product.html?product="
                + data.products[i].name +"&version="
                + data.products[i].version;

            let img = document.createElement("img");
            img.src = "https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg"; //TODO 改圖 data.products[i].smallImage;
            img.style = "width: 100%";

            let info = document.createElement("div");
            info.className = "info";
            let text = document.createElement("p");
            let name = document.createElement("b");
            name.color = "#000000";
            name.innerHTML = data.products[i].name;
            let detail = data.products[i].detail;
            text.appendChild(name);
            text.innerHTML = detail;
            let price = document.createElement("div");
            price.className = "price";
            price.innerHTML = data.products[i].price;

            info.appendChild(text).appendChild(price);
            link.appendChild(img).appendChild(info);
            wrapper.appendChild(link);
            productList.appendChild(wrapper);
        }
    });
}
