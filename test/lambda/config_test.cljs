(ns lambda.config-test
  (:require [cljs.test :as test :refer-macros [async deftest is testing]]
            [clojure.core.async :refer [<! go]]
            [lambda.aws-async :as aws-async]
            [lambda.config :as config])
  (:require-macros [lambda.util.macros :refer [go>!]]))

(defn mock-get-environment-variable [variable]
  (get {"EXAMPLE"     "example-value"
        "SECRET_KEY"  "secr3t-value-base-64"}
       variable))

(defn mock-decrypt [kms-client encrypted-string]
  (go>! "secr3t-value"))


;; We are using async tests so with-redefs won't work.
;;
;; In ClojureScript set! is an explicit rebinding of the var, so for functions
;; this will be permanently changed so keep in mind if you are running more tests.


(set! config/get-environment-variable mock-get-environment-variable)
(set! aws-async/decrypt               mock-decrypt)

(deftest config-test

  (testing "Expected config is decrypted and resolved."

    (async done
           (go
             (let [resolved-config (<! (config/resolve-config :aws-client))]
               (is (= resolved-config
                      {:example    "example-value"
                       :secret-key "secr3t-value"}))
               (done))))))


(comment

  (test/run-tests)

  )
