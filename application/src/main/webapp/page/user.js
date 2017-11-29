/**
 * Created by huaying on 27/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(
        userAuthData,
        function () {
            loadUserData();
            updateUserData();
        },
        function () {

        });
});

function initLocalStorage() {
    console.info("initLocalStorage");
    // clearLocalStorage(); //For testing.
    if (localStorage.getItem("lastName") === null) {
        setLocalStorage("", "", "", "");
    } else {
        setInputValue(localStorage.lastName, localStorage.firstName, localStorage.phone, localStorage.address);
    }
}

function setLocalStorage(lastName, firstName, phone, address) {
    localStorage.lastName = lastName;
    localStorage.firstName = firstName;
    localStorage.phone = phone;
    localStorage.address = address;

    console.info("lastName: " + localStorage.lastName +
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
        },
        error : function(xhr, status, error) {
            console.log("token error");
        }
    });

    setLocalStorage(lastName, firstName, phone, address);
}

function cancel() {
    setInputValue(localStorage.lastName, localStorage.firstName, localStorage.phone, localStorage.address);
}