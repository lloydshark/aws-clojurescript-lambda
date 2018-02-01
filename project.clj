(defproject aws-clojurescript-lambda "0.0.1"
  :description "Minimal AWS Lambda example in Clojurescript."
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/core.async "0.3.442"]
                 ]
  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.7"]]
  :source-paths ["src"]
  :npm {:dependencies [[source-map-support "0.5.3"]
                       [aws-sdk "2.188.0"]]}
  :cljsbuild
  {:builds [{:id "lambda-build"
             :source-paths ["src"]
             :compiler {:output-to     "app/main.js"
                        :output-dir    "app"
                        :main          "lambda.core"
                        :source-map    true
                        :target        :nodejs
                        :language-in   :ecmascript5
                        :optimizations :none}}]})
