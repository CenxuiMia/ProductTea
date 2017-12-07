function onAllCash() {
    setTableHead();
    clearReceiptForm()
    getCash(reportEndpoint + "/cash/all")
    
}

function onDailyCash() {
    setTableHead();
    clearReceiptForm();
    getCash(reportEndpoint + "/cash/daily/" + document.getElementById("daily").value);
}

function onRangeCash() {
    setTableHead();
    clearReceiptForm();
    let from = document.getElementById("from").value;
    let to = document.getElementById("to").value;
    getCash(reportEndpoint + "/cash/range/" + from + "/" + to);
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
            let receipts = data.receipts;
            document.getElementById("revenue").innerHTML = data.revenue;
            appendReceipts(receipts);
        },
        error : function(xhr, status, error) {
            console.log("error: " + error + ", status: " + status);

        }
    });
}

function clearReceiptForm() {
    document.getElementById("receiptForm").innerHTML = "";
}

function appendReceipts(receipts) {
    let receiptForm = document.getElementById("receiptForm");

    for (let i = 1; i <= receipts.length; i++) {
        let receipt =
            "<tr> " +
            "   <td>" + i + "</td> " +
            "   <td>" + receipts[i].mail + " " + receipts[i].orderDateTime + "</td> " +
            "   <td>" + receipts[i].paymentMethod+"</td> " +
            "   <td>" + receipts[i].price +"</td> " +
            "</tr>";
        receiptForm.innerHTML += receipt;
    }

}