(ns lambda.aws
  (:require [cljs.pprint :as pprint]))

(defonce AWS (js/require "aws-sdk"))

;; Cloud Formation...

(defn new-cfm-client [region]
  (AWS.CloudFormation. #js {:region region}))

(defn list-stacks [s3-client result-handler next-token]
  (println "list-stacks" next-token)
  (.listStacks s3-client
               (if next-token #js{"NextToken" next-token} #js {})
                result-handler))

(defn list-all-stacks [s3-client ])

(comment

  (def my-cfm-client (new-cfm-client "ap-southeast-2"))

  (list-stacks my-cfm-client pretty-print-result-handler)

  )

;; S3...

(defn new-s3-client []
  (new AWS.S3))

(defn list-buckets [s3-client result-handler]
  (.listBuckets s3-client
                #js {}
                result-handler))

(defn pretty-print-result-handler [error result]
  (if error
    (println "ERROR !!!" error)
    (println (pprint/pprint (js->clj result)))))

(comment

  (def my-s3-client (new-s3-client))

  (list-buckets my-s3-client pretty-print-result-handler)

  )