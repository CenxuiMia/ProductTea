/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(productsAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
});

let productDataAsKey;


$.ajax({
    type : 'GET',
    url : productEndpoint + "/" + getParameterByName("name") + "/" +  getParameterByName("version"),
    success : function(response) {

        let name = getParameterByName("name");
        let version = getParameterByName("version");

        console.info("name :" + name);
        console.info("version :" + version);
        document.getElementById("nest").innerHTML = name + version;

        console.log("product: " + response);
        productDataAsKey = response;

        let data = JSON.parse(response);
        let spec = data.spec.replace(/;/g, "<br>");
        document.getElementById("smallImage").setAttribute("src", data.smallImage);
        document.getElementById("productName").innerHTML = data.productName;
        document.getElementById("version").innerHTML = "-" + data.version + versionName;
        document.getElementById("introduction").innerHTML = data.introduction;
        document.getElementById("spec").innerHTML = spec;
        document.getElementById("price").innerHTML = data.price;
        document.getElementById("details").innerHTML = data.details;

        if (typeof data.originalPrice !== 'undefined') {
            document.getElementById("originalPrice").innerHTML = data.originalPrice;
            document.getElementById("originalPrice").hidden = false;
        }

        let images = document.getElementById("images");
        let imgSize = data.smallImages.length;
        for (let i=0; i<imgSize; i++) {
            let image = document.createElement("img");
            image.src = data.smallImages[i];
            images.appendChild(image);
        }
    },
    error : function(xhr, status, error) {
        console.log("error: " + error + ", status: " + status);
    },
    complete : function(jqxhr, status) {
        showContent();
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

function showContent() {
    hideProgressBar();
    document.getElementById("content").hidden = false;
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
    showSnackBarAutoClose(document.getElementById("addSuccess"), addCartSuccess);

    updateCartNum();

    console.info("localstorage getItem: " + localStorage.getItem("cartItems"));
    console.info("JSON parse: " + JSON.parse(localStorage.cartItems));
}