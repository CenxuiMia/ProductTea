let signIn = "登入/註冊"

let signOut = "登出"

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

function setUp(authData) {

    var auth = initCognitoSDK(authData);

    var user = auth.getCurrentUser();

    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });

    if (user != null) {
        console.info("user get Session");
        showSignedIn();
        auth.getSession();

    }else {
        showSignedOut();
    }

    var curUrl = window.location.href;
    auth.parseCognitoWebResponse(curUrl);

    return auth;
}

// Operations when signed in.
function showSignedIn() {
    console.info("show signed in")
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

function showSignedOut() {
    console.info("show signOut")
    document.getElementById("signInButton").innerHTML = signIn;
}

// Initialize a cognito auth object.
function initCognitoSDK(authData) {
    let auth = new AWSCognito.CognitoIdentityServiceProvider.CognitoAuth(authData);
    auth.userhandler = {
        onSuccess: function(result) {
            console.log("Cognito Sign in successful!");
            showSignedIn(result);
            let id_token = auth.signInUserSession.idToken.jwtToken;

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
            showSignedOut();
        }
    };

    // The default response_type is "token", uncomment the next line will make it be "code".
    // auth.useCodeGrantFlow();
    return auth;
}

