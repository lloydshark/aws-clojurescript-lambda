(defproject aws-clojurescript-lambda "0.0.1"
  :description "Minimal AWS Lambda example in clojurescript."
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.671"]
                 [org.clojure/core.async "0.3.442"]
                 ]
  :plugins [[lein-npm "0.6.1"]
            [lein-cljsbuild "1.1.6"]]
  :source-paths ["src"]
  :npm {:dependencies [[source-map-support "0.4.0"]
                       [aws-sdk "2.36.0"]]}
  :cljsbuild
  {:builds [{:id "lambda-build"
             :source-paths ["src"]
             :compiler {:output-to     "app/lambda/main.js"
                        :output-dir    "app/lambda"
                        :main          "lambda.core"
                        :source-map    true
                        :target        :nodejs
                        :language-in   :ecmascript5
                        :optimizations :none}}]})
