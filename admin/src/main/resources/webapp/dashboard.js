function onAllOrder() {
    document.getElementById("sectionTitle").innerHTML = allOrder;
    cleanOrderForm();
    getOrders(orderEndpoint + "/table");
}

function onActiveOrder() {
    document.getElementById("sectionTitle").innerHTML = activeOrder;
    cleanOrderForm();
    getOrders(orderEndpoint + "/active");
}

function onBankOrder() {
    document.getElementById("sectionTitle").innerHTML = bankOrder;
    cleanOrderForm();
    getOrders(orderEndpoint + "/bank");
}

function onPaidOrder() {
    document.getElementById("sectionTitle").innerHTML = paidOrder;
    cleanOrderForm();
    getOrders(orderEndpoint + "/paid");
}

function onProcessingOrder() {
    document.getElementById("sectionTitle").innerHTML = processingOrder;
    cleanOrderForm();
    number = 1;
    getOrders(orderEndpoint + "/processing");
}

function onShippedOrder() {
    document.getElementById("sectionTitle").innerHTML = shippedOrder;
    cleanOrderForm();
    number = 1;
    getOrders(orderEndpoint + "/shipped");
    //todo
}

function onLoad() {

}

let lastKey;
let number = 1;

function getOrders(url) {
    console.info(url);
    $.ajax({
        type : 'GET',
        url : url,
        success : function(response) {
            if (response === null) return;

            let data = JSON.parse(response);
            let orders = data.orders;

            if (orders === null) return;

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

function cleanOrderForm() {
    document.getElementById("orderForm").innerHTML = "";
    number =1;
}

let next = null;

function appendOrders(orders) {
    let orderForm = document.getElementById("orderForm");
    let sectionTile = document.getElementById("sectionTitle").innerText;

    for (var i = 0; i < orders.length ; i++) {
        let buttonActiveHTML = "";
        let buttonPayHTML = "";
        let buttonShipHTML = "";

        if (orders[i].paidDate === null &&
            orders[i].processingDate === null &&
            orders[i].shippedDate === null) {

            buttonActiveHTML = orders[i].isActive === null ?
                "<button onclick='activeOrderButton()'>訂單復原</button>" :
                "<button onclick='activeOrderButton()'>訂單取消</button>"
        }

        if (orders[i].isActive === true && orders[i].paidDate === null) {
            buttonPayHTML =
                "<button onclick='payOrderButton()'>付款確認</button>";
        }

        if (orders[i].paidDate !== null && orders[i].processingDate !== null) {
            buttonPayHTML =
                "<button onclick='payOrderButton()'>付款取消</button>";
        }


        if (orders[i].processingDate !== null) {
            buttonShipHTML =
                "<button onclick='shipOrderButton()'>出貨確認</button>"
        }else if (orders[i].shippedDate !== null){
            buttonShipHTML =
                "<button onclick='shipOrderButton()'>出貨取消</button>";;
        }


        let productForm = orders[i].products.toString().replace(/;/g," ").replace(/,/g, "<br>");

        let comment = orders[i].comment === null ? "" : orders[i].comment;
        let paidDate = orders[i].paidDate === null ? "" : orders[i].paidDate;
        let paidTime = orders[i].paidTime === null ? "" : "T" + orders[i].paidTime;
        let processingDate = orders[i].processingDate === null ? "" : orders[i].processingDate;
        let shippedDate = orders[i].shippedDate === null ? "" : orders[i].shippedDate;
        let shippedTime = orders[i].shippedTime === null ? "" : "T" + orders[i].shippedTime;


        var order =
            "<tr> " +
            "   <td>"+ number++ +"</td> " +
            "   <td>" + orders[i].mail + " " + orders[i].orderDateTime + " " + buttonActiveHTML + buttonPayHTML + buttonShipHTML + "</td> " +
            "   <td class='productForm'>" + productForm + "</td> " +
            "   <td>" + orders[i].price +"</td> " +
            "   <td>" + orders[i].purchaser+"</td> " +
            "   <td>" + orders[i].purchaserPhone+"</td> " +
            "   <td class='receiver'>" + orders[i].receiver + "</td> " +
            "   <td>" + orders[i].receiverPhone + "</td> " +
            "   <td>" + orders[i].shippingWay + "</td> " +
            "   <td>" + orders[i].shippingAddress + "</td> " +
            "   <td>" + comment+"</td> " +
            "   <td>" + paidDate + paidTime + "</td> " +
            "   <td>" + processingDate + "</td> " +
            "   <td>" + shippedDate + shippedTime + "</td> " +
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
    let dateTime = key[1].trim();

    if (target.innerText.includes(reactiveOrder)) {
        active(target, mail, dateTime);
    }else {
        deactive(target, mail, dateTime);
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

function active(element, mail, dateTime) {
    let url = orderEndpoint + "/table/active/" + mail + "/" + dateTime;
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

function deactive(element, mail, dateTime) {
    let url = orderEndpoint + "/table/deactive/" + mail + "/" + dateTime;
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

let ableScroll = false;

function onScroll() {
    if (lastKey === null) return;

    if (ableScroll === false) {
        return;
    }else {
        ableScroll = false;
    }

    let limit = 1000;

    let sectionTitle = document.getElementById("sectionTitle").innerHTML;
    let url;
    if (sectionTitle.includes(allOrder)) {
       url = orderEndpoint + "/table/" + lastKey.mail + "/" + lastKey.orderDateTime + "/" + limit;
    }else if (sectionTitle.includes(activeOrder)) {
        url = orderEndpoint + "/active/" + lastKey.mail + "/" + lastKey.orderDateTime + "/" + limit;
    }else if (sectionTitle.includes(paidOrder)) {
        url = orderEndpoint + "/paid/" + lastKey.paidDate + "/" + lastKey.paidTime + "/" +
            lastKey.mail + "/" + lastKey.orderDateTime + "/" + limit;
    }else if (sectionTitle.includes(processingOrder)) {
        url = orderEndpoint + "/processing/" + lastKey.processingDate + "/" + lastKey.owner + "/" +
        lastKey.mail + "/" + lastKey.orderDateTime + "/" + limit;
    }else if (sectionTitle.includes(shippedOrder)) {
        url = orderEndpoint + "/shipped/" + lastKey.shippedDate + "/" + lastKey.shippedTime + "/" +
            lastKey.mail + "/" + lastKey.orderDateTime + "/" + limit;
    }
    getOrders(url);
}

function searchAllOrder() {
    let mail = document.getElementById("mail").value;
    let orderDateTime = document.getElementById("orderDateTime").value;

    if (mail === null) return;
    let url = orderEndpoint + "/table/" + mail.trim();
    if (orderDateTime !== null)
        url = url + "/" + orderDateTime.trim();
    cleanOrderForm();

    getOrders(url);
}

function searchBankOrder() {
    let bankInformation = document.getElementById("bankInformation").value;

    if (bankInformation === null) return;

    let url = orderEndpoint + "/bank/" + bankInformation;

    cleanOrderForm();
    getOrders(url);
}


function searchPaidOrder() {
    let paidDate = document.getElementById("paidDate").value;

    if (paidDate === null) return;

    let url = orderEndpoint + "/paid/" + paidDate;

    let paidTime = document.getElementById("paidTime").value;

    if (paidTime !== null)
        url = url + "/" + paidTime;

    cleanOrderForm();
    getOrders(url);
}

function searchProcessingOrder() {
    let processingDate = document.getElementById("processingDate").value;

    if (processingDate === null) return;

    let url = orderEndpoint + "/processing/" + processingDate.trim();

    let owner = document.getElementById("owner").value;

    if (owner !== null)
        url = url + "/" + owner.trim();


    cleanOrderForm();
    getOrders(url);
}

function searchShippedOrder() {
    let shippedDate = document.getElementById("shippedDate").value;

    if (shippedDate === null) return;

    let url = orderEndpoint + "/shipped/" + shippedDate.trim();

    let shippedTime = document.getElementById("shippedTime").value;

    if (shippedTime !== null)
        url = url + "/" + shippedTime.trim();
    cleanOrderForm();
    getOrders(url);
}

function sortTable(compare) {
    let table = document.getElementById("orderForm");
    let tr = table.getElementsByTagName("TR");

    let ar = [];

    for (let i = 0; i< tr.length; i++) {
        ar.push(tr[i]);
    }

    ar.sort(compare);

    table.innerHTML = "";

    for (let i = 0; i< ar.length; i++) {
        table.appendChild(ar[i]);
    }
}

function sortTableByPriceAS() {
    sortTable(function (a,b) {
        return a.getElementsByTagName("TD")[3].innerHTML - b.getElementsByTagName("TD")[3].innerHTML;

    })
}

function sortTableByPriceDS() {
    sortTable(function (a,b) {
        return b.getElementsByTagName("TD")[3].innerHTML - a.getElementsByTagName("TD")[3].innerHTML;

    })
}

function sortTableByOrderDateTimeAS() {
    sortTable(function (a, b) {
        let d1 = a.getElementsByTagName("TD")[1].innerHTML.toString().split(" ")[1];
        let d2 = b.getElementsByTagName("TD")[1].innerHTML.toString().split(" ")[1];

        console.info(d1)
        console.info(d2)
        return new Date(d1) > new Date(d2);
    })
}

function sortTableByOrderDateTimeDS() {
    sortTable(function (a, b) {
        let d1 = a.getElementsByTagName("TD")[1].innerHTML.toString().split(" ")[1];
        let d2 = b.getElementsByTagName("TD")[1].innerHTML.toString().split(" ")[1];

        console.info(d1)
        console.info(d2)

        return new Date(d1) < new Date(d2);
    })
}

function sortTableByShippingWay() {
    sortTable(function (a,b) {
        return a.getElementsByTagName("TD")[8].innerHTML - b.getElementsByTagName("TD")[8].innerHTML;

    })
}