(ns lambda.async
  (:require [cljs.core.async :as async :refer [>! <! chan close!]]
            [lambda.aws :as aws])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

; Use Core Async for handling AWS SDK results..
; ie Return a core async channel than will eventually receive the result.


(defn handle-aws-result
  ([result-channel error result]
   (handle-aws-result result-channel identity error result))
  ([result-channel transform error result]
   (if error
     (async/put! result-channel [error result])
     (async/put! result-channel [error (apply transform (list result))]))))

;; Stacks

(defn list-stacks
  ([s3-client]
   (list-stacks s3-client nil))
  ([s3-client next-token]
   (let [result-channel (async/chan)]
     (aws/list-stacks s3-client (partial handle-aws-result result-channel) next-token)
     result-channel)))

(defn list-all-stacks
  "Returns a channel that will receive all the stacks."
  [s3-client]
  (let [result-channel (async/chan)]
    (go-loop [[error stacks]  (<! (list-stacks s3-client))
               all-stacks     (list)]
          (if (.-NextToken stacks)
            (recur (<! (list-stacks s3-client (.-NextToken stacks)))
                   (concat all-stacks (map #(.-StackName %) (.-StackSummaries stacks))))
            (>! result-channel all-stacks))
           )
    result-channel))

(comment

  (def my-client (aws/new-cfm-client "ap-southeast-2"))

  (def result (list-stacks my-client))

  (def all-stacks-result (list-all-stacks my-client))

  (go (println (count (<! all-stacks-result))))



  )

;; Buckets...

(defn list-buckets
  ([s3-client]
   (list-buckets s3-client identity))
  ([s3-client transform]
   (let [result-channel (async/chan)]
     (aws/list-buckets s3-client
                       (partial handle-aws-result result-channel transform))
     result-channel)))

(defn result->bucket-names [result]
  (map #(.-Name %) (.-Buckets result)))

(defn list-bucket-names [s3-client]
  (list-buckets s3-client result->bucket-names))


(comment

  (def my-s3-client (aws/new-s3-client))

  (go
    (let [[error result] (<! (list-bucket-names my-s3-client))]
      (aws/pretty-print-result-handler error result)))

  )