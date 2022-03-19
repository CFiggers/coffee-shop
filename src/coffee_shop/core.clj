(ns coffee-shop.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

;; TODO(#4): A function for generating the starting state of a brand new coffee shop

;; TODO(#5): Tests

;; TODO: A function for determining customer satisfaction from a day of coffee shop operation

;; TODO: A function for recording a day of activity in the database

;; TODO: A function for generating a number of new customers per shift
;;  Should be based on the satisfaction from the previous day

;; TODO: Some way for customer satisfaction to be impacted by the shop's ambiance

;; TODO: A function for taking a list of newly-generated customers and
;; matching them with baristas at the shop, balanced based on the 
;; barista's relative speed and the size of each customer's order

  ;; TODO: A function for determining how long a customer's order will
  ;;  take to fill based on its size/complexity

    ;; TODO: A function for determining the complexity/time to fill of an individual drink 
    ;;  Maybe just part of the spec?

;; TODO: A function for taking a customer and a barista and creating a :customer/filled-order 
;;  Should have some margin of error from the barista's accuracy rating

  ;; TODO: Make equipment quality impact drink tastiness
  ;;  A function for increasing or decreasing the tastiness of an order based on the quality of the equipment in the shop

  ;; TODO: Make barista's skill impact drink tastiness
  ;;  A function for increasing or decreasing the tastiness of an order based on the skill of the barista

;; TODOO: BIG: Manager function for making automated decisions about day-to-day operations 
;;   Especially, to start with, shift staffing

(defn run-shift [shift]
  (case shift
    :morning {:coffees (+ (rand-int 50) 75)}
    :afternoon {:coffees (+ (rand-int 20) 30)}
    :evening {:coffees (+ (rand-int 10) 10)}))

(defn run-day []
  {:morning (run-shift :morning)
   :afternoon (run-shift :afternoon)
   :evening (run-shift :evening)}) ;; => {:morning {:coffees 121}, :afternoon {:coffees 38}, :evening {:coffees 13}}


(defn profit [{:keys [morning afternoon evening]}]
  (let [coffee-price 5]
    (* coffee-price
       (+ (:coffees morning)
          (:coffees afternoon)
          (:coffees evening)))))

;; TODO -- Break this into multiple funcs
(defn coffee-shop
  "1 difficulty = easy, 2 = medium, 3 = hard"
  [difficulty days]
  (if (and (< difficulty 4) (> difficulty 0))
    (loop [day 1
           history (sorted-map)
           money (* 1000 (- 4 difficulty))]
      (if (> day days)
        history
        (let [thisday (run-day)
              rent 250
              wages 250
              newmoney (- money rent wages)
              plusprofit (+ newmoney (profit thisday))]
          (if (< newmoney 0)
            (str "You went bankrupt on day " day)
            (do (println (str "You have $" newmoney " left"))
                (recur (inc day)
                       (assoc history day thisday)
                       plusprofit))))))
    "Difficulty must be 1-3!"))

(defn -main
  "I don't do a whole lot ... yet."
  [difficulty days]
  (println (coffee-shop (Integer. difficulty) (Integer. days))))
