(ns lambda.config
  (:require [clojure.core.async :as async :refer [<! go]]
            [lambda.aws-async :as aws-async]
            [goog.object :as gob]
            [lambda.aws :as aws])
  (:require-macros [lambda.util.async.macros :refer [go>! <?]]))


(defn get-environment-variable [name]
  (gob/getValueByKeys js/process "env" name))

(defn must-get-environment-variable [name]
  (if-let [value (get-environment-variable name)]
    value
    (throw (js/Error. (str "Environment Variable [" name "] not defined")))))

(defn base-64 [base-64-encoded-string]
  (.from js/Buffer base-64-encoded-string "base64"))

(defn decrypt-environment-variable [kms-client encrypted-base64-encoded]
  (aws-async/decrypt kms-client (base-64 encrypted-base64-encoded)))

(defn resolve-config
  "Returns a channel that will receive the resolved config - or an error."
  [kms-client]
  (go>!
    {:example    (must-get-environment-variable "EXAMPLE")
     :secret-key (<? (decrypt-environment-variable kms-client
                                                   (must-get-environment-variable "SECRET_KEY")))}))


(comment

  (def kms-client (aws/new-kms-client "ap-southeast-2"))

  (go (prn (<! (resolve-config kms-client))))

  )