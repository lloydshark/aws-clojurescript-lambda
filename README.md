# aws-clojurescript-lambda

A minimalist template project for clojurescript on aws lambda.

Including some examples of how to use the raw AWS Javascript (aws-sdk) API.
...and additionally how to use core async to handle the responses.

## Usage

git clone

cd aws-clojurescript-lambda

lein npm install

./build.sh

Produces target/lambda.zip

Manually upload to AWS.

Set the handler function to be "app/index.main"

## REPL

In Cursive choose "Use clojure.main in normal JVM process".

Parameters: "repl/node_repl.clj"

## TODO

- Examples that use the AWS Javascript SDK.
- Support packaging node_modules.


## License

Copyright © 2018 Boyd Sharrock.

Distributed under the Eclipse Public License.
