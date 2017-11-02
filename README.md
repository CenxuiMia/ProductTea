prequest 

1. download dynamodb local 
 http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning
2. add dynamodblocal.jar to project test library



deploy to aws 

＊＊you need to set the region and credential before deploy 
 
** you can change the tamplate name and its details to the name you want to deploy

table
aws cloudformation deploy --template-file tea-product-table.yaml --stack-name teaProductTable
aws cloudformation deploy --template-file tea-order-table.yaml --stack-name teaOrderTable



lambda

-gradle build -x test 

** you can change the sam.yaml details about lambda evironment valuable

-aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket <YOUR_BUCKET_NAME>

-aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file /Users/cenxui/git/tea/output-sam.yaml --stack-name <YOUR_STACK_NAME>