(ns lambda.core-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))


(deftest meaning-of-life

  (testing "Meaning of life is 42"
    (is (= 42 42))))



(run-tests)