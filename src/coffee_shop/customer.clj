(ns coffee-shop.customer
  (:require [clojure.spec.alpha :as s]
            [com.rpl.specter :refer :all]))

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
                                                :coffee/taste 5}}
                           3 {:coffee/taste 10
                              :coffee/volume 200
                              :coffee/temp 100}}
   :customer/satisfaction 50})

;; (s/valid? :customer/customer testcustomer-before)
;; (s/valid? :customer/customer testcustomer-after)

(defn check-order-item [item filled-items]
  (let [hits (->> filled-items
                  (map #(if (s/valid? (item :order/item) %) 1 0))
                  (reduce +))]
    [(= (item :order/quantity) hits) hits]))

(defn check-order-accurate [customer-after]
  (when (customer-after :customer/filled-order)
    (->> customer-after
         :customer/order
         (map #(first (check-order-item % (vals (customer-after :customer/filled-order)))))
         (reduce #(and %1 %2)))))

(defn check-order-accuracy [customer-after]
  (when (customer-after :customer/filled-order)
    (let [order (:customer/order customer-after)]
      (Math/round
       (double
        (* 10
           (/
            (->> order
                 (map #(second (check-order-item % (vals (customer-after :customer/filled-order)))))
                 (reduce +))
            (apply + (select [(walker :order/quantity) :order/quantity] order)))))))))

(check-order-accurate testcustomer-after)
(check-order-accuracy testcustomer-after)

;; (defn get-leaves [in-map]
;;   (->> in-map
;;        (map #(if (map? (second %))
;;                (get-leaves (second %))
;;                %))
;;        flatten
;;        (partition 2)))

;; (defn extract [key in-map]
;;   (filter #(= (first %) key)
;;           (get-leaves in-map)))

(defn check-order-quality [in-map]
  (->> in-map
       (select [(walker :coffee/taste) :coffee/taste])
       (#(Math/round (double (/ (apply + %) (count %)))))))

(check-order-quality testcustomer-after)

;; Update accuracy-factor to make missing or wrong drinks more or less
;; impactful to the customer's satisfaction.
(defn satisfaction-delta [customer-after]
  (when (customer-after :customer/filled-order)
    (let [accuracy-factor 3
          accuracy (* accuracy-factor (- (check-order-accuracy customer-after) 10))
          quality (check-order-quality customer-after)]
      (+ accuracy quality))))

(satisfaction-delta testcustomer-after)

(defn apply-satisfaction [customer-after]
  (when (customer-after :customer/filled-order)
    (update customer-after
            :customer/satisfaction
            #(+ % (satisfaction-delta customer-after)))))

(apply-satisfaction testcustomer-after)

(defn gen-item []
  {:order/item (rand-nth (vec (s/describe :coffee/drink-type)))
   :order/quantity (rand-nth [1 1 1 1 2 2 3])})

(defn gen-order []
  (case (rand-int 11)
    10 [(gen-item) (gen-item) (gen-item)]
    (7 8 9) [(gen-item) (gen-item)]
    [(gen-item)]))

(gen-order)

(defn gen-customer []
  {:customer/order (gen-order)
   :customer/satisfaction 50})

(gen-customer)

(take 3 (repeatedly gen-customer))
