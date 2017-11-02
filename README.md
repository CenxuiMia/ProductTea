prequest 

1. download dynamodb local 
 http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning
2. add dynamodblocal.jar to project test library



deploy to aws 

gradle build -x test 

aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket <YOUR_BUCKET_NAME>

aws cloudformation deploy --capabilities CAPABILITY_IAM --template-file /Users/cenxui/git/tea/output-sam.yaml --stack-name <YOUR_STACK_NAME>