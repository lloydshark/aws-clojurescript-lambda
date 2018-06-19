(defproject aws-clojurescript-lambda "0.0.1"
  :description "An example AWS Lambda in ClojureScript."
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async "0.4.474"]
                 [com.bhauman/figwheel-main "0.1.2-SNAPSHOT"]
                 ]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :source-paths ["src"]
  :cljsbuild
  {:test-commands {"unit-test" ["node" "target/test-runner.js"]}
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
             :compiler {:output-to     "target/test-runner.js"
                        :main          "lambda.test-runner"
                        :source-map    true
                        :target        :nodejs
                        :language-in   :ecmascript5
                        :optimizations :none}}]})
