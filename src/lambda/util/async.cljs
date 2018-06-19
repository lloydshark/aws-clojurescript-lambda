(ns lambda.util.async)

(defn throw-if-error [thing]
  (if (instance? js/Error thing)
    (throw thing)
    thing))