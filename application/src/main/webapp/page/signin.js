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
    if (getToken() !== "") {
        console.info("token" + getToken());
        showSignedIn();
    }else {
        showSignedOut();
    }

    var auth = initCognitoSDK(authData);
    document.getElementById("signInButton").addEventListener("click", function() {
        userButton(auth);
    });

    var curUrl = window.location.href;
    auth.parseCognitoWebResponse(curUrl);

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
    document.cookie = cookieToken + "=;expires=Thu, 01 Jan 1970 00:00:01 GMT;domain=.hwangying.com;path=/";

    console.info("sign out Token :" + getToken())
}

// Initialize a cognito auth object.
function initCognitoSDK(authData) {
    let auth = new AWSCognito.CognitoIdentityServiceProvider.CognitoAuth(authData);
    auth.userhandler = {
        onSuccess: function(result) {
            console.log("Cognito Sign in successful!");
            showSignedIn(result);
            let id_token = auth.signInUserSession.idToken.jwtToken;

            var d = new Date();
            d.setTime(d.getTime() + (60 * 60 * 1000));
            var expires = "expires="+d.toUTCString();
            document.cookie = cookieToken +"=" + id_token + ";expires=" + expires
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
            showSignedOut();
        }
    };

    // The default response_type is "token", uncomment the next line will make it be "code".
    // auth.useCodeGrantFlow();
    return auth;
}

function getToken() {
    var name = cookieToken + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
