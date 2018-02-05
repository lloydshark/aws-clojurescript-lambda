(ns lambda.aws)

(defonce AWS (js/require "aws-sdk"))

;; Cloud Formation...

(defn new-cfm-client [region]
  (AWS.CloudFormation. #js {:region region}))

(defn list-stacks [s3-client result-handler]
  (.listStacks s3-client
                #js {}
                result-handler))

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
    (println (cljs.pprint/pprint (js->clj result)))))

(comment

  (def my-s3-client (new-s3-client))

  (list-buckets my-s3-client pretty-print-result-handler)

  )