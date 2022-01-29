(ns coffee-shop.tinkering3)

(comment
  (defn coffee-shop-2 [days]
    (into (sorted-map)
          (map #(vector % (run-day))
               (range 1 (inc days)))))
  )