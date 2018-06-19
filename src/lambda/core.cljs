(ns lambda.core
  (:require [clojure.core.async :refer [<! go]]
            [cljs.nodejs :as nodejs]
            [lambda.aws :as aws]
            [lambda.aws-async :as aws-async]
            [lambda.config :as config])
  (:require-macros [lambda.util.async.macros :refer [<?]]))

(nodejs/enable-util-print!)

(defn ^:export main [event-js context callback]

  (println "This lambda was called with" event-js)

  (callback nil #js{:status "OK"})

  (comment

    ;; Commented out example here, it will only work if you give the role of the lambda
    ;; the right AWS permissions.

    ;; That said - if you are running in a repl it will pick up your local AWS credentials
    ;; and you could try it out

    (go
      (try
        (let [kms-client (aws/new-kms-client "ap-southeast-2")
              s3-client  (aws/new-s3-client)
              config     (<? (config/resolve-config kms-client))]

          (println "Resolved Config:" config)

          (callback js/undefined (aws-async/list-bucket-names s3-client)))

        (catch :default err
          (println "Error!!!" err)))))
  )

