let poolId = 'ap-northeast-1_W5kvDZgpy'; //Cognito User Pool
let clientId = '44na9iljebbp8se4t6ojlbr4hj'; //Cognito User Pool App
let appDomain = 'cens.auth.ap-northeast-1.amazoncognito.com';
let userEndpoint = "https://2w1vis3iw8.execute-api.ap-northeast-1.amazonaws.com/Auth/user"; //API Endpoint URL
let orderEndpoint = "https://2w1vis3iw8.execute-api.ap-northeast-1.amazonaws.com/Auth/order";
let productEndpoint = "https://2vhhlhju8c.execute-api.ap-northeast-1.amazonaws.com/UnAuth/product";

let tokenScopesArray = ['phone', 'email', 'profile','openid'];

let indexAuthData = {
    ClientId : clientId,
    AppWebDomain : appDomain,
    TokenScopesArray : tokenScopesArray,
    RedirectUriSignIn : 'https://tw.hwangying.com',
    RedirectUriSignOut : 'https://tw.hwangying.com',
    UserPoolId : poolId,
};

let productsAuthData = {
    ClientId : clientId,
    AppWebDomain : appDomain,
    TokenScopesArray : tokenScopesArray,
    RedirectUriSignIn : 'https://tw.hwangying.com/products.html',
    RedirectUriSignOut : 'https://tw.hwangying.com/products.html',
    UserPoolId : poolId
};

let contactAuthData = {
    ClientId : clientId,
    AppWebDomain : appDomain,
    TokenScopesArray : tokenScopesArray,
    RedirectUriSignIn : 'https://tw.hwangying.com/contact.html',
    RedirectUriSignOut : 'https://tw.hwangying.com/contact.html',
    UserPoolId : poolId
};



