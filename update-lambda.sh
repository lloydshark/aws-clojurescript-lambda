#!/bin/bash

if [ $# -ne 1 ]
  then
    echo "Must supply: 1. The name of the lambda."
    echo
    echo "ie ./update-lambda.sh my-great-lambda"
    exit -1
fi

set -x

aws lambda update-function-code --function-name "$1" --zip-file fileb://target/lambda.zip

