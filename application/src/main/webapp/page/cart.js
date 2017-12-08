/**
 * Created by huaying on 08/12/2017.
 */
$(document).ready(function () {
    setUp(cartAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );

    checkCartValid();
});

function checkCartValid() {
    if (localStorage.getItem("cartItems") === null) {
        document.getElementsByClassName("bannerRight")[0].hidden = false;
        document.getElementById("cartProductsList").innerHTML = "";
        document.getElementById("shipAndPrice").innerHTML = cartEmpty +
            "<br><a class='actionButton' href="+ URL_PRODUCTS + ">" + goToProductsList + "</a>";
        return false;
    } else {
        showCartItems();
        return true;
    }
}

function clearCart() {
    localStorage.removeItem("cartItems");
}

function showCartItems() {
    //TODO show original price if exists
    let cartItems = new Map(JSON.parse(localStorage.getItem("cartItems")));
    console.info("cartItems size:" + cartItems.size);

    let cartTable = document.getElementById("cartTable");

    cartItems.forEach(function(value,key,map) {
        console.info("key :" + key);
        console.info("value :" + value);

        let item = JSON.parse(key);
        let queryString = "https://tw.hwangying.com/product.html?name=" + item.productName + "&version=" + item.version;

        cartTable.innerHTML +=
            "<tr>" +
            "<td class='cartProductName'>" +
            "<a href=" + queryString + ">" +
            // "<img src=" +item.smallImage +">" + item.productName + item.version + //TODO change smallImage
            "<img class='cartImage' src=https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg>" +
            "<span>" + item.productName + item.version + "</span>" +
            "</a>" +
            "</td>" +
            "<td>" + item.price + "</td>" +
            "<td><input onchange='updateSubtotal()'type='number' value=" + value + "></td>" +
            "<td>" + parseInt(item.price)*parseInt(value) + "</td>" +
            "<td><button>"+ "X" +"</button></td>" +
            "</tr>";
    });
}

function updateSubtotal() {
//TODO update subtotal
}

function goToOrder() {
    if (checkCartValid()) {
        redirect(URL_PRE_ORDER);
    }
}
