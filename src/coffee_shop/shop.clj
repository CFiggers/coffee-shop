(ns coffee-shop.shop
  (:require [clojure.spec.alpha :as s]))

(s/def :equip/quality (s/and int?
                            #(< 0 %)
                            #(> 11 %)))
(s/def :equip/name string?)
(s/def :equip/type #{:equip/espresso-machine
                     :equip/grinder
                     :equip/v60
                     :equip/milk-steamer})
(s/def :shop/equipment (s/keys :req [:equip/type
                                     :equip/name
                                     :equip/quality]))
(s/def :shop/equipments (s/coll-of :shop/equipment))

(def old-espresso-machine 
  {:equip/type :equip/espresso-machine
   :equip/name "Old Espresso Machine"
   :equip/quality 5})

(def average-grinder
  {:equip/type :equip/grinder
   :equip/name "Average Grinder"
   :equip/quality 6})

;; (s/valid? :shop/equipment old-espresso-machine) => true

(s/def :shop/employees (s/map-of string? :barista/barista))
(s/def :shop/ambiance (s/and int?
                            #(< 0 %)
                            #(> 11 %)))
(s/def :shop/shop (s/keys :req [:shop/employees
                                :shop/equipments
                                :shop/ambiance]))

(def starting-shop 
  {:shop/employees {"Ardelle" #:barista{:name "Ardelle", :skill 3, :speed 1, :accuracy 3}}
   :shop/equipments [old-espresso-machine]
   :shop/ambiance 5})

;; (s/valid? :shop/shop starting-shop) => true

;; (defn shop-value [] 
;;     )
