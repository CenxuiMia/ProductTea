function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    setOrderColumn(primaryKey, purchaser, money, receiver, address);
    queryOrders(orderEndpoint + "/table");

    //todo
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
    //todo
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    cleanOrderForm();
    queryOrders(orderEndpoint + "/paid");

    //todo
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
    cleanOrderForm();
    queryOrders(orderEndpoint + "/processing");
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
    cleanOrderForm();
    queryOrders(orderEndpoint + "/shipped");
    //todo
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
    let sectionTitle = document.getElementById("sectionTitle").innerText;

    var buttonHTML;

    if (sectionTitle.includes(allOrder)) {
        buttonHTML = "<button>未定</button>" //todo
    }else if (sectionTitle.includes(activeOrder)) {
        buttonHTML = "<button>未定</button>"
    }else if (sectionTitle.includes(paidOrder)) {
        buttonHTML = "<button onclick='payOrderButton()'>取消</button>"
    }else if (sectionTitle.includes(processingOrder)) {
        buttonHTML = "<button onclick='shipOrderButton()'>出貨</button>"
    }else if (sectionTitle.includes(shippedOrder)) {
        buttonHTML = "<button onclick='shipOrderButton()'>取消</button>"
    }

    let orderForm = document.getElementById("orderForm");

    for (var i = 0; i< data.length ; i++) {
        var order =
            "<tr> " +
            "   <td>" + data[i].mail + " " + data[i].time + " " + buttonHTML + "</td> " +
            "   <td>" + data[i].purchaser+"</td> " +
            "   <td>" + data[i].money +"</td> " +
            "   <td>" + data[i].receiver + "</td> " +
            "   <td>" + data[i].address + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }
}

function activeOrderButton(e) {

}

function active() {

}

function deactive() {

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
            element.innerText= cancelOrder;

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
            element.innerText= cancelOrder;

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