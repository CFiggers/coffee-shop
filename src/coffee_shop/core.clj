(ns coffee-shop.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [coffee-shop.shop :as shop]))

;; TODO(#5): Tests

;; TODO(#6): A function for determining customer satisfaction from a day of coffee shop operation

;; TODO(#7): A function for recording a day of activity in the database

;; TODO(#9): Some way for customer satisfaction to be impacted by the shop's ambiance

;; TODO(#15): Functions that matches customers to baristas
;;  A function for taking a list of newly-generated customers and
;;  matching them with baristas at the shop, balanced based on the 
;;  barista's relative speed and the size of each customer's order

  ;; TODO(#16): A function to determine order complexity
  ;;  A function for determining how long a customer's order will take to fill based on its size/complexity
  ;;  Needs ability to assess complexity of an individual drink

    ;; TODO(#10): A function for determining the complexity/time to fill of an individual drink 
    ;;  Maybe just part of the spec?

;; TODO(#11): A function for taking a customer and a barista and creating a :customer/filled-order 
;;  Should have some margin of error from the barista's accuracy rating

  ;; TODO(#12): Make equipment quality impact drink tastiness
  ;;  A function for increasing or decreasing the tastiness of an order based on the quality of the equipment in the shop

  ;; TODO(#13): Make barista's skill impact drink tastiness
  ;;  A function for increasing or decreasing the tastiness of an order based on the skill of the barista

;; TODOO(#14): BIG: Manager function for making automated decisions about day-to-day operations 
;;   Especially, to start with, shift staffing

(s/def :shift/shift-time #{:shift/morning
                           :shift/afternoon
                           :shift/evening})

(s/fdef run-shift
  :args :shift/shift-time)
(defn run-shift [shift]
  (case shift
    :shift/morning {:coffees (+ (rand-int 50) 75)}
    :shift/afternoon {:coffees (+ (rand-int 20) 30)}
    :shift/evening {:coffees (+ (rand-int 10) 10)}))


(defn run-day []
  {:shift/morning (run-shift :shift/morning)
   :shift/afternoon (run-shift :shift/afternoon)
   :shift/evening (run-shift :shift/evening)}) ;; => {:morning {:coffees 121}, :afternoon {:coffees 38}, :evening {:coffees 13}}


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

(defn manager [shop last-results]
  shop)

(defn better-coffee-shop [{:keys [day
                                  shop
                                  last-results]}]
  (let [up-shop (manager shop last-results)]
    {:day (inc day)
     :shop up-shop
     :last-results last-results}))

(defn init-state [difficulty]
  (let [value (- 100000 (* 2000 (dec difficulty)))]
    {:day 1
     :shop (shop/gen-shop {:value value})
     :last-results {:satisfaction 50}}))

(defn -main
  "I don't do a whole lot ... yet."
  [difficulty days]
  ;; (println (coffee-shop (Integer. difficulty) (Integer. days)))
  (nth (iterate better-coffee-shop (init-state difficulty)) days))

(-main 1 3)
