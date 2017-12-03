/**
 * Created by huaying on 28/10/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(orderAuthData,
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
        //TODO 購物車沒東西的頁面 html
    } else {
        setInputWithUserData();
        showCartItems();
    }
}

function setInputWithUserData() {
    if (localStorage.getItem("lastName") !== null) {
        console.info("LocalStorage! lastName: " + localStorage.lastName +
            ", firstName: " + localStorage.firstName +
            "\nphone: " + localStorage.phone +
            ", address: " + localStorage.address);

        document.getElementById("purchaser").setAttribute("value", localStorage.lastName + localStorage.firstName);
        document.getElementById("phone").setAttribute("value", localStorage.phone);
        document.getElementById("shippingAddress").setAttribute("value", localStorage.address);
    } else {
        console.info("Query DB!");
        //TODO query user data from DB
    }
}

function showCartItems() {
    // {"productName":"綠茶","version":"翠玉","introduction":"Ａ","details":"Ａ",
    // "smallImage":"Ａ","video":"A","images":[],"price":400.0,"tag":"Ａ"}, 3, {key},1
    let cartItems = new Map(JSON.parse(localStorage.getItem("cartItems")));
    console.info("cartItems size:" + cartItems.size);

    let cartTable = document.getElementById("cartTable");

    cartItems.forEach(function(value,key,map) {
        console.info("key :" + key);
        console.info("value :" + value);

        let item = JSON.parse(key);
        let queryString = "https://tw.hwangying.com/product.html?name=" + item.productName + "&version=" + item.versio;

        cartTable.innerHTML +=
            "<tr>" +
                "<td class='cartProductName' style='background-color: #d4edda'>" +
                    "<a href=" + queryString + ">" +
                        // "<img src=" +item.smallImage +">" + item.productName + item.version + //TODO change smallImage
                        "<img class='cartImage' src=https://farm5.staticflickr.com/4519/24605617318_1a9f4e861c_z.jpg>" +
                        "<span>" + item.productName + item.version + "</span>" +
                    "</a>" +
                "</td>" +
                "<td style='background-color: #fcf8e3'>" + value + "</td>" +
                "<td style='background-color: #f8d7da'>" + item.price + "</td>" +
            "</tr>";

        //Add product to orderProductsList for order usage
        let orderProduct = item.productName + ";" + item.version + ";"+ value;
        orderProductsList.push(orderProduct);
    });

}

let orderProductsList = [];

function addOrder() {
    //TODO 加上防呆不送出, 成功後redirect到會員購物資料
    console.info("onclick addOrder");

    if (!checkInputValid()) {
        console.info("Check input not valid!");
        return;
    }

    showSnackBar(document.getElementById("snackbar"), processing);
    //disable buttons
    document.getElementById("submitButton").disabled = true;

    let order = {};
    order.purchaser = document.getElementById("purchaser").value;
    order.phone = document.getElementById("phone").value;
    order.receiver = document.getElementById("receiver").value;
    order.shippingWay = document.querySelector('input[name="shippingWay"]:checked').value;
    order.shippingAddress = document.getElementById("shippingAddress").value;
    order.products = orderProductsList;
    order.comment = document.getElementById("comment").value;
    let id_token = getToken();

    console.info("Add order: " + JSON.stringify(order));

    $.ajax({
        type : 'PUT',
        url : orderEndpoint,
        headers : {
            Authorization : id_token
        },
        data: JSON.stringify(order),
        success : function(response) {
            console.log("message: " + response);

            showSnackBarAutoClose(document.getElementById("snackbar"), processingSuccess);
            localStorage.removeItem("cartItems");
        },
        error : function(xhr, status, error) {
            console.log( "error: " + error);
            showSnackBarAutoClose(document.getElementById("snackbar"), processingfailed);
        },
        complete : function (jqxhr, status) {
            //enable buttons
            document.getElementById("submitButton").disabled = false;
        }
    });

}



function checkInputValid() {
    let isValid = true;
    let labelArray = ["inputPurchaser", "inputPhone", "inputReceiver", "inputShippingAddress"];
    let inputArray = ["purchaser", "phone", "receiver", "shippingAddress"];
    let alertClass = "alert";
    for (let i=0; i<labelArray.length; i++) {
        let label = document.getElementById(labelArray[i]);
        let input = document.getElementById(inputArray[i]);
        console.info(inputArray[i]);
        if (label.className !== null && label.className.indexOf(alertClass) !== -1) {
            console.info(inputArray[i] + " className = " + label.className);
            label.className = label.className.replace(alertClass, "");
        }
        if (input.value === null || input.value === "") {
            label.className += alertClass;
            isValid = false;
        }
    }

    let shippingWay = document.querySelector('input[name="shippingWay"]:checked');
    let shippingWayElement = document.getElementById("shippingWay");
    shippingWayElement.className = shippingWayElement.className.replace(alertClass, "");
    if (shippingWay === null || shippingWay.value === "") {
        shippingWayElement.className += alertClass;
        isValid = false;
    }

    if (document.getElementById("phone").value !== "" &&
        !isNumeric(document.getElementById("phone").value)) {
        document.getElementById("inputPhone").className += alertClass;
        isValid = false;
    }

    document.getElementById("alert").hidden = isValid;

    return isValid;
}
