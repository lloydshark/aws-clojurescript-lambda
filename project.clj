(defproject aws-clojurescript-lambda "0.0.1"
  :description "Minimal AWS Lambda example in ClojureScript."
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async "0.3.442"]
                 [com.bhauman/figwheel-main "0.1.0"]
                 ]
  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.7"]]
  :source-paths ["src"]
  :npm {:dependencies [[source-map-support "0.5.3"]
                       [aws-sdk "2.188.0"]]}
  :cljsbuild
  {:test-commands {"unit-test" ["node" "target/test-main.js"]}
   :builds [{:id "lambda-build"
             :source-paths ["src"]
             :compiler {:output-to     "app/main.js"
                        :output-dir    "app"
                        :main          "lambda.core"
                        :source-map    true
                        :target        :nodejs
                        :language-in   :ecmascript5
                        :optimizations :none}}
            {:id "unit-test"
             :source-paths ["src" "test"]
             :compiler {:output-to     "target/test-main.js"
                        :main          "lambda.core-test"
                        :source-map    true
                        :target        :nodejs
                        :language-in   :ecmascript5
                        :optimizations :none}}]})
