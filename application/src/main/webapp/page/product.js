/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(productAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
});

let productDataAsKey;
let name = getParameterByName("name");
let version = getParameterByName("version");

console.info("name :" + name);
console.info("version :" + version);

$.ajax({
    type : 'GET',
    url : productEndpoint + "/" + name + "/" + version,
    success : function(response) {
        console.log("product: " + response);
        productDataAsKey = response;

        let data = JSON.parse(response);
        document.getElementById("smallImage").setAttribute("src", data.smallImage);
        document.getElementById("productName").innerHTML = data.productName;
        document.getElementById("version").innerHTML = data.version;
        document.getElementById("introduction").innerHTML = data.introduction;
        document.getElementById("currency").innerHTML = data.currency;
        document.getElementById("price").innerHTML = data.price;
        document.getElementById("video").setAttribute("src", data.video);

        let images = document.getElementById("images");
        let imgSize = data.images.length;
        for (let i=0; i<imgSize; i++) {
            let image = document.createElement("img");
            image.src = data.images[i];
            images.appendChild(image);
        }
    },
    error : function(xhr, status, error) {
        console.log("error: " + error + ", status: " + status);
        //TODO 測試用要拿掉
        productDataAsKey = JSON.stringify({"productName":"測試","version":"喝喝茶","introduction":"簡介文字","details":"詳細介紹文字","smallImage":"https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg","video":"A","images":[],"price":123.0,"currency":"NT","tag":"A"});
    }
});

function getParameterByName(name) {
    let url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function addToCart() {
    //TODO 加入購物車的商品必須全為同幣別 currency
    let cartItems;
    let selected = document.getElementById("count");
    let count = parseInt(selected.options[selected.selectedIndex].value);
    console.info("selected count = " + count);

    if (localStorage.getItem("cartItems") === null) {
        console.info("Init cartItems to localstorage");
        cartItems = new Map();
    } else {
        console.info("Localstorage has cartItems");
        cartItems = new Map(JSON.parse(localStorage.getItem("cartItems")));
        console.info("Get old cartItems: " + JSON.parse(localStorage.getItem("cartItems")));
        if (cartItems.has(productDataAsKey)) {
            console.info("Just update cartItems to localstorage");
            count += parseInt(cartItems.get(productDataAsKey));
            console.info("count += " + count);
        }
    }
    cartItems.set(productDataAsKey, count);
    localStorage.setItem("cartItems", JSON.stringify(Array.from(cartItems.entries())));
    showSnackBarAutoClose(document.getElementById("snackbar"), addCartSuccess);

    console.info("localstorage getItem: " + localStorage.getItem("cartItems"));
    console.info("JSON parse: " + JSON.parse(localStorage.cartItems));
}