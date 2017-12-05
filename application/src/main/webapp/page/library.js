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
    if (element.className.indexOf("show") !== -1) {
        element.className = element.className.replace("show", "");
    }

    element.innerHTML = message;
    element.className += "show";
}

function showSnackBarAutoClose(element, message) {
    element.innerHTML = message;
    if (element.className.indexOf("show") !== -1) {
        element.className = element.className.replace("show", "");
    }

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
    window.setTimeout('window.location(redirectURL)', REDIRECT_TIME);
}