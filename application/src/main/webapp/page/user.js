/**
 * Created by huaying on 27/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(
        userAuthData,
        function () {
            // signed in
        },
        function () {
            console.info("signed out on user page")
        });

    initLocalStorage();
});

function initLocalStorage() {
    //TODO Use map to store different user data. key = mail
    if (localStorage.getItem("lastName") === null) {
        console.info("init DB & localStorage");
        let lastName = "", firstName = "", phone = "", address = "";
        $.ajax({
            type : 'GET',
            url : userEndpoint,
            headers : {
                Authorization : getToken()
            },
            success : function(response) {
                console.log("message: " + response);

                let data = JSON.parse(response);
                // lastName = data.lastName;
                // firstName = data.firstName;
                // phone = data.phone;
                // address = data.address;
                if (lastName !== null) {
                    document.getElementById("lastName").value = lastName = data.lastName;
                }
                if (firstName !== null) {
                    document.getElementById("firstName").value = firstName = data.firstName;
                }
                if (phone !== null) {
                    document.getElementById("phone").value = phone = data.phone;
                }
                if (address !== null) {
                    document.getElementById("address").value = address = data.address;
                }
            },
            error : function(xhr, status, error) {
                console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
            }
        });
        setLocalStorage(lastName, firstName, phone, address);
    } else {
        console.info("initLocalStorage");
        setInputValue(localStorage.lastName, localStorage.firstName, localStorage.phone, localStorage.address);
    }
}

function setLocalStorage(lastName, firstName, phone, address) {
    if (lastName === null) lastName = "";
    if (firstName === null) firstName = "";
    if (phone === null) phone = "";
    if (address === null) address = "";
    localStorage.lastName = lastName;
    localStorage.firstName = firstName;
    localStorage.phone = phone;
    localStorage.address = address;

    console.info("LocalStorage lastName: " + localStorage.lastName +
        ", firstName: " + localStorage.firstName +
        "\nphone: " + localStorage.phone +
        ", address: " + localStorage.address);
}

function clearLocalStorage() {
    localStorage.removeItem("lastName");
    localStorage.removeItem("firstName");
    localStorage.removeItem("phone");
    localStorage.removeItem("address");
}

function setInputValue(lastName, firstName, phone, address) {
    $("#lastName").val(lastName);
    $("#firstName").val(firstName);
    $("#phone").val(phone);
    $("#address").val(address);
}


function save() {
    console.info("onclick save");
    showSnackBar(document.getElementById("snackbar"), processing);
    //disable buttons
    document.getElementById("submitButton").disabled = true;
    document.getElementById("cancelButton").disabled = true;

    let lastName = document.getElementById("lastName").value;

    let firstName = document.getElementById("firstName").value;

    let phone = document.getElementById("phone").value;

    let address = document.getElementById("address").value;

    let userProfile = {
        lastName : lastName,
        firstName : firstName,
        phone : phone,
        address : address,
    };

    $.ajax({
        type : 'POST',
        url : userEndpoint,
        headers : {
            Authorization : getToken()
        },
        data: JSON.stringify(userProfile),
        success : function(response) {
            console.log("user message: " + response);
            showSnackBarAutoClose(document.getElementById("snackbar"), processingSuccess);
            // setLocalStorageMail(JSON.parse(response).mail);
        },
        error : function(xhr, status, error) {
            console.log("token error");
            showSnackBarAutoClose(document.getElementById("snackbar"), processingFailed);
        },
        complete : function (jqxhr, status) {
            setLocalStorage(lastName, firstName, phone, address);

            //enable buttons
            document.getElementById("submitButton").disabled = false;
            document.getElementById("cancelButton").disabled = false;
        }
    });

}

function cancel() {
    setInputValue(localStorage.lastName, localStorage.firstName, localStorage.phone, localStorage.address);
}