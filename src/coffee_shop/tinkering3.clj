(ns coffee-shop.tinkering3
  (:require [datascript.core :as d]))

;; (defn coffee-shop-2 [days]
;;   (into (sorted-map)
;;         (map #(vector % (run-day))
;;              (range 1 (inc days)))))

;; (def conn (d/create-conn {}))

(def schema {:car/maker {:db/type :db.type/ref}
             :car/colors {:db/cardinality :db.cardinality/many}
             :car/name {:db/unique :db.unique/identity}})

(def conn (d/create-conn schema))

(d/transact! conn [{:maker/name "Honda"
                    :maker/country "Japan"}])

(d/transact! conn [{:db/id -1
                    :maker/name "BMW"
                    :maker/country "Germany"}
                   {:car/maker -1
                    :car/name "i525"
                    :car/colors ["red" "green" "blue"]}])

(d/q '[:find ?name
       :where
       [?e :maker/name "BMW"]
       [?c :car/maker ?e]
       [?c :car/name ?name]]
     @conn)

(d/q '[:find ?name
       :where
       [3 :car/name ?name]]
     @conn)

(d/entity @conn [:car/name "i525"])