** processingIndex project include shipped way address  products receiver phone
** project refactor 
  application config
  
  sam-auth.yaml 
  sam-un-auth.yaml
  
  tea-auth-swagger.yaml
  tea-un-auth-swagger.yaml

prequest 

1. download dynamodb local 
 http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning
2. add dynamodblocal.jar to project test library


deploy project

ProductTea
User Pool refactor name and policy
1. cens-user.yaml *

NoSQL Database refactor name

1. tea-order-table.yaml 
2. tea-product-table.yaml
3. tea-user-table.yaml

admin

DynamoDBConfig
S3Bucket

application

DynamoDBConfig
S3Bucket


swagger.yaml

resource name

app

** dynamodb configuration
all you need to check out is about the table name
true if it is cloud version


deploy to aws 

＊＊you need to set the region and credential before deploy 

** 
user pool for login auth
aws cloudformation deploy --template-file cens-user.yaml --stack-name cens

**
web 
aws cloudformation deploy --template-file shop-web.yaml --stack-name tea-web
 
** you can change the tamplate name and its details to the name you want to deploy

table with table name
aws cloudformation deploy --template-file shop-product-table.yaml --stack-name tea-product-table
aws cloudformation deploy --template-file shop-order-table.yaml --stack-name tea-order-table
aws cloudformation deploy --template-file shop-user-table.yaml --stack-name tea-user-table



lambda

go to the application folder

gradle build -x test 

Template with related source name

** auth app 

aws cloudformation package --template-file sam-auth.yaml --output-template-file output-sam-auth.yaml --s3-bucket tea-lambda

aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file output-sam-auth.yaml --stack-name tea-auth

** unauth app

aws cloudformation package --template-file sam-un-auth.yaml --output-template-file output-sam-un-auth.yaml --s3-bucket tea-lambda

aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file output-sam-un-auth.yaml --stack-name tea-un-auth



modify table attribute

Order.class OrderKey.class OrderPaidLastKey.class OrderProcessingLastKey.class OrderShippedLastKey.class 

*Product

Product.class ProductKey.class
tea-product-table.yaml

*User
User.class UserKey.class
tea-user-table-yaml

*Price issue

Product.class Order.class
Price.class 

** the attribute mapping for secondary index is related to tea-order-table.yaml
make sure the projection mapping and Order.class are right.