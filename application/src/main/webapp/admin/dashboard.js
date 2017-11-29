let allOrder = "所有訂單";
let activeOrder = "有效訂單";
let paidOrder = "已付款訂單";
let processingOrder = "處理中訂單";
let shippedOrder = "已出貨訂單";

let primaryKey = "訂單序號";
let time = "時間";
let money = "總額";
let purchaser = "購買人";
let receiver = "收件人";
let address = "地址";

function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    setOrderColumn(primaryKey, purchaser, money, receiver, address);
    queryOrders(orderEndpoint + "/table");
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    cleanOrderForm();
    // queryOrders(orderEndpoint);
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    cleanOrderForm();
    queryOrders(orderEndpoint + "/paid");
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
            "   <td>" + data[i].mail + "," + data[i].time + "<img type='image' src='image/button_shipped.png' onclick='shipOrderButton()'>" + "</td> " +
            "   <td>" + data[i].purchaser+"</td> " +
            "   <td>" + data[i].money +"</td> " +
            "   <td>" + data[i].receiver + "</td> " +
            "   <td>" + data[i].address + "</td> " +
            "</tr>";               // Create element with HTML
        orderForm.innerHTML += order;      // Append the new elements
    }
}

function shipOrderButton(e) {
    e = e || window.event;
    var target = e.target || e.srcElement;

    target.disabled = true;
    let key = target.parentNode.innerText.split(",");
    let mail = key[0].trim();
    let time = key[1].trim();

    if (target.src.endsWith('button_shipped.png') ) {
        shipOrder(target, mail, time);
    }else {
        deshipOrder(target, mail, time);
    }
}

function shipOrder(element, mail, time) {
    let url = orderEndpoint + "/table/ship/" + mail + "/" + time;
    console.info("url :" + url);

    $.ajax({
        type : 'POST',
        url : url,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.src = 'image/button_cancel.png';

        },
        error : function(xhr, status, error) {
            element.disabled = false;
            alert(error);
        }
    });
}


function deshipOrder(element, mail, time) {
    $.ajax({
        type : 'POST',
        url : orderEndpoint + "/table/deship/" + mail + "/" + time,
        success : function(response) {
            console.info(response)
            element.disabled = false;
            element.src = 'image/button_shipped.png';

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