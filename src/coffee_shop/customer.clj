(ns coffee-shop.customer
  (:require [clojure.spec.alpha :as s]
            [coffee-shop.coffee :as c]
            [clojure.walk :as w]))

(s/def :customer/satisfaction (s/and int?
                                     #(< % 101)
                                     #(> % 0)))
(s/def :order/item :coffee/drink-type)
(s/def :order/quantity int?)
(s/def :customer/order (s/coll-of (s/keys :req [:order/item
                                                :order/quantity])))
(s/def :customer/filled-order (s/map-of int?
                               (s/map-of keyword? :coffee/drink)))
(s/def :customer/customer (s/keys :req [:customer/order
                                        :customer/satisfaction]
                                  :opt [:customer/filled-order]))

(def testcustomer-before
  {:customer/order [{:order/item :coffee/latte
                     :order/quantity 2}
                    {:order/item :coffee/pour-over
                     :order/quantity 1}]
   :customer/satisfaction 50})

(def testcustomer-after
  {:customer/order [{:order/item :coffee/latte
                     :order/quantity 2}
                    {:order/item :coffee/pour-over
                     :order/quantity 1}]
   :customer/filled-order {1 {:coffee/milk {:coffee/volume 400
                                            :coffee/temp 100
                                            :coffee/taste 10}
                              :coffee/espresso {:coffee/volume 40
                                                :coffee/temp 100
                                                :coffee/taste 10}}
                           2 {:coffee/milk {:coffee/volume 400
                                            :coffee/temp 100
                                            :coffee/taste 8}
                              :coffee/espresso {:coffee/volume 40
                                                :coffee/temp 100
                                                :coffee/taste 5}}}
   :customer/satisfaction 50})

(s/valid? :customer/customer testcustomer-before)
(s/valid? :customer/customer testcustomer-after)

(defn check-order-item [item filled-items]
  (->> filled-items
       (map #(if (s/valid? (item :order/item) %) 1 0))
       (reduce +)
       (= (item :order/quantity))))

(defn check-order-accurate [customer-after]
  (when (customer-after :customer/filled-order)
    (->> customer-after
         :customer/order
         (map #(check-order-item % (vals (customer-after :customer/filled-order))))
         (reduce #(and %1 %2)))))

(check-order-accurate testcustomer-after)

;; TODO - check-order-accuracy that returns a ratio

(defn get-leaves [in-map]
  (->> in-map
       (map #(if (map? (second %))
               (get-leaves (second %))
               %))
       flatten
       (partition 2)))

(defn extract [key in-map] 
  (filter #(= (first %) key) 
          (get-leaves in-map)))

(defn check-order-quality [in-map]
  (->> in-map
       :customer/filled-order
       (extract :coffee/taste)
       (map second)
       (#(int (/ (apply + %) (count %))))))

(check-order-quality testcustomer-after)

;; TODO - defn satisfaction to calculate :customer/satisfaction update
