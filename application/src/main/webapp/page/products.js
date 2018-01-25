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
    queryProductsData();
    showSlides();
});

function queryProductsData() {
    showProgressBar();

    $.get(productEndpoint, function(jsonData, status){
        hideProgressBar();
        console.info("Data: " + jsonData + "\nStatus: " + status);

        let productList = document.getElementsByClassName("prod-list")[0];
        productList.innerHTML = "";

        let data = JSON.parse(jsonData);
        let count = data.products.length;
        for (let i=0; i<count; i++) {
            let queryString = URL_PRODUCT + "?name=" + data.products[i].productName +
                "&version=" + data.products[i].version;
            let originalPrice = JSON.stringify(data.products[i]).includes("originalPrice")?
                "<div class='originalPrice'>" + "NT." + data.products[i].originalPrice + "</div>" : "";
            productList.innerHTML +=
                "<div class='prod-wrapper'>" +
                    "<a href=" + queryString + ">" +
                        "<img src=" + data.products[i].smallImage + " alt='茶品項' style='width: 100%'>" +
                        "<div class='info'>" +
                            "<p><b>" + data.products[i].productName + "</b><br>" + data.products[i].introduction + "</p>" +
                            originalPrice +
                            "<div class='price'>" + "NT." + data.products[i].price + "</div>" +
                        "</div>" +
                    "</a>" +
                "</div>";
        }
    });
}

let slideIndex = 0;
let timer;

function onClickSlide(n) {
    clearTimeout(timer);

    if (typeof n !== 'undefined') {
        slideIndex = n;
    }
    showSlides();
}

function showSlides() {
    let i;
    let slides = document.getElementsByClassName("slides");
    let dots = document.getElementsByClassName("dot");
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" dotActive", "");
    }
    if (slideIndex >= slides.length) {
        slideIndex = 0;
    }
    slides[slideIndex].style.display = "block";
    dots[slideIndex].className += " dotActive";
    slideIndex++;
    timer = setTimeout(showSlides, 3000);
}
