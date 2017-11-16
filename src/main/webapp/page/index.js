/**
 * Created by huaying on 09/11/2017.
 */

let signIn = "登入/註冊"

let signOut = "登出"


const navbar = document.getElementById("navbar");

// Get the offset position of the navbar
const sticky = navbar.offsetTop;

// Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}
//--------------------------------------

//-------------- aws part --------------
AWS.config.update({
    region: region,
    credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: ''
    })
});

AWSCognito.config.region = region;


AWSCognito.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: identityPool
});

AWSCognito.config.update({accessKeyId: 'null', secretAccessKey: 'null'});

// Operations when the web page is loaded.
function onLoad() {
    // Initiatlize CognitoAuth object
    var auth = initCognitoSDK();
    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });
    var curUrl = window.location.href;
    auth.parseCognitoWebResponse(curUrl);
}

// Operations when signed in.
function showSignedIn() {
    let e = document.getElementById("signInButton");
    e.innerHTML = signOut;
    e.className = "login";
}

// Perform user operations.
function userButton(auth) {
    var state = document.getElementById('signInButton').innerHTML;
    var statestr = state.toString();
    if (statestr.includes(signOut)) {
        let e = document.getElementById("signInButton");
        e.innerHTML = signIn;
        e.className = "login";
        auth.signOut();
        showSignedOut();
    } else {
        auth.getSession();
    }
}

// Initialize a cognito auth object.
function initCognitoSDK() {
    let auth = new AWSCognito.CognitoIdentityServiceProvider.CognitoAuth(authData);
    auth.userhandler = {
        onSuccess: function(result) {
            console.log("Cognito Sign in successful!");
            showSignedIn(result);
            let id_token = auth.signInUserSession.idToken.jwtToken;

            var cookieName = cookieToken;
            var cookieValue = id_token;
            var myDate = new Date();
            myDate.setMonth(myDate.getMonth() + 12);
            document.cookie = cookieName +"=" + cookieValue + ";expires=" + myDate
                + ";domain=.hwangying.com;path=/";

            let cognitoParams = {
                IdentityPoolId: identityPool,
                Logins: {}
            };
            cognitoParams.Logins["cognito-idp."+region+".amazonaws.com/"+poolId] = id_token;
            $.ajax({
                type : 'GET',
                url : userEndpoint,
                headers : {
                    Authorization : id_token
                },
                success : function(response) {
                    console.log("user message: " + response)

                },
                error : function(xhr, status, error) {
                    console.log("token error ");
                }
            });
        },
        onFailure: function(err) {
            console.log("Error!" + err);
            auth.signOut();
        }
    };



    // The default response_type is "token", uncomment the next line will make it be "code".
    // auth.useCodeGrantFlow();
    return auth;
}
//--------------------------------------
