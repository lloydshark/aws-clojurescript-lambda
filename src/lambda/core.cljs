(ns lambda.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn ^:export main [event context callback]
  (println event)
  (println context)
  (println callback)
  ;The below line does not work - the error from the AWS console says call of undefined and nothing is printed.
  ;But when you add the when clause it does work. I don't get it.
  ;(callback nil #js {:status "OK"})
  (when callback (callback nil #js {:status "OK"}))
  )

(set! *main-cli-fn* main)

