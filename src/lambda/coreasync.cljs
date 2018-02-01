(ns lambda.coreasync
  (:require [cljs.core.async :as async :refer [>! <! chan close!]]
            [lambda.aws :as aws])
  (:require-macros [cljs.core.async.macros :refer [go]]))

; Use Core Async for handling AWS SDK results..
; ie Return a core async channel than will eventually receive the result.


(defn handle-aws-result
  ([result-channel error result]
   (handle-aws-result result-channel identity error result))
  ([result-channel transform error result]
   (if error
     (async/put! result-channel [error result])
     (async/put! result-channel [error (apply transform (list result))]))))

;; Buckets...

(defn list-buckets
  ([s3-client]
   (list-buckets s3-client identity))
  ([s3-client transform]
   (let [result-channel (async/chan)]
     (aws/list-buckets s3-client
                       (partial handle-aws-result result-channel transform))
     result-channel)))

(defn result->bucket-keys [result]
  (map #(.-Name %) (.-Buckets result)))

(defn list-bucket-keys [s3-client]
  (list-buckets s3-client result->bucket-keys))


(comment

  (def my-s3-client (aws/new-s3-client))

  (go
    (let [[error result] (<! (list-bucket-keys my-s3-client))]
      (aws/pretty-print-result-handler error result)))

  )