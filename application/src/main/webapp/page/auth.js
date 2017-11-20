let identityPool = 'ap-northeast-1:ab50eb1e-7005-4337-bf74-dae01c60a0cc'; //Cognito Identity Pool
let region = 'ap-northeast-1';
let poolId = 'ap-northeast-1_6cIsdfBKc'; //Cognito User Pool
let clientId = '4ho3iodosfju9a96odrv3gavvb'; //Cognito User Pool App
let appDomain = 'cens.auth.ap-northeast-1.amazoncognito.com';
let userEndpoint = "https://2w1vis3iw8.execute-api.ap-northeast-1.amazonaws.com/Auth/user"; //API Endpoint URL
let orderEndpoint = "http://localhost:9000/order";
let productEndpoint = "https://2vhhlhju8c.execute-api.ap-northeast-1.amazonaws.com/UnAuth/product";

let cookieToken = "id_token";

let tokenScopesArray = ['openid'];

let indexAuthData = {
    ClientId : clientId,
    AppWebDomain : appDomain,
    TokenScopesArray : tokenScopesArray,
    RedirectUriSignIn : 'https://tw.hwangying.com', //CloudFront Distribution URL
    RedirectUriSignOut : 'https://tw.hwangying.com' //CloudFront Distribution URL
}

let productsAuthData = {
    ClientId : clientId,
    AppWebDomain : appDomain,
    TokenScopesArray : tokenScopesArray,
    RedirectUriSignIn : 'https://tw.hwangying.com/products.html', //CloudFront Distribution URL
    RedirectUriSignOut : 'https://tw.hwangying.com/products.html' //CloudFront Distribution URL
}

