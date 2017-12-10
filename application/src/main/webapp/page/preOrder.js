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
            // document.getElementById("signInButton").click();
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
            // "<img src=" +item.smallImage +">" + item.productName + item.version + //TODO change smallImage
            "<img class='cartImage' src=https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg>" +
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

//TODO trial total
let orderProductsList = [];
function calculateTrialTotal(selectedRadio) {
    let selectedWay;
    if (selectedWay !== selectedRadio.value) {
        selectedWay = selectedRadio.value;
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
            console.log("success trail order: " + response);

        },
        error : function(xhr, status, error) {
            console.log( "error trial order: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);

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
        let value = document.querySelector('input[name="shippingWay"]:checked').value;
        localStorage.shippingWay = value === shop ? shippingWayShop : shippingWayHome;
        isValid = true;
    }

    document.getElementById("alert").hidden = isValid;
    return isValid;
}