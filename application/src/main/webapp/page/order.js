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
});

function addOrder() {
    let order = {};
    order.purchaser = document.getElementById("purchaser").value;
    order.mail = document.getElementById("mail").value;
    order.phone = document.getElementById("phone").value;
    order.receiver = document.getElementById("receiver").value;
    order.address = document.getElementById("address").value;
    order.products = [];
    order.products[0] = document.getElementById("products").value;
    order.money = document.getElementById("money").value;
    order.comment = document.getElementById("comment").value;
    let id_token = getToken();

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
