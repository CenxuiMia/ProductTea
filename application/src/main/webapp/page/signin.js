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
    onSignIn = doSignIn

    let auth = initCognitoSDK(authData);

    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });

    if (auth.getCurrentUser()!== null) {
        console.info("user get Session");
        showSignedIn();
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
    onSignIn();
}


function showSignedOut() {
    console.info("show signOut");
    document.getElementById("signInButton").innerHTML = signIn;
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
            let id_token = auth.signInUserSession.idToken.jwtToken;

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
    userAuth.getSession();
    return userAuth.signInUserSession.idToken.jwtToken;
}

function updateUserData(attributeList) {

    var attribute = new AWSCognito.CognitoIdentityServiceProvider.CognitoUserAttribute(attribute);
    attributeList.push(attribute);

    cognitoUser.updateAttributes(attributeList, function(err, result) {
        if (err) {
            alert(err);
            return;
        }
        console.log('call result: ' + result);
    });
}

function loadUserData() {
    let userData;
    cognitoUser.getUserAttributes(function(err, result) {
        if (err) {
            alert(err);
            return;
        }
        userData = result;
    });
    return userData;
}