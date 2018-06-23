# aws-clojurescript-lambda

An example project of clojurescript on aws lambda (nodejs) that demonstrates common requirements.

Specifically:

- How to build a package for running on aws lambda (using lein-cljsbuild & npm).
- How to setup a nodejs repl in Cursive for interactive repl development.
- How to use the raw AWS Javascript (aws-sdk) APIs.
- How to use core.async to handle the responses from the AWS sdk (including exception handling).
- How to use cljs.test for writing tests (and ensure your build fails if a test fails).
- How to access AWS Environment Properties (including KMS encrypted ones).
- How to write and use clojurescript macros.

## Motivation

There's a lot of information to absorb about clojurescript / node / lambda / tooling etc.

I created this project to demonstrate the above features in a single working codebase.

## Usage

You need [lein](https://leiningen.org/) and [npm](https://www.npmjs.com/) installed.

```
git clone

cd aws-clojurescript-lambda

./build.sh
```

Produces... 

```
target/lambda.zip
```

Now create a Role for the lambda to run as.

Once you have done that, you can create the lambda with the following.

```
./create-lambda.sh <lambda-name> arn:aws:iam::<aws account id>:role/<lambda role name>
```

## Build and Package for AWS lambda.

A simple build script ```./build.sh``` is provided to generate your deployable lambda zip.

The built zip contains 3 things.

1. Your compiled ClojureScript code in /app (built via lein-cljsbuild).
2. Any required node_modules in /node_mdules (resolved via npm).
3. The entrypoint for your lambda defined in /app/index.js.

The aws-sdk is defined as a devDependency in package.json, this means it is not included
in our final zip and decreases package size as AWS Lambda supplies the sdk automatically.


## REPL - A figwheel-main repl in Cursive.

First, resolve npm dependencies via

```npm install```

In Cursive choose "Use clojure.main in normal JVM process".

Two Choices:

(a) Using the core cljs tooling.

Parameters: "repl/node_repl.clj".

(b) Using figwheel-main.

Parameters: "repl/figwheel_repl.clj".


## Core Async

Given that the latest core async and clojurescript is being used, then it is possible to
use the simpler method of requiring and referring to the core.async functions and methods.

ie. (:require [clojure.core.async :as async :refer [>! <! chan go go-loop]])

See src/lambda/aws_async.cljs.

## Testing

```./test.sh```

Runs all your cljs.test tests via the lein-cljsbuild plug-in.

And will return non zero exit code on test failure so that it works well for your CI build.

See test/lambda/test_runner.cljs.

## Config

For a Lambda, a key source of config is the envronment variables.

And often you may find you need to encrypt a environment variable such that it can be committed to source control.

See src/lambda/config.cljs for an example of accessing environment variables - including aws kms encrypted ones.

Here is how you encrypt a "secret" using the aws cli.

```
aws kms encrypt --key-id <kms-key-id> --plaintext "secret" --output text --query CiphertextBlob
```

## Macros

To define a clojurescript macro it needs to be defined in a clojure (.clj) source file.

See src/lambda/util/async/macros.clj for an example of defining one and src/lambda/config.cljs for using it.


## TODO

- Provide a convenience script for creating a role for the lambda to run as.
- Maybe use raw clj cljs tooling instead of lein / lein-cljsbuild?
- Provide some example usage of spec.

## Learnings

#### For AWS Lambda on nodejs you must define the entry point via an module exported function.

This is not something you get from the cljs build tools so we include a small piece of js to provide this. 
For this template we use index.js to provide the export that links to our cljs function.

#### Optimization none - you don't need it when you are targeting node.

You can run into some traps when performing any optimization. As we are targetting node there is 
really no advantage to this. So keep your compiler options as :optimizations :none.

#### To access the AWS js SDK use simple js interop via require (js/require).

If you've been reading along with the advancements about requiring node modules you might be tempted
to do something fancy here.  Don't. The simple js/require is your friend. See lambda.aws namespace.
If you are targetting the browser then this advice might change - but given we are targeting node
then this is not necessary.

#### Don't set \*main-cli-fn\* 

This interferes with the execution of your lambda as your main function is automatically call.
We export our functions directly for use (see index.js).


## License

Copyright © 2018 Boyd Sharrock.

Distributed under the Eclipse Public License.
