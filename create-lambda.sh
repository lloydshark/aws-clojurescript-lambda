#!/bin/bash

if [ $# -ne 2 ]
  then
    echo "Must supply the name to call your lambda and an existing IAM role ARN for it to use. ie ./deploy my-great-lambda arn:aws:iam::<aws account id>:role/<lambda role name>"
    exit -1
fi

set -x

aws lambda create-function --function-name "$1" --runtime "nodejs6.10" --role "$2" --handler "app/index.main" --zip-file fileb://target/lambda.zip




