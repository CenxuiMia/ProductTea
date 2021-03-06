## Product Tea in AWS

### Architecture

![alt text](./product-tea.png)

### Advantage
1. micro-service
2. zero-downtime
3. auto-scalling
4. pay as you go

We use aws cloudformation tamplate to delpoy stack for this project, take adventage cloudformation we able to deploy our infrastructure immediately and with zero down time CI/CD.
We are able to deploy new version product in two minutes.

sample website 
https://hwangying.com/

### Application ###

1. web app in S3 bucket
2. application deploy in Lambda
3. admin run on laptop

#### admin ####

1. DynamoDBConfig
2. S3Bucket

#### application ####

1. DynamoDBConfig
2. S3Bucket

#### webapp ####

1. auth.js


#### NoSQL Database template name ####

1. shop-order-table.yaml 
2. shop-product-table.yaml
3. cens-user-table.yaml


deploy to aws 

＊＊you need to set the region and credential before deploy
https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-welcome.html



 
### Deploy

#### user pool for login auth
aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file cens-user.yaml --stack-name cens
aws cloudformation deploy --template-file cens-user-table.yaml --stack-name cens-user-table

note the user pool id


#### web app static website
aws cloudformation deploy --template-file shop-web.yaml --stack-name tea-web

note the bucket name
 
#### deploy dynamodb table 
aws cloudformation deploy --template-file shop-product-table.yaml --stack-name tea-product-table
aws cloudformation deploy --template-file shop-order-table.yaml --stack-name tea-order-table
aws cloudformation deploy --template-file shop-coupon-table.yaml --stack-name tea-coupon-table
aws cloudformation deploy --template-file shop-point-table.yaml --stack-name tea-point-table


#### deploy lambda lambda function

make sure the all the Class in config package.

go to the application folder

gradle build -x test 

Template with related source name

#### auth app

modify the shop-auth-swagger.yaml securityDefinitions user pool to note the user pool id

aws cloudformation package --template-file sam-auth.yaml --output-template-file output-sam-auth.yaml --s3-bucket tea-lambda

aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file output-sam-auth.yaml --stack-name tea-auth

#### unauth app 

aws cloudformation package --template-file sam-un-auth.yaml --output-template-file output-sam-un-auth.yaml --s3-bucket tea-lambda

aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file output-sam-un-auth.yaml --stack-name tea-un-auth

#### Cognito User Pool

add the signin url and sign out url
add login domain 


#### web app deploy 

modify auth.js 

deploy the web app to note the bucket name

#### admin dashboard 

1. make sure the all the Class in config package.

2. make sure auth.js

3. run the application.class

4. browsing http://localhost:9000/dashboard.html

if modify table attribute below


##### CI/CD #####

Order.class OrderKey.class OrderPaidLastKey.class OrderProcessingLastKey.class OrderShippedLastKey.class 

*Product

Product.class ProductKey.class
shop-product-table.yaml

*User
User.class UserKey.class
cens-user-table-yaml


** the attribute mapping for secondary index is related to shop-order-table.yaml
make sure the projection mapping and Order.class are right.

local version

1. download dynamodb local 
 http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning
2. add dynamodblocal.jar to project test library