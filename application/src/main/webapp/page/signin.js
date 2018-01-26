let signIn = "登入/註冊";
let signOut = "登出";

let onSignIn;
let onSignOut;

let userAuth;

function setUp(authData, doSignIn, doSignOut) {
    onSignOut = doSignOut;
    onSignIn = doSignIn;

    let auth = initCognitoSDK(authData);

    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });

    if (auth.getCurrentUser()!== null) {
        //todo possibly user exists but not signin
        console.info("user get Session");
        auth.getSession();
    }else {
        showSignedOut();
    }

    let curUrl = window.location.href;
    auth.parseCognitoWebResponse(curUrl);

    userAuth = auth;

    initShrinkNavbar();
    initCartNum();
}

// Perform user operations.
function userButton(auth) {
    if (isSignIn()) {
        document.getElementById("signInButton").innerHTML = signIn;
        auth.signOut();
        showSignedOut();
    } else {
        auth.getSession();
    }
}

// Operations when signed in.
function showSignedIn() {
    console.info("show signed in");
    document.getElementById("signInButton").innerHTML = signOut;
    document.getElementById("member").style.display = "block";
    onSignIn();
}


function showSignedOut() {
    console.info("show signed out");
    document.getElementById("signInButton").innerHTML = signIn;
    document.getElementById("member").style.display = "none";
    onSignOut();
}

// Initialize a cognito auth object.
function initCognitoSDK(authData) {
    let auth = new AWSCognito.CognitoIdentityServiceProvider.CognitoAuth(authData);
    auth.userhandler = {
        onSuccess: function(result) {
            console.log("Cognito Sign in successful!");
            showSignedIn();
        },
        onFailure: function(err) {
            console.log("Error!" + err);
            auth.signOut();
            showSignedOut();
        }
    };

    // The default response_type is "token", uncomment the next line will make it be "code".
    // auth.useCodeGrantFlow();
    return auth;
}

function isSignIn() {
    let state = document.getElementById('signInButton').innerHTML;
    let statestr = state.toString();
    return statestr.includes(signOut)
}

function getToken() {
    return userAuth.signInUserSession.idToken.jwtToken;
}

function initShrinkNavbar() {
    $(window).scroll(function() {
        if ($(document).scrollTop() > 10) {
            $('.header').addClass('shrink');
            $('.img').addClass('shrink');
            $('#id_header').addClass('shrink')
        } else {
            $('.header').removeClass('shrink');
            $('.img').removeClass('shrink');
            $('#id_header').removeClass('shrink')
        }
    });
}

function initCartNum() {
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