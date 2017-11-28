let signIn = "登入/註冊";
let signOut = "登出";

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

let onSignIn;
let onSignOut;

let userAuth;
let cognitoUser;

function setUp(authData, doSignIn, doSignOut) {
    onSignOut = doSignOut;
    onSignIn = doSignIn;

    let auth = initCognitoSDK(authData);

    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });

    if (auth.getCurrentUser()!== null) {
        console.info("user get Session");
        auth.getSession();
    }else {
        showSignedOut();
    }

    let curUrl = window.location.href;
    auth.parseCognitoWebResponse(curUrl);

    userAuth = auth;
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
            cognitoUser = auth.getCurrentUser();
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
    // userAuth.getSession(); todo
    return userAuth.signInUserSession.idToken.jwtToken;
}
