(ns lambda.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn ^:export main [event context callback]
  ;(fred aws-sdk)
  (println "started...")
  (println "completed...")
  ;(callback nil #js {:status "OK"})
  )

(set! *main-cli-fn* main)

