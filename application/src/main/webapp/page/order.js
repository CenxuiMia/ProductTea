/**
 * Created by huaying on 28/10/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(indexAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
            document.getElementById("signInButton").click();
        }
    );

    checkCartValid();
});

function checkCartValid() {
    if (localStorage.getItem("cartItems") === null) {
        document.getElementsByClassName("bannerRight")[0].hidden = false;
        document.getElementById("accountFields").innerHTML = cartEmpty +
            "<br><a class='actionButton' href=" + URL_PRODUCTS + ">" + goToProductsList + "</a>";
    } else {
        setInputWithUserData();
        showShippingData();
        showPaymentData();
    }
}

function clearCart() {
    localStorage.removeItem("cartItems");
    localStorage.removeItem("shippingWay");
}

function setInputWithUserData() {
    if (localStorage.getItem("lastName") !== null) {
        console.info("LocalStorage! lastName: " + localStorage.lastName +
            ", firstName: " + localStorage.firstName +
            "\npurchaserPhone: " + localStorage.phone +
            ", address: " + localStorage.address);
        let name = "";
        if (localStorage.lastName !== undefined) {
            name += localStorage.lastName;
        }
        if (localStorage.firstName !== undefined) {
            name += localStorage.firstName;
        }
        document.getElementById("purchaser").value = name;
        document.getElementById("purchaserPhone").value = localStorage.phone === undefined? "" : localStorage.phone;
        document.getElementById("shippingAddress").value = localStorage.address === undefined? "" : localStorage.address;
    } else {
        console.info("Query DB!");
        $.ajax({
            type : 'GET',
            url : userEndpoint,
            headers : {
                Authorization : getToken()
            },
            success : function(response) {
                console.log("message: " + response);

                let data = JSON.parse(response);
                if (data.lastName !== null && data.firstName !== null) {
                    document.getElementById("purchaser").value = data.lastName + data.firstName;
                }
                if (data.phone !== null) {
                    document.getElementById("purchaserPhone").value = data.phone;
                }
                if (data.address !== null) {
                    document.getElementById("address").value = data.address;
                }
            },
            error : function(xhr, status, error) {
                console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
            }
        });
    }
}

function showShippingData() {
    let isShop = localStorage.shippingWay === shop;
    document.getElementById("shippingWay").innerHTML = isShop ? shippingWayShop : shippingWayHome;
    document.getElementById("shippingAddress").setAttribute("placeholder", isShop ? shippingHintShop : shippingHintHome);
}

function showPaymentData() {
    if (document.getElementById('account').checked === true) {
        document.getElementById('bankInformation').hidden = false;
    } else if (document.getElementById('visualAccount').checked === true ||
        document.getElementById("creditCard").checked === true) {
        document.getElementById('bankInformation').hidden = true;
    }
}

function reset() {
    let name = "";
    if (localStorage.lastName !== undefined) {
        name += localStorage.lastName;
    }
    if (localStorage.firstName !== undefined) {
        name += localStorage.firstName;
    }
    document.getElementById("purchaser").value = name;
    document.getElementById("purchaserPhone").value = localStorage.phone === undefined? "" : localStorage.phone;
    document.getElementById("shippingAddress").value = localStorage.address === undefined? "" : localStorage.address;
    document.getElementById("receiver").value = "";
    document.getElementById("receiverPhone").value = "";
    document.getElementById("account").checked = true;
    document.getElementById('bankInformation').hidden = false;
    document.getElementById("bankCode").value = "";
    document.getElementById("accountNum").value = "";
    document.getElementById("comment").value = "";
}

function getCartItems() {
    let orderProductsList = [];
    let cartItems = new Map(JSON.parse(localStorage.getItem("cartItems")));
    console.info("cartItems size:" + cartItems.size);

    cartItems.forEach(function(value,key,map) {
        console.info("key :" + key);
        console.info("value :" + value);

        let item = JSON.parse(key);

        //Add product to orderProductsList for order usage
        let orderProduct = item.productName + ";" + item.version + ";"+ value;
        orderProductsList.push(orderProduct);
    });

    return orderProductsList;
}


function addOrder() {
    console.info("onclick addOrder");

    if (!checkInputValid()) {
        console.info("Check input not valid!");
        return;
    }

    showSnackBar(document.getElementById("snackbar"), processing);
    //disable buttons
    document.getElementById("submitButton").disabled = true;

    let order = {};
    order.purchaser = document.getElementById("purchaser").value;
    order.purchaserPhone = document.getElementById("purchaserPhone").value;
    order.receiver = document.getElementById("receiver").value;
    order.receiverPhone = document.getElementById("receiverPhone").value;
    order.shippingWay = localStorage.shippingWay;
    order.shippingAddress = document.getElementById("shippingAddress").value;
    order.products = getCartItems();
    order.paymentMethod = document.querySelector('input[name="payWay"]:checked').value;

    if (document.getElementById('account').checked === true) {
        order.bankInformation = document.getElementById("bankCode").value + document.getElementById("accountNum").value;
    }

    let commentValue = document.getElementById("comment").value;
    if (commentValue === null || isNotEmptyNoSpace(commentValue)) {
        order.comment = commentValue;
    }

    console.info("Add order: " + JSON.stringify(order));

    $.ajax({
        type : 'PUT',
        url : orderEndpoint + "?recaptcha=" +  grecaptcha.getResponse(),
        headers : {
            Authorization : getToken()
        },
        data: JSON.stringify(order),
        success : function(response) {
            console.log("success reponse: " + response);
            showSnackBarAutoClose(document.getElementById("snackbar"), processingSuccess);
            clearCart();
            let orderConfirmURL = URL_ORDER_CONFIRM + "?price=" + JSON.parse(response).price;
            redirect(orderConfirmURL);
        },
        error : function(xhr, status, error) {
            console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
            showSnackBarAutoClose(document.getElementById("snackbar"), cartItemWrong);
            clearCart();
        },
        complete : function (jqxhr, status) {
            console.log( "complete jqxhr: " + JSON.stringify(jqxhr) + ", status: " + status);
            //enable buttons
            document.getElementById("submitButton").disabled = false;

            document.getElementsByClassName("bannerRight")[0].hidden = false;

            if (status !== "success") {
                document.getElementById("accountFields").innerHTML = shoppingFailed +
                    "<br><a href=" + URL_PRODUCTS + ">" + redirectToProductsList + "</a>";
                autoRedirect(URL_PRODUCTS);
            }
        }
    });

}

function checkInputValid() {
    let isValid = true;
    let labelArray = ["labelPurchaser", "labelPurchaserPhone", "labelReceiver", "labelReceiverPhone",
        "labelShippingAddress"];
    let inputArray = ["purchaser", "purchaserPhone", "receiver", "receiverPhone", "shippingAddress"];
    let alertClass = " alert";
    for (let i=0; i<labelArray.length; i++) {
        let label = document.getElementById(labelArray[i]);
        let input = document.getElementById(inputArray[i]);
        console.info(inputArray[i]);
        if (label.className !== null && label.className.indexOf(alertClass) !== -1) {
            console.info(inputArray[i] + " className = " + label.className);
            label.className = label.className.replace(alertClass, "");
        }
        if (input.value === null || input.value === "") {
            label.className += alertClass;
            isValid = false;
        }
    }

    if (document.getElementById("purchaserPhone").value !== "" &&
        !isNumeric(document.getElementById("purchaserPhone").value)) {
        document.getElementById("labelPurchaserPhone").className += alertClass;
        isValid = false;
    }

    if (document.getElementById("receiverPhone").value !== "" &&
        !isNumeric(document.getElementById("receiverPhone").value)) {
        document.getElementById("labelReceiverPhone").className += alertClass;
        isValid = false;
    }

    let payWay = document.getElementById("payWay");
    payWay.className = payWay.className.replace(alertClass, "");
    if (document.getElementById('account').checked === false &&
        document.getElementById('visualAccount').checked === false &&
        document.getElementById('creditCard').checked === false) {
        console.info("pay way not selected.");
        payWay.className += alertClass;
        isValid = false;
    }

    if (document.getElementById('account').checked === true) {
        let inputBankCode = document.getElementById("bankCode");
        let inputAccountNum = document.getElementById("accountNum");
        let labelBankCode = document.getElementById("labelBankCode");
        let labelAccountNum = document.getElementById("labelAccountNum");
        labelBankCode.className = labelBankCode.className.replace(alertClass, "");
        labelAccountNum.className = labelAccountNum.className.replace(alertClass, "");
        if (inputBankCode.value === null || inputBankCode.value === "" || !isNumeric(inputBankCode.value)) {
            labelBankCode.className += alertClass;
            isValid = false;
        }

        if (inputAccountNum.value === null || inputAccountNum.value === "" || !isNumeric(inputAccountNum.value)) {
            labelAccountNum.className += alertClass;
            isValid = false;
        }
    }

    document.getElementById("alert").hidden = isValid;

    return isValid;
}

function sameWithPurchaser() {
    let purchaser = document.getElementById("purchaser").value;
    let receiverPhone = document.getElementById("purchaserPhone").value;
    document.getElementById("receiver").value = purchaser === undefined ? "" : purchaser;
    document.getElementById("receiverPhone").value = receiverPhone === undefined ? "" : receiverPhone;
    document.getElementById("shippingAddress").value = localStorage.address === undefined? "" : localStorage.address;;
}
