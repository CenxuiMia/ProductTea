/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(productAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
    // localStorage.removeItem("cartItems"); //For testing
});

let productDataAsKey;
let name = getParameterByName("name");
let version = getParameterByName("version");

console.info("name :" + name);
console.info("version :" + version);

$.ajax({
    type : 'GET',
    url : productEndpoint + "/" + name + "/" + version,
    success : function(response) {
        console.log("product: " + response);
        productDataAsKey = response;

        let data = JSON.parse(response);
        document.getElementById("smallImage").setAttribute("src", data.smallImage);
        document.getElementById("productName").innerHTML = data.productName;
        document.getElementById("version").innerHTML = data.version;
        document.getElementById("introduction").innerHTML = data.introduction;
        document.getElementById("price").innerHTML = data.price;
        document.getElementById("video").setAttribute("src", data.video);

        let images = document.getElementById("images");
        let imgSize = data.images.length;
        for (let i=0; i<imgSize; i++) {
            let image = document.createElement("img");
            image.src = data.images[i];
            images.appendChild(image);
        }
    },
    error : function(xhr, status, error) {
        console.log("error: " + error + ", status: " + status);
    }
});

function getParameterByName(name) {
    let url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function addToCart() {
    let cartItems;
    let selected = document.getElementById("count");
    let count = parseInt(selected.options[selected.selectedIndex].value);
    console.info("selected count = " + count);

    if (localStorage.getItem("cartItems") === null) {
        console.info("Init cartItems to localstorage");
        cartItems = new Map();
    } else {
        console.info("Localstorage has cartItems");
        cartItems = new Map(JSON.parse(localStorage.getItem("cartItems")));
        console.info("Get old cartItems: " + JSON.parse(localStorage.getItem("cartItems")));
        if (cartItems.has(productDataAsKey)) {
            console.info("Just update cartItems to localstorage");
            count += parseInt(cartItems.get(productDataAsKey));
            console.info("count += " + count);
        }
    }
    cartItems.set(productDataAsKey, count);
    localStorage.setItem("cartItems", JSON.stringify(Array.from(cartItems.entries())));

    console.info("localstorage getItem: " + localStorage.getItem("cartItems"));
    console.info("JSON parse: " + JSON.parse(localStorage.cartItems));
}