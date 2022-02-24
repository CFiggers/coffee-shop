(ns coffee-shop.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

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
