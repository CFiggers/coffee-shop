(ns coffee-shop.tinkering3
  (:require [asami.core :as d]))

;; (defn coffee-shop-2 [days]
;;   (into (sorted-map)
;;         (map #(vector % (run-day))
;;              (range 1 (inc days)))))

(def db-uri "asami:local://testdb")
(d/create-database db-uri)

(def conn (d/connect db-uri))

(def first-movies [{:movie/title "Explorers"
                    :movie/genre "adventure/comedy/family"
                    :movie/release-year 1985}
                   {:movie/title "Demolition Man"
                    :movie/genre "action/sci-fi/thriller"
                    :movie/release-year 1993}
                   {:movie/title "Johnny Mnemonic"
                    :movie/genre "cyber-punk/action"
                    :movie/release-year 1995}
                   {:movie/title "Toy Story"
                    :movie/genre "animation/adventure"
                    :movie/release-year 1995}])

(def new-movie [{:movie/title "Scott Pilgrim vs the World"
                 :movie/genre "romance/action/fantasy/comedy"
                 :movie/release-year 2010}])

(d/transact conn {:tx-data first-movies})

(d/transact conn new-movie)

(def db (d/db conn))

(print db)

(d/q '[:find ?movie-title ?year
       :where 
         [?m :movie/title ?movie-title]
         [?m :movie/release-year ?year]] db)

(d/release conn)

(d/shutdown)

