/**
 * Created by huaying on 14/11/2017.
 */

// Operations when the web page is loaded.
function onLoad() {
    setUp(productAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );
}

let name = getParameterByName("name");
let version = getParameterByName("version");

console.info("name :" + name);
console.info("version :" + version);

$.ajax({
    type : 'GET',
    url : productEndpoint + "/" + name + "/" + version,
    success : function(response) {
        console.log("product: " + response);

        let data = JSON.parse(response);
        document.getElementById("smallImage").setAttribute("src", data.smallImage);
        document.getElementById("productName").innerHTML = data.productName;
        document.getElementById("introduction").innerHTML = data.introduction;
        document.getElementById("price").innerHTML = "NT." + data.price;
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