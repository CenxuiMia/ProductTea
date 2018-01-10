/**
 * Created by huaying on 09/11/2017.
 */

// Operations when the web page is loaded.
$(document).ready(function () {
    setUp(indexAuthData,
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

function showSlides(n) {
    if (typeof n !== 'undefined') {
        slideIndex = n;
    }

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
    setTimeout(showSlides, 3000);
}