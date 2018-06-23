#!/bin/bash

if [ $# -ne 3 ]
  then
    echo "Must supply 1. AWS Account Id. 2. Name to call your lambda. 3. Name of an existing IAM role for the lambda."
    echo
    echo "ie ./create-lambda.sh <aws account id> <lambda name> <lambda role name>"
    exit -1
fi

set -x

aws lambda create-function --function-name "$2" --runtime "nodejs6.10" --role "arn:aws:iam::$1:role/$3" --handler "app/index.main" --zip-file fileb://target/lambda.zip




