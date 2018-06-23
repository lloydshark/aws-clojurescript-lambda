#!/bin/bash

if [ $# -ne 2 ]
  then
    echo "Must supply: 1. AWS account id. 2. The name to call of the lambda role (and policy)."
    echo
    echo "ie ./create-role.sh <aws-account-id> <my-lambda-role>"
    exit -1
fi

set -x
set -e

aws iam create-role --role-name "$2" --assume-role-policy-document fileb://iam/lambda-role.json

aws iam create-policy --policy-name "$2" --policy-document fileb://iam/lambda-policy.json

aws iam attach-role-policy --role-name "$2" --policy-arn "arn:aws:iam::$1:policy/$2"



