** paidIndex project include price
** processingIndex project include shipped way address  products receiver phone
** shippedIndex project include KEYS_ONLY


prequest 

1. download dynamodb local 
 http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning
2. add dynamodblocal.jar to project test library

swagger.yaml
accout region
sam.yaml

resource name

app

** dynamodb configuration
all you need to check out is about the table name
true if it is cloud version


deploy to aws 

＊＊you need to set the region and credential before deploy 
 
** you can change the tamplate name and its details to the name you want to deploy

table with table name
aws cloudformation deploy --template-file tea-product-table.yaml --stack-name tea-product-table
aws cloudformation deploy --template-file tea-order-table.yaml --stack-name tea-order-table
aws cloudformation deploy --template-file tea-user-table.yaml --stack-name tea-user-table



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

Product

Product.class ProductKey.class
tea-product-table.yaml

User
User.class UserKey.class
tea-user-table-yaml

** the attribute mapping for secondary index is related to tea-order-table.yaml
make sure the projection mapping and Order.class are right.