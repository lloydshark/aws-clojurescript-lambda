# aws-clojurescript-lambda

A minimalist template project for clojurescript on aws lambda (nodejs).

Including...
- Use of lein and lein-cljsbuild.
- Some examples of how to use the raw AWS Javascript (aws-sdk) APIs.
- Some examples of how to use core async to handle the responses.

## Motivation

I wanted to produce a clojurescript lambda with very few dependencies so I could see how it worked. 

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

## REPL

In Cursive choose "Use clojure.main in normal JVM process".

Parameters: "repl/node_repl.clj".

## TODO

- Package node_modules with the lambda as part of the build.
- Provide a convenience script for creating a role for the lambda to run as.
- Provide a convenience script for uploading the lambda.
- Maybe use npm directly?
- Maybe use raw clj cljs tooling instead of lein / lein-cljsbuild?
- Provide some example tests.
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

#### Don't set \*main-cli-fn\* 

This interferes with the execution. We export our functions directly for use (see index.js).


## License

Copyright © 2018 Boyd Sharrock.

Distributed under the Eclipse Public License.
