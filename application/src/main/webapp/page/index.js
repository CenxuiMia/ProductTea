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

    showSlidesWithoutDots();
});

let slideIndex = 0;
let timer;

function showSlidesWithoutDots() {
    let i;
    let slides = document.getElementsByClassName("slides");
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    if (slideIndex >= slides.length) {
        slideIndex = 0;
    }
    slides[slideIndex].style.display = "block";
    slideIndex++;
    timer = setTimeout(showSlides, 3000);
}