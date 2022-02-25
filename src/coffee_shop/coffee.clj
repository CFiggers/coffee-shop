(ns coffee-shop.coffee
  (:require [clojure.spec.alpha :as s]))

(s/def :coffee/volume int?)
(s/def :coffee/temp int?)
(s/def :coffee/taste int?)
(s/def :coffee/milk (s/keys :req [:coffee/volume
                                  :coffee/temp
                                  :coffee/taste]))
(s/def :coffee/espresso (s/and
                         (s/keys :req [:coffee/volume
                                       :coffee/temp
                                       :coffee/taste])
                         #(< (% :coffee/volume) 100)))
(s/def :coffee/latte (s/keys :req [:coffee/milk
                                 :coffee/espresso]))

(s/def :coffee/pour-over (s/keys :req [:coffee/volume
                                       :coffee/temp
                                       :coffee/taste]))
(s/def :coffee/drink-type #{:coffee/espresso
                            :coffee/latte
                            :coffee/pour-over})

(defmacro make-fn [m] 
  `(fn [& args#] 
    (eval `(~'~m ~@args#))))

(s/def :coffee/drink (apply (make-fn s/or) (interleave (s/describe :coffee/drink-type) 
                                       (s/describe :coffee/drink-type))))


(def a-latte
  {:coffee/milk {:coffee/volume 400
               :coffee/temp 100
               :coffee/taste 10}
   :coffee/espresso {:coffee/volume 40
                   :coffee/temp 100
                   :coffee/taste 10}})

(def a-pourover
  {:coffee/volume 200
   :coffee/temp 100
   :coffee/taste 10})

(def an-espresso
  {:coffee/volume 70
   :coffee/temp 100
   :coffee/taste 10})


(s/conform :coffee/drink an-espresso)
(s/conform :coffee/drink a-pourover)
(s/conform :coffee/drink a-latte)

(s/describe :coffee/drink-type)
