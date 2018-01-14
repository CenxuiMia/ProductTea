/**
 * Created by huaying on 06/12/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(
        userAuthData,
        function () {
            // signed in
        },
        function () {
            console.info("signed out on user page")
        });

    getOrders();
});

function getOrders() {
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
                document.getElementById("ordersTable").innerHTML +=
                    "<tr>" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td>" + order.products + "</td>" +
                        "<td>" + "NT." + order.price + "</td>" +
                        "<td>" + order.orderDateTime + "</td>" +
                        "<td>" + status + "</td>" +
                    "</tr>";
            }
        },
        error : function(xhr, status, error) {
            console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
        }
    });
}

