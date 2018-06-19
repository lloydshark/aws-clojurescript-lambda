(ns lambda.aws-async
  (:require [cljs.core.async :as async :refer [>! <! chan go go-loop]]
            [lambda.util.async :as util-async]
            [goog.object :as gob]
            [lambda.aws :as aws])
  (:require-macros [lambda.util.async.macros :refer [go>! <?]]))

;; Use Core Async for handling AWS SDK results.
;;
;; ie Return a core async channel that will eventually receive the result - or a js/Error.

(defn aws-result->channel
  "Handle the standard AWS sdk callback and place a single result (or error) onto the supplied channel.
  - Optionally supply a transform to apply to the result.
  - For an error ensure it is an instance of js/Error and wrap it if required."
  ([result-channel error result]
   (aws-result->channel result-channel identity error result))
  ([result-channel transform error result]
   (if error
     (if (instance? js/Error error)
       (async/put! result-channel error)
       (async/put! result-channel (js/Error. error)))
     (async/put! result-channel [error (transform result)]))))


;; S3 -----------------------------

(defn list-buckets
  ([s3-client]
   (list-buckets s3-client identity))
  ([s3-client transform]
   (let [result-channel (async/chan)]
     (aws/list-buckets s3-client
                       (partial aws-result->channel result-channel transform))
     result-channel)))

(defn result->bucket-names [result]
  (map #(gob/get % "Name") (gob/get result "Buckets")))

(defn list-bucket-names [s3-client]
  (list-buckets s3-client result->bucket-names))


(comment

  (def my-s3-client (aws/new-s3-client))

  (go
    (try
      (prn (<? (list-bucket-names my-s3-client)))
      (catch :default err
        (println "ERROR!!!" err))))

  )

;; KMS -----------------------------


(defn decrypt [kms-client cipher-text]
  (let [result-channel (async/chan)]
    (aws/decrypt kms-client cipher-text
                 (partial aws-result->channel result-channel #(.toString (gob/get % "Plaintext"))))
    result-channel))