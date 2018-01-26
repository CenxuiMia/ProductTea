/**
 * Created by huaying on 02/12/2017.
 */
/**
 * This library should be placed as last in html
 */
const CLOSE_TIME = 3000;
const REDIRECT_TIME = 5000;

// Add the "show" class to DIV
function showSnackBar(element, message) {
    element.className = "";
    element.innerHTML = message;
    element.className += "show";
}

function showSnackBarAutoClose(element, message) {
    element.className = "";
    element.innerHTML = message;
    element.className += "show";
    setTimeout(function () {
        element.className = element.className.replace("show", "close");
    }, CLOSE_TIME);
}

//remove the show class from DIV
function closeSnackBar(element) {
    element.className = element.className.replace("show", "");
}

//remove the show class from DIV
function closeSnackBarWithTime(millisecond) {
    if (!isNumber(millisecond)) {
        console.error("closeSnackBar, millisecond should be number");
        millisecond = CLOSE_TIME;
    }
    setTimeout(function () {
        element.className = element.className.replace("show", "");
    }, millisecond);
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function isNotEmptyNoSpace(string) {
    return /\S/.test(string);
}

function autoRedirect(redirectURL) {
    window.setTimeout(function () {
        window.location = redirectURL
    },REDIRECT_TIME);
}

function redirect(redirectURL) {
    window.location = redirectURL;
}

function sendMail() {
    window.location.assign('mailto:support@hwangying.com');
}

function showProgressBar() {
    document.getElementById("progressBar").hidden = false;
}

function hideProgressBar() {
    document.getElementById("progressBar").hidden = true;
}

function updateCartNum() {
    let cartNumDoc = document.getElementById("cartNum");
    let cartStorage = localStorage.getItem("cartItems");
    let cartItems = new Map(JSON.parse(cartStorage));

    if (cartStorage === null || cartItems.size === 0) {
        cartNumDoc.hidden = true;
    } else {
        let num = 0;
        cartItems.forEach(function(value,key,map) {
            num += parseInt(value);
        });
        cartNumDoc.innerHTML = num;
        cartNumDoc.hidden = false;
    }
}

function showImageFadein(el) {
    let windowHeight = $(window).height();
    $(el).each(function(){
        let thisPos = $(this).offset().top;

        let topOfWindow = $(window).scrollTop();
        if (topOfWindow + windowHeight - 200 > thisPos ) {
            $(this).addClass("fadeinUp");
        }
    });
}