(ns lambda.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn ^:export main [event-js context callback]

  (println "This lambda was called with" event-js)

  (callback nil #js{:status "OK"}))

