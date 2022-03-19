(ns coffee-shop.customer-test
  (:require [clojure.test :refer :all]
            [coffee-shop.customer :refer :all]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.test.check.properties :as prop]))

(deftest order-test
  (testing "Testing gen-order"
    (testing "should match spec :customer/order"
      #_{:clj-kondo/ignore [:unresolved-symbol]}
      (defspec test-spec-gen-order
        100
        (prop/for-all []
                      (s/valid? :customer/order (gen-order)))))))

(deftest customer-test
  (testing "Testing gen-customer"
    (testing "should match spec :customer/customer"
      #_{:clj-kondo/ignore [:unresolved-symbol]}
      (defspec test-spec-gen-customer
        100
        (prop/for-all []
                      (s/valid? :customer/customer (gen-customer)))))))