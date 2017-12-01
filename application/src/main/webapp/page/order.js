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
        // document.getElementById("mail").setAttribute("value", localStorage.mail); //TODO get mail from localStorage
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
    //TODO 加上防呆不送出
    let order = {};
    order.purchaser = document.getElementById("purchaser").value;
    order.mail = document.getElementById("mail").value;
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
            console.log("message: " + response)

        },
        error : function(xhr, status, error) {
            console.log( "error: " + error);
        }
    });

}
