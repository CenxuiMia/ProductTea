/**
 * Created by huaying on 14/11/2017.
 */

function doSticky() {
    if (window.pageYOffset > sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}