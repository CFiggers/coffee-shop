(ns coffee-shop.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))


(s/def :core/volume int?)
(s/def :core/temp int?)
(s/def :core/taste int?)
(s/def :core/milk (s/keys :req [:core/volume
                                :core/temp
                                :core/taste]))
(s/def :core/espresso (s/keys :req [:core/volume
                                    :core/temp
                                    :core/taste]))
(s/def :core/latte (s/keys :req [:core/milk
                                 :core/espresso]))


(def a-latte
  {:core/milk {:core/volume 400
               :core/temp 100
               :core/taste 10}
   :core/espresso {:core/volume 40
                   :core/temp 100
                   :core/taste 10}})

;; (s/conform :core/latte a-latte)

(defn run-shift [shift]
  (case shift
    :morning {:coffees (+ (rand-int 50) 75)}
    :afternoon {:coffees (+ (rand-int 20) 30)}
    :evening {:coffees (+ (rand-int 10) 10)}))

(defn run-day []
  {:morning (run-shift :morning)
   :afternoon (run-shift :afternoon)
   :evening (run-shift :evening)})

(defn profit [{:keys [morning afternoon evening]}]
  (let [coffee-price 5]
    (* coffee-price
       (+ (:coffees morning)
          (:coffees afternoon)
          (:coffees evening)))))
;; TODO -- Break this into multiple funcs
(defn coffee-shop [difficulty days]
  "1 difficulty = easy, 2 = medium, 3 = hard"
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

;; (coffee-shop 4) 

(defn -main
  "I don't do a whole lot ... yet."
  [arg & args]
  (println (coffee-shop (Integer. arg))))
