# aws-clojurescript-lambda

An example project of clojurescript on aws lambda (nodejs) that demonstrates some common base requirements.

Specifically:

- How to build a package for running on aws lambda (using lein-cljsbuild & lein-npm).
- How to setup a figwheel-main repl in Cursive for interactive repl development.
- How to use the raw AWS Javascript (aws-sdk) APIs.
- How to use core.async to handle the responses from the AWS sdk.
- How to use cljs.test for writing tests (and ensure your build fails if a test fails).
- How to access AWS Environment Properties (including KMS encrypted ones).
- How to write and use clojurescript macros.

## Motivation

There's a lot of information to absorb about clojurescript / node / lambda / tooling etc.
Unfortunately given this is an area where there has been much progress it is an area where many blog posts
and other sources of information can sometimes be misleading or contradictory.

I created this project to demonstrate the above features in a single working codebase.

## Usage

```
git clone

cd aws-clojurescript-lambda

lein npm install

./build.sh
```

Produces... 

```
target/lambda.zip
```

Manually upload to AWS.

Set the handler function to be...

```
app/index.main
```

## REPL - A figwheel-main repl in Cursive.

In Cursive choose "Use clojure.main in normal JVM process".

Parameters: "repl/figwheel_repl.clj".

(Make sure you resolved dependencies via lein first).

## Core Async

Given we are using the latest core async and clojurescript we can use the simpler
method of requiring and referring to the core.async functions and methods.

ie. (:require [clojure.core.async :as async :refer [>! <! chan go go-loop]])

See lambda.aws_async.cljs.

## Testing

````./test.sh```

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

To define a clojurescript macro it needs to be defined in a clojure source file.

See lambda.util.macros.clj for an example of defining one and lambda.config for using it.


## TODO

- Package node_modules with the lambda as part of the build.
- Provide a convenience script for creating a role for the lambda to run as.
- Provide a convenience script for uploading the lambda.
- Maybe use npm directly?
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
If you were tagetting the browser then this advice might change - but given we are targeting node
then this is not necessary.

#### Don't set \*main-cli-fn\* 

This interferes with the execution of your lambda as your main function is automatically call.
We export our functions directly for use (see index.js).


## License

Copyright © 2018 Boyd Sharrock.

Distributed under the Eclipse Public License.
