function searchAllOrder() {
    let mail = document.getElementById("mail").value;
    let orderDateTime = document.getElementById("orderDateTime").value;

    getOrders(orderEndpoint + "/table/" + mail + "/" + orderDateTime);
}

function searchPaidOrder() {
    let paidDate = document.getElementById("paidDate").value;
    let paidTime = document.getElementById("paidTime").value;

    getOrders(orderEndpoint + "/paid/" + paidDate + "/" + paidTime);
}

function searchProcessingOrder() {
    let processingDate = document.getElementById("processingDate").value;
    let owner = document.getElementById("owner").value;

    getOrders(orderEndpoint + "/processing/" + processingDate + "/" + owner);
}

function searchShippedOrder() {
    let shippedDate = document.getElementById("shippedDate").value;
    let shippedTime = document.getElementById("shippedTime").value;

    getOrders(orderEndpoint + "/shipped/" + shippedDate + "/" + shippedTime);
}