(ns lambda.test-runner
  (:require [cljs.nodejs :as nodejs]
            [cljs.test :as test :refer-macros [run-tests run-all-tests]]
            [lambda.core-test :as core-test]
            [lambda.config-test :as config-test]))

;;
;; For a CI build we need it to fail (non zero exit code) when the tests fail.
;;
(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (if (test/successful? m)
    (println "Success!")
    (do (println "There were failing tests!")
        (.exit js/process -1))))

;; We explicitly name the namespaces we wish to test...
(run-tests 'lambda.config-test
           'lambda.core-test)