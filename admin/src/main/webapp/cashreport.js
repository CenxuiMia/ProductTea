function onAllCash() {
    setTableHead();
    getCash(orderEndpoint + "/")
    
}

function onDailyCash() {
    setTableHead();
}

function onDailyRangeCash() {
    setTableHead();
}

function setTableHead() {
    document.getElementById("v1").innerText = "No.";
    document.getElementById("v2").innerText = "Id";
    document.getElementById("v3").innerText = "paymentMethod";
    document.getElementById("v4").innerText = "price";
}

function getCash(url) {
    $.ajax({
        type : 'GET',
        url : url,
        success : function(response) {
            let data = JSON.parse(response);
            let orders = data.orders;
            lastKey = data.lastKey;
            appendOrders(orders);
            ableScroll = true;

        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status);
            ableScroll = true;

        }
    });
}