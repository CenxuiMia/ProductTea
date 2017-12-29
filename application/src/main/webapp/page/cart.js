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

    if (checkCartValid() === true) {
        showCartItems();
    }
});

function checkCartValid() {
    if (localStorage.getItem("cartItems") === null ||
        new Map(JSON.parse(localStorage.getItem("cartItems"))).size === 0) {
        document.getElementById("cartProductsList").innerHTML = "";
        document.getElementsByClassName("content")[0].innerHTML = cartEmpty +
            "<br><a class='actionButton confirmButton1' href="+ URL_PRODUCTS + ">" + goToProductsList + "</a>";
        return false;
    } else {
        return true;
    }
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
                        "<img class='cartImage' src=" +item.smallImage +">" +
                        "<span>" + item.productName + item.version + "</span>" +
                    "</a>" +
                "</td>" +
                "<td>" + item.price + "</td>" +
                "<td><input onchange='updateSubtotal(event,"+ key +")'type='number' min='1' value=" + value + "></td>" +
                "<td>" + parseInt(item.price)*parseInt(value) + "</td>" +
                "<td><button onclick='removeItem("+ key +")'>"+ "X" +"</button></td>" +
            "</tr>";
    });
}

function updateSubtotal(event, key) {
    key = JSON.stringify(key);
    let target = event.target;
    let number = target.value;
    if (number < 1) {
        number = target.value = 1;
    }
    let tableRow = target.parentNode.parentNode;
    let price = tableRow.childNodes[1].innerHTML;
    tableRow.childNodes[3].innerHTML = number * price;

    let cartItem = new Map(JSON.parse(localStorage.getItem("cartItems")));
    cartItem.set(key, number);
    localStorage.setItem("cartItems", JSON.stringify(Array.from(cartItem.entries())));
    console.info("JSON parse: " + JSON.parse(localStorage.cartItems));
}

function removeItem(key) {
    key = JSON.stringify(key);
    let cartItem = new Map(JSON.parse(localStorage.getItem("cartItems")));
    cartItem.delete(key);
    localStorage.setItem("cartItems", JSON.stringify(Array.from(cartItem.entries())));
    console.info("JSON parse: " + JSON.parse(localStorage.cartItems));
    location.reload();
}

function clearCart() {
    localStorage.removeItem("cartItems");
}

function goToOrder() {
    if (checkCartValid()) {
        redirect(URL_PRE_ORDER);
    }
}
