/**
 * Created by huaying on 08/12/2017.
 */
$(document).ready(function () {
    setUp(indexAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
            document.getElementById("signInButton").click();
        }
    );

    onLoading();
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
        calculateTrialTotal();
        return true;
    }
}

function showCartItems() {
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
            "<td>" + value + "</td>" +
            "<td>" + parseInt(item.price)*parseInt(value) + "</td>" +
            "</tr>";

        let orderProduct = item.productName + ";" + item.version + ";"+ value;
        orderProductsList.push(orderProduct);
    });
}

//TODO feature: trial total, activity
let orderProductsList = [];
function calculateTrialTotal() {
    let selectedWay;

    if (document.getElementById('shop').checked === true || document.getElementById('home').checked === true) {
        selectedWay = document.querySelector('input[name="shippingWay"]:checked').value;
    }

    let order = {};
    order.shippingWay = selectedWay;
    order.products = orderProductsList;
    $.ajax({
        type : 'POST',
        url : orderEndpoint,
        headers : {
            Authorization : getToken()
        },
        data: JSON.stringify(order),
        success : function(response) {
            console.log("success trial order: " + response);
            let data = JSON.parse(response);
            document.getElementById("productsPrice").innerHTML = data.productsPrice;
            document.getElementById("shippingCost").innerHTML = data.shippingCost;
            if (typeof data.activity !== "undefined") {
                document.getElementById("activityArea").hidden = false;
                document.getElementById("activity").innerHTML = data.activity;
            } else {
                document.getElementById("activityArea").hidden = true;
            }
            document.getElementById("price").innerHTML = data.price;
        },
        error : function(xhr, status, error) {
            console.log( "error trial order: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);

        },
        complete : function () {
            onLoaded();
        }
    });
}

function goToOrder() {
    if (checkShippingValid()) {
        redirect(URL_ORDER);
    }
}

function goToCart() {
    redirect(URL_CART);
}

function checkShippingValid() {
    let isValid = true;
    let alertClass = " alert";
    let shippingWay = document.getElementById("shippingWay");
    shippingWay.className = shippingWay.className.replace(alertClass, "");
    if (document.getElementById('shop').checked === false &&
        document.getElementById('home').checked === false) {
        console.info("shipping way not selected. " + shippingWay.className);
        shippingWay.className += alertClass;
        console.info( shippingWay.className);
        isValid = false;
    } else {
        localStorage.shippingWay = document.querySelector('input[name="shippingWay"]:checked').value;
        isValid = true;
    }

    document.getElementById("alert").hidden = isValid;
    return isValid;
}

function onLoading() {
    showProgressBar();
    document.getElementsByClassName("content")[0].hidden = true;
}

function onLoaded() {
    hideProgressBar();
    document.getElementsByClassName("content")[0].hidden = false;
}