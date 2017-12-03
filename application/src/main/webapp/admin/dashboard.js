function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    setOrderColumn(primaryKey, money, products, receiver, shippingWay, shippingAddress,
    comment, purchaser, paidTime, processingDate, shippedTime);
    getOrders(orderEndpoint + "/table");
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    setOrderColumn(primaryKey, money, products, receiver, shippingWay, shippingAddress,
        comment, purchaser, paidTime, processingDate, shippedTime);

    cleanOrderForm();
    getOrders(orderEndpoint + "/active");
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    setOrderColumn(primaryKey, money, products, receiver, shippingWay, shippingAddress,
        comment, purchaser, paidTime, processingDate, shippedTime);
    cleanOrderForm();
    getOrders(orderEndpoint + "/paid");
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
    setOrderColumn(primaryKey, money, products, receiver, shippingWay, shippingAddress,
        comment, purchaser, paidTime, processingDate, shippedTime);
    cleanOrderForm();
    getOrders(orderEndpoint + "/processing");
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
    setOrderColumn(primaryKey, money, products, receiver, shippingWay, shippingAddress,
        comment, purchaser, paidTime, processingDate, shippedTime);
    cleanOrderForm();
    getOrders(orderEndpoint + "/shipped");
    //todo
}

function onLoad() {
   onProcessingOrder();
}

function setOrderColumn(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11) {
    document.getElementById("v1").innerHTML = v1;
    document.getElementById("v2").innerHTML = v2;
    document.getElementById("v3").innerHTML = v3;
    document.getElementById("v4").innerHTML = v4;
    document.getElementById("v5").innerHTML = v5;
    document.getElementById("v6").innerHTML = v6;
    document.getElementById("v7").innerHTML = v7;
    document.getElementById("v8").innerHTML = v8;
    document.getElementById("v9").innerHTML = v9;
    document.getElementById("v10").innerHTML = v10;
    document.getElementById("v11").innerHTML = v11;
}

function getOrders(url) {
    $.ajax({
        type : 'GET',
        url : url,
        success : function(response) {
            let data = JSON.parse(response);
            let sectionTitle = document.getElementById("sectionTitle").innerHTML;
            let orders = data.orders;
            if (sectionTitle.includes(allOrder)) {
                appendOrders(orders);
            }else if (sectionTitle.includes(activeOrder)) {
                appendOrders(orders);
            }else if (sectionTitle.includes(paidOrder)) {
                appendOrders(orders);
            }else if (sectionTitle.includes(processingOrder)) {
                appendOrders(orders);
            }else if (sectionTitle.includes(shippedOrder)) {
                appendOrders(orders);
            }
        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status);
        }
    });
}

function cleanOrderForm() {
    document.getElementById("orderForm").innerHTML = "";
}

function appendOrders(orders) {
    let orderForm = document.getElementById("orderForm");
    let sectionTile = document.getElementById("sectionTitle").innerText;


    for (var i = 0; i< orders.length ; i++) {
        let buttonPayHTML = "";
        let buttonShipHTML = "";

        if (orders[i].shippedTime !== null) {
            buttonShipHTML =  "<button onclick='shipOrderButton()'>出貨取消</button>"
        }else if(orders[i].processingDate !== null) {
            buttonShipHTML =  "<button onclick='shipOrderButton()'>出貨確認</button>"
        }else if (orders[i].paidTime === null) {
            buttonPayHTML = "<button onclick='payOrderButton()'>付款確認</button>";
        }

        if (!sectionTile.includes(processingOrder)
            && orders[i].paidTime !== null && orders[i].shippedTime === null) {
            buttonPayHTML = "<button onclick='payOrderButton()'>付款取消</button>"
        }

        var order =
            "<tr> " +
            "   <td>" + orders[i].mail + " " + orders[i].orderDateTime + " "  + buttonPayHTML + buttonShipHTML + "</td> " +
            "   <td>" + orders[i].currency + orders[i].price +"</td> " +
            "   <td>" + orders[i].products+"</td> " +
            "   <td>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "   <td>" + orders[i].comment+"</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].paidTime+"</td> " +
            "   <td>" + orders[i].processingDate+"</td> " +
            "   <td>" + orders[i].shippedTime+"</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }

}

function payOrderButton(e) {
    e = e || window.event;
    var target = e.target || e.srcElement;

    target.disabled = true;
    let key = target.parentNode.innerText.split(" ");
    let mail = key[0].trim();
    let dateTime = key[1].trim();

    if (target.innerText.includes(payOrder)) {
        pay(target, mail, dateTime);
    }else {
        depay(target, mail, dateTime);
    }
}

function pay(element, mail, dateTime) {
    let url = orderEndpoint + "/table/pay/" + mail + "/" + dateTime;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText= depayOrder;

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}

function depay(element, mail, dateTime) {
    let url = orderEndpoint + "/table/depay/" + mail + "/" + dateTime;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText= payOrder;
        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}

function shipOrderButton(e) {
    e = e || window.event;
    var target = e.target || e.srcElement;

    target.disabled = true;
    console.info("key :" + target.parentNode.innerText);
    let key = target.parentNode.innerText.split(" ");
    let mail = key[0].trim();
    let dateTime = key[1].trim();

    if (target.innerText.includes(shipOrder)) {
        ship(target, mail, dateTime);
    }else {
        deship(target, mail, dateTime);
    }
}

function ship(element, mail, dateTime) {
    let url = orderEndpoint + "/table/ship/" + mail + "/" + dateTime;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText= deShipOrder;

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}


function deship(element, mail, dateTime) {
    $.ajax({
        type : 'POST',
        url : orderEndpoint + "/table/deship/" + mail + "/" + dateTime,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText = shipOrder;

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}



function onScroll() {
 //todo
}