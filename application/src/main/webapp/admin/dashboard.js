function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    setOrderColumn(primaryKey, purchaser, money, receiver, shippingWay, shippingAddress);
    getOrders(orderEndpoint + "/table");
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    setOrderColumn(primaryKey, purchaser, money, receiver, shippingWay, shippingAddress);
    cleanOrderForm();
    getOrders(orderEndpoint + "/active");
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    setOrderColumn(primaryKey, purchaser, money, receiver, shippingWay, shippingAddress);
    cleanOrderForm();
    getOrders(orderEndpoint + "/paid");
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
    setOrderColumn(primaryKey, purchaser, money, receiver, shippingWay, shippingAddress);
    cleanOrderForm();
    getOrders(orderEndpoint + "/processing");
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
    setOrderColumn(primaryKey, purchaser, money, receiver, shippingWay, shippingAddress);
    cleanOrderForm();
    getOrders(orderEndpoint + "/shipped");
    //todo
}

function onLoad() {
   onAllOrder();
}

function setOrderColumn(v1, v2, v3, v4, v5, v6) {
    document.getElementById("v1").innerHTML = v1;
    document.getElementById("v2").innerHTML = v2;
    document.getElementById("v3").innerHTML = v3;
    document.getElementById("v4").innerHTML = v4;
    document.getElementById("v5").innerHTML = v5;
    document.getElementById("v6").innerHTML = v6;
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
                appendAllOrders(orders);
            }else if (sectionTitle.includes(activeOrder)) {
                appendAllActiveOrders(orders);
            }else if (sectionTitle.includes(paidOrder)) {
                appendAllPaidOrders(orders);
            }else if (sectionTitle.includes(processingOrder)) {
                appendAllProcessingOrders(orders);
            }else if (sectionTitle.includes(shippedOrder)) {
                appendAllShippedOrders(orders);
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

function appendAllOrders(orders) {
    let buttonHTML = ""; //todo
    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< orders.length ; i++) {
        var order =
            "<tr> " +
            "   <td>" + orders[i].mail + " " + orders[i].time + " " + buttonHTML + "</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].money +"</td> " +
            "   <td>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }

}

function appendAllActiveOrders(orders) {
    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< orders.length ; i++) {
        let buttonActiveHTML = "";
        let buttonPayHTML = "";
        if (orders[i].paidTime === null) {
            buttonActiveHTML = "<button onclick='activeOrderButton()'>失效</button>"
            buttonPayHTML = "<button onclick='payOrderButton()'>付款</button>";
        }

        var order =
            "<tr> " +
            "   <td>" + orders[i].mail + " " + orders[i].time + " " + buttonActiveHTML + " " + buttonPayHTML + "</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].money +"</td> " +
            "   <td>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }
}

function appendAllPaidOrders(orders) {


    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< orders.length ; i++) {
        let buttonPayHTML = "";
        if (orders[i].shippedTime === null) {
            buttonPayHTML = "<button onclick='payOrderButton()'>取消</button>"
        }

        var order =
            "<tr> " +
            "   <td>" + orders[i].mail + " " + orders[i].time + " " + buttonPayHTML + "</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].money +"</td> " +
            "   <td>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }
}

function appendAllProcessingOrders(orders) {
    let buttonHTML = "<button onclick='shipOrderButton()'>出貨</button>";
    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< orders.length ; i++) {
        var order =
            "<tr> " +
            "   <td>" + orders[i].mail + " " + orders[i].time + " " + buttonHTML + "</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].money +"</td> " +
            "   <td>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }
}

function appendAllShippedOrders(orders) {
    let buttonHTML = "<button onclick='shipOrderButton()'>取消</button>";
    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< orders.length ; i++) {
        var order =
            "<tr> " +
            "   <td >" + "<span>" + orders[i].mail + " " + orders[i].time + " " + "</span>"+ buttonHTML + "</td> " +
            "   <td>" + orders[i].currency + orders[i].money +"</td> " +
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

function activeOrderButton(e) {
    e = e || window.event;
    var target = e.target || e.srcElement;

    target.disabled = true;
    let key = target.parentNode.innerText.split(" ");
    let mail = key[0].trim();
    let time = key[1].trim();

    if (target.innerText.includes(reactiveOrder)) {
        active(target, mail, time);
    }else {
        deactive(target, mail, time);
    }

}

function active(element, mail, time) {
    let url = orderEndpoint + "/table/active/" + mail + "/" + time;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText= deactiveOrder;

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });

}

function deactive(element, mail, time) {
    let url = orderEndpoint + "/table/deactive/" + mail + "/" + time;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.innerText= reactiveOrder;

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}


function payOrderButton(e) {
    e = e || window.event;
    var target = e.target || e.srcElement;

    target.disabled = true;
    let key = target.parentNode.innerText.split(" ");
    let mail = key[0].trim();
    let time = key[1].trim();

    if (target.innerText.includes(payOrder)) {
        pay(target, mail, time);
    }else {
        depay(target, mail, time);
    }
}

function pay(element, mail, time) {
    let url = orderEndpoint + "/table/pay/" + mail + "/" + time;
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

function depay(element, mail, time) {
    let url = orderEndpoint + "/table/depay/" + mail + "/" + time;
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
    let time = key[1].trim();

    if (target.innerText.includes(shipOrder)) {
        ship(target, mail, time);
    }else {
        deship(target, mail, time);
    }
}

function ship(element, mail, time) {
    let url = orderEndpoint + "/table/ship/" + mail + "/" + time;
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


function deship(element, mail, time) {
    $.ajax({
        type : 'POST',
        url : orderEndpoint + "/table/deship/" + mail + "/" + time,
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