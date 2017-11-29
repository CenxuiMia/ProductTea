let allOrder = "所有訂單";
let activeOrder = "有效訂單";
let paidOrder = "已付款訂單";
let processingOrder = "處理中訂單";
let shippedOrder = "已出貨訂單";

let mail = "信箱";
let time = "時間";
let money = "總額";
let receiver = "收件人";
let address = "地址";

function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    setOrderColumn(mail, time, money, receiver, address);
    queryOrders(orderEndpoint);
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
}

function onLoad() {
   onAllOrder();
}

function setOrderColumn(v1, v2, v3, v4, v5) {
    document.getElementById("v1").innerHTML = v1;
    document.getElementById("v2").innerHTML = v2;
    document.getElementById("v3").innerHTML = v3;
    document.getElementById("v4").innerHTML = v4;
    document.getElementById("v5").innerHTML = v5;
}

function queryOrders(url) {
    $.ajax({
        type : 'GET',
        url : url,
        success : function(response) {
            let data = JSON.parse(response);
            let orders = data.orders;
            appendText(orders);
        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status);
        }
    });
}

function cleanOrderForm() {
    document.getElementById("orderForm").innerHTML = "";

}

function appendText(data) {
    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< data.length ; i++) {
        var order =
            "<tr> " +
            "   <td>" + data[i].mail +"</td> " +
            "   <td>" + data[i].time +"</td> " +
            "   <td>" + data[i].money +"</td> " +
            "   <td>dolor</td> " +
            "   <td>sit</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }

}

function onScroll() {

}