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
    document.getElementById("lastName").setAttribute("value", lastName);
    document.getElementById("firstName").setAttribute("value", firstName);
    document.getElementById("phone").setAttribute("value", phone);
    document.getElementById("address").setAttribute("value", address);
}

function save() {
    var attributeList = [];
    let lastName = document.getElementById("lastName").value;
    if (lastName !== null) {
        var attribute = {
            Name : 'lastName',
            Value : lastName
        };
        attributeList.push(attribute);
    }

    let firstName = document.getElementById("firstName").value;
    if (firstName !== null) {
        var attribute = {
            Name : 'firstName',
            Value : firstName
        };
        attributeList.push(attribute);
    }

    let phone = document.getElementById("phone").value;
    if (phone !== null) {
        var attribute = {
            Name : 'phone',
            Value : phone
        };
        attributeList.push(attribute);
    }

    let address = document.getElementById("address").value;
    if (address !== null) {
        var attribute = {
            Name : 'address',
            Value : address
        };
        attributeList.push(attribute);
    }

    let  cognitoUser = userPool.getCurrentUser();
    cognitoUser.updateAttributes(attributeList, function(err, result) {
        if (err) {
            alert(err);
            return;
        }
        console.log('call result: ' + result);
    });

    setLocalStorage(lastName, firstName, phone, address);
}