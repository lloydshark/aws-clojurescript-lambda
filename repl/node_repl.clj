(require 'cljs.repl)
(require 'cljs.build.api)
(require 'cljs.repl.node)

(cljs.build.api/build "src"
                      {:main 'lambda.core
                       :output-to "out/main.js"
                       :verbose true})

(cljs.repl/repl (cljs.repl.node/repl-env)
                :watch "src"
                :output-dir "out")
