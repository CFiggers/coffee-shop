(ns coffee-shop.core-test
  (:require [clojure.test :refer :all]
            [coffee-shop.core :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(deftest test-coffee-shop
  (testing "Testing coffee-shop"
    (testing "Returns correct spec"
      (get-in (first (stest/check `coffee-shop))
              [:clojure.spec.test.check/ret :result]))))

;; (deftest test-init-state
;;   (testing "Testing init-state"
;;     (testing "Matches fdef"
;;       (get-in (first (stest/check `init-state))
;;               [:clojure.spec.test.check/ret :result]))))
