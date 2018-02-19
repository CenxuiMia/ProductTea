/**
 * Created by huaying on 06/12/2017.
 */

$(document).ready(function () {
    setUp(
        indexAuthData,
        function () {
            // signed in
        },
        function () {
            console.info("signed out on user page")
        });

    getOrders();
});

function getOrders() {
    //TODO show all details about orders
    onLoading();

    $.ajax({
        type : 'GET',
        url : orderEndpoint,
        headers : {
            Authorization : getToken()
        },
        success : function(response) {
            console.log("message: " + response);
            let orders = JSON.parse(response);
            for (let i=0; i<orders.orders.length; i++) {
                let order = orders.orders[i];
                let status = typeof order.shippedDate !== "undefined"? orderShipped :
                    typeof order.payDate !== "undefined"? orderProcessing :
                    order.isActive? orderUnpaid : orderInactive;
                let productsInfo = order.products.toString();
                document.getElementById("ordersTable").innerHTML +=
                    "<tr>" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td>" + productsInfo.replace(/;/g," ").replace(/,/g, "<br>") + "</td>" +
                        "<td>" + "NT." + order.price + "</td>" +
                        "<td>" + order.orderDateTime + "</td>" +
                        "<td>" + new Date(Number(order.orderDateTime)).toLocaleDateString() + "</td>" +
                        "<td>" + status + "</td>" +
                    "</tr>";
            }
        },
        error : function(xhr, status, error) {
            console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
        },
        complete : function () {
            onLoaded();
        }
    });
}

function onLoading() {
    showProgressBar();
    document.getElementById("ordersTable").hidden = true;
}

function onLoaded() {
    hideProgressBar();
    document.getElementById("ordersTable").hidden = false;
}

