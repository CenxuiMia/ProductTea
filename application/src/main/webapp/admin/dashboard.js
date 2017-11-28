let allOrder = "所有訂單";
let activeOrder = "有效訂單";
let paidOrder = "已付款訂單";
let processingOrder = "處理中訂單";
let shippedOrder = "已出貨訂單";

function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
}
