(ns coffee-shop.shop
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as st]
            [coffee-shop.barista :as barista]))

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
(s/def :shop/cash int?)
(s/def :shop/shop (s/keys :req [:shop/employees
                                :shop/equipments
                                :shop/ambiance
                                :shop/cash]))

(def starting-shop
  {:shop/employees {"Ardelle" #:barista{:name "Ardelle",
                                        :skill 3,
                                        :speed 1,
                                        :accuracy 3}}
   :shop/equipments [old-espresso-machine]
   :shop/ambiance 5
   :shop/cash 1000})

;; (s/valid? :shop/shop starting-shop) => true

(defn equip-value [equip]
  (* 100 (:equip/quality equip)))

(defn employee-value [emp]
  (let [speed (:barista/speed emp)
        skill (:barista/skill emp)
        accuracy (:barista/accuracy emp)]
    (* 1000 speed skill accuracy)))

(defn gen-equip [& [{:keys [quality type]}]]
  (let [q (or quality (inc (rand-int 10)))
        t (or type (first (shuffle (seq (s/describe :equip/type)))))
        nq (condp < q 9 "Flawless" 7 "Awesome" 5 "Great" 3 "Good" "Old")
        nt (st/replace-first (name t) #"\-" " ")]
    {:equip/type t
     :equip/name (str nq " " nt)
     :equip/quality q}))

(defn ambiance-value [amb]
  (* 1000 amb))

(defn shop-value [{:keys [shop/employees
                          shop/equipments
                          shop/ambiance
                          shop/cash]}]
  (apply +
         (apply + (map equip-value equipments))
         (apply + (map employee-value (vals employees)))
         (list (ambiance-value ambiance)
               cash)))

(defn gen-shop [& [{:keys [staff equip ambiance cash]}]]
  (let [s (or staff (barista/gen-barista))
        e (or equip (gen-equip))
        a (or ambiance (inc (rand-int 10)))
        c (or cash (- 100000 (+ (employee-value s) (equip-value e) (ambiance-value a))))]
    {:shop/employees {(:barista/name s) s}
     :shop/equipments (vector e)
     :shop/ambiance a
     :shop/cash c}))

;; TODO - Fix Out of Bounds exception on this
(take 100 (repeatedly #(shop-value (gen-shop))))

;; (shop-value starting-shop) => 15500
