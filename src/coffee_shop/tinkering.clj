(ns coffee-shop.tinkering
  (:require [clojure.spec.alpha :as s]))

(defrecord Barista [skill speed])

(defn create-barista [name]
  (let [skill (+ (rand-int 5) 3)
        speed (+ (rand-int 5) 3)]
    (eval `(def ~name (Barista.
                       ~skill
                       ~speed)))
    (println
     (str "Hired " name "! "
          "Stats: skill " skill
          ", speed " speed))))

(defn hire-baristas [num]
  (try 
    (let [namelist ['Ashley 'Betty 'Charlie]]
         (dotimes [n num]
           (create-barista
            (nth namelist n))))
    (catch Exception e "Didn't work!")))

(hire-baristas 3)