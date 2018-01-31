#!/bin/bash

lein clean
lein cljsbuild once

mkdir target
mv app/ target/
cp index.js target/app
cd target

zip -r lambda.zip app
