/**
 * Created by huaying on 12/12/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(contactAuthData,
        function () {
            console.info("index on signIn");
        },
        function () {
            console.info("index on signOut");
        }
    );

    showSlides();
});

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