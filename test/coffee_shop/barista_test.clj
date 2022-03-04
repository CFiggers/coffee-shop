(ns coffee-shop.barista-test
  (:require [clojure.test :refer :all]
            [coffee-shop.barista :refer :all]))

(deftest a-test
  (testing "Testing gen-barista"
    (testing "should match spec :barista/barista"
      (is (= 0 1)))))
