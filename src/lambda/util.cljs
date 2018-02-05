(ns lambda.util
  (:require [cljs.nodejs :as nodejs]
            [goog.string :as gstring]
            [goog.string.format]))


(defn slurp [path]
  (let [fs (nodejs/require "fs")]
    (.readFileSync fs path "utf8")))

(defn gformat [& parameters]
  (apply gstring/format parameters))