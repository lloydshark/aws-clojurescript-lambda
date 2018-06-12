(ns lambda.core-test
  (:require [cljs.test :as test :refer-macros [deftest is testing]]))


(deftest meaning-of-life
  (testing "Meaning of life is 42"
    (is (= 42 42))))