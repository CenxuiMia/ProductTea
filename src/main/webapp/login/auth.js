let identityPool = 'ap-northeast-1:ab50eb1e-7005-4337-bf74-dae01c60a0cc'; //Cognito Identity Pool
let region = 'ap-northeast-1';
let poolId = 'ap-northeast-1_6cIsdfBKc'; //Cognito User Pool
let clientId = '4ho3iodosfju9a96odrv3gavvb'; //Cognito User Pool App
let appDomain = 'cens.auth.ap-northeast-1.amazoncognito.com';
let endpoint = "https://9hssiictdl.execute-api.ap-northeast-1.amazonaws.com/prod"; //API Endpoint URL
let authData = {
    ClientId : clientId, 
    AppWebDomain : appDomain,
    TokenScopesArray : ['openid'],
    RedirectUriSignIn : 'https://tw.hangying.com', //CloudFront Distribution URL
    RedirectUriSignOut : 'https://tw.hangying.com' //CloudFront Distribution URL
};

