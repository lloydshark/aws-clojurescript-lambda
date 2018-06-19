#!/bin/bash

set -x
set -e


lein clean
lein cljsbuild once lambda-build

# Create a target/package directory for us to prepare out package...
mkdir target/package

# Copy in our built javascript...
mv app target/package

# Copy in our index file to provide the lambda entry point...
cp index.js target/package/app

# Add in the required node modules...
cp package.json target/package
cd target/package
npm install --only=production
rm package.json

# Now that we have the package, zip it up.
zip -r ../lambda.zip *
