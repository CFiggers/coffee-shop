(ns coffee-shop.barista-test
  (:require [coffee-shop.barista :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.test.check.properties :as prop]))

;; (deftest barista-test
;;   (testing "Testing gen-barista"
;;     (testing "should match spec :barista/barista"
;;       #_{:clj-kondo/ignore [:unresolved-symbol]}
;;       (defspec test-spec-gen-barista
;;         100
;;         (prop/for-all []
;;                       (s/valid? :barista/barista (gen-barista)))))))

(deftest barista-test
  (testing "Testing gen-barista"
    (testing "check spec "
      (get-in (first (stest/check `gen-barista))
              [:clojure.spec.test.check/ret :result]))))