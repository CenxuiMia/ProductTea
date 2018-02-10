/**
 * Created by huaying on 27/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(
        indexAuthData,
        function () {
            // signed in
        },
        function () {
            console.info("signed out on user page");
            document.getElementById("signInButton").click();
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
                if (lastName !== null && data.lastName !== undefined) {
                    document.getElementById("lastName").value = lastName = data.lastName;
                }
                if (firstName !== null && data.firstName !== undefined) {
                    document.getElementById("firstName").value = firstName = data.firstName;
                }
                if (phone !== null && data.phone !== null) {
                    document.getElementById("phone").value = phone = data.phone;
                }
                if (address !== null && data.address !== null) {
                    document.getElementById("address").value = address = data.address;
                }

                setLocalStorage(lastName, firstName, phone, address);
            },
            error : function(xhr, status, error) {
                console.log( "error: " + error + ", xhr: " + JSON.stringify(xhr) + ", status: " + status);
            }
        });
    } else {
        console.info("initLocalStorage");
        setInputValue(localStorage.lastName, localStorage.firstName, localStorage.phone, localStorage.address);
    }
}

function setLocalStorage(lastName, firstName, phone, address) {
    if (lastName === null || lastName === "") {
        localStorage.removeItem("lastName");
    } else {
        localStorage.lastName = lastName;
    }

    if (firstName === null || firstName === "") {
        localStorage.removeItem("firstName");
    } else {
        localStorage.firstName = firstName;
    }

    if (phone === null || phone === "") {
        localStorage.removeItem("phone");
    } else {
        localStorage.phone = phone;
    }

    if (address === null || address === "") {
        localStorage.removeItem("address");
    } else {
        localStorage.address = address;
    }

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