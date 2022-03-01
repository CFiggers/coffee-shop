(ns coffee-shop.coffee
  (:require [clojure.spec.alpha :as s]))

(s/def :coffee/volume int?)
(s/def :coffee/temp int?)
(s/def :coffee/taste int?)
(s/def :coffee/milk (s/keys :req [:coffee/volume
                                  :coffee/temp
                                  :coffee/taste]))
(s/def :coffee/water (s/keys :req [:coffee/volume
                                   :coffee/temp]))
(s/def :coffee/brew (s/keys :req [:coffee/volume
                                  :coffee/temp
                                  :coffee/taste]))
(s/def :chocolate/type #{:white :dark :milk})
(s/def :chocolate/taste :coffee/taste)
(s/def :coffee/chocolate (s/keys :req [:chocolate/type
                                       :chocolate/taste]))
(s/def :coffee/espresso (s/and :coffee/brew
                               #(< (% :coffee/volume) 100)))
(s/def :coffee/latte (s/keys :req [:coffee/milk
                                   :coffee/espresso]))
(s/def :coffee/frappuccino (s/and :coffee/latte
                                  #(< (:coffee/temp
                                       (% :coffee/milk))
                                      49)
                                  #(< (:coffee/temp
                                       (% :coffee/espresso))
                                      49)))
(s/def :coffee/mocha (s/keys :req [:coffee/latte
                                   :coffee/chocolate]))
(s/def :coffee/cappuccino (s/and :coffee/latte
                                 #(< (:coffee/volume
                                      (% :coffee/milk)) 200)))
(s/def :coffee/pour-over (s/and :coffee/brew
                                #(> (% :coffee/volume) 190)
                                #(> (% :coffee/temp) 35)))
(s/def :coffee/cold-brew (s/and :coffee/brew
                                #(> (% :coffee/volume) 190)
                                #(< (% :coffee/temp) 35)))
(s/def :coffee/americano (s/keys :req [:coffee/espresso
                                       :coffee/water]))
(s/def :coffee/drink-type #{:coffee/americano
                            :coffee/cappuccino
                            :coffee/cold-brew
                            :coffee/espresso
                            :coffee/frappuccino
                            :coffee/latte
                            :coffee/mocha
                            :coffee/pour-over})

(defmacro make-fn [m]
  `(fn [& args#]
     (eval `(~'~m ~@args#))))

(s/def :coffee/drink (apply (make-fn s/or)
                            (interleave (s/describe :coffee/drink-type)
                                        (s/describe :coffee/drink-type))))


(def a-latte
  {:coffee/milk {:coffee/volume 400
                 :coffee/temp 100
                 :coffee/taste 10}
   :coffee/espresso {:coffee/volume 40
                     :coffee/temp 100
                     :coffee/taste 10}})

;; (s/conform :coffee/drink a-latte)

(def a-frappuccino
  {:coffee/milk {:coffee/volume 400
                 :coffee/temp 32
                 :coffee/taste 10}
   :coffee/espresso {:coffee/volume 40
                     :coffee/temp 32
                     :coffee/taste 10}})

;; (s/conform :coffee/drink a-frappuccino)

(def a-cappuccino
  {:coffee/milk {:coffee/volume 150
                 :coffee/temp 100
                 :coffee/taste 10}
   :coffee/espresso {:coffee/volume 40
                     :coffee/temp 100
                     :coffee/taste 10}})

;; (s/conform :coffee/drink a-cappuccino)

(def a-mocha
  {:coffee/latte {:coffee/milk {:coffee/volume 400
                                :coffee/temp 100
                                :coffee/taste 10}
                  :coffee/espresso {:coffee/volume 40
                                    :coffee/temp 100
                                    :coffee/taste 10}}
   :coffee/chocolate {:chocolate/taste 10
                      :chocolate/type :milk}})

;; (s/conform :coffee/drink a-mocha)

(def a-pourover
  {:coffee/volume 200
   :coffee/temp 100
   :coffee/taste 10})

;; (s/conform :coffee/drink a-pourover)

(def a-cold-brew
  {:coffee/volume 200
   :coffee/temp 49
   :coffee/taste 10})

;; (s/conform :coffee/drink a-cold-brew)

(def an-espresso
  {:coffee/volume 70
   :coffee/temp 100
   :coffee/taste 10})

;; (s/conform :coffee/drink an-espresso)

(def an-americano
  {:coffee/espresso {:coffee/volume 70
                     :coffee/temp 100
                     :coffee/taste 10}
   :coffee/water {:coffee/volume 100
                  :coffee/temp 100}})

;; (s/conform :coffee/drink an-americano)

(s/describe :coffee/drink-type)

