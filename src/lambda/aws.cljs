(ns lambda.aws)

(defonce AWS (js/require "aws-sdk"))

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