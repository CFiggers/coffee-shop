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

;;DONE -- Figured out how to do this, man
(defn coffee-shop [days]
    (into (sorted-map)
          (map (fn [i] (vector i (run-day)))
               (range 1 (inc days)))))

;; (coffee-shop 4) 

(defn -main
  "I don't do a whole lot ... yet."
  [arg & args]
  (println (coffee-shop (Integer. arg))))
