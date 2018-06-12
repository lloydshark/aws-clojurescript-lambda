#!/bin/bash

set -e
set -x

lein clean
lein cljsbuild test 
