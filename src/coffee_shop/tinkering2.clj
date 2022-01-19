(ns coffee-shop.tinkering
  (:require [clojure.spec.alpha :as s]))

(s/def :tinkering2/name string?)
(s/def :tinkering2/skill int?)
(s/def :tinkering2/speed int?)
(s/def :tinkering2/barista (s/keys :req [:tinkering2/name
                                         :tinkering2/skill
                                         :tinkering2/speed]))

(s/valid? :tinkering2/barista {:tinkering2/name "Amy"
                               :tinkering2/skill 5
                               :tinkering2/speed 5})

(defn create-barista [name]
  {:tinkering2/name name
   :tinkering2/skill (+ (rand-int 5) 3)
   :tinkering2/speed (+ (rand-int 5) 3)})

(defn hire-baristas [num]
  (take num 
    (for [name '("Amy" "Betty" 
                 "Charlie" "Daniel" 
                 "Evy" "Fran")]
      (create-barista name))))

