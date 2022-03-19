(ns coffee-shop.barista
  (:require [clojure.spec.alpha :as s]))

(s/def :barista/stat (s/and int?
                            #(< % 6)
                            #(> % 0)))
(s/def :barista/name string?)
(s/def :barista/skill :barista/stat)
(s/def :barista/speed :barista/stat)
(s/def :barista/accuracy :barista/stat)
(s/def :barista/barista (s/keys :req [:barista/name
                                      :barista/skill
                                      :barista/speed
                                      :barista/accuracy]))

(def barista-names
  ["Sonnie" "Tymothy" "Sybil" "Porty" "Coriss" "Ileane" "Brandea" "Claudius" "Elbertine" "Rickey"
   "Arny" "Ardelle" "Diann" "Erik" "Margie" "Doe" "Mohammed" "Dory" "Dougy" "Raleigh" "Trevar"
   "Isidor" "Maible" "Rachelle" "Selinda" "Griffy" "Hewett" "Tulley" "Kellen" "Fin" "Charmane" "Lars"
   "Cosimo" "Any" "Goober" "Sayers" "Dugald" "Katrinka" "Dodie" "Claudie" "Fleurette" "Matias" "Filippo"
   "Nonna" "Shermie" "Steffie" "Boycey" "Lynnette" "Chrysa" "Wendel" "Ericha" "Clay" "Pennie" "Gilbert"
   "Sylvan" "Rex" "Dev" "Lonee" "Joe" "Boot" "Fenelia" "Thorpe" "Rozamond" "Josephina" "Susette"
   "Steffen" "Egor" "Reggis" "Ruddie" "Gussie" "Colman" "Edmund" "Neile" "Danya" "Charleen" "Gus"
   "Shannah" "Vanna" "Cullie" "Lina" "Zach" "Fredek" "Irma" "Regina" "Ivar" "Cookie" "Chas"
   "Elijah" "Katya" "Lila" "Klarrisa" "Meta" "Nancie" "Dorry" "Denice" "Elle" "Gavan" "Kevyn" "Terry"
   "Godfrey" "Nancie" "Hilliard" "Angelique" "Fanechka" "Gabriel" "King" "Doreen" "Carolin" "Antony"
   "Ode" "Ryan" "Dominique" "Tallulah" "Dori" "Koralle" "Aili" "Betsey" "Lanette" "Ricardo" "Shalom"
   "Netta" "Leif" "Bernette" "Serene" "Sara" "Bengt" "Olwen" "Allin" "Karalynn" "Parsifal" "Konrad"
   "Giovanni" "Worden" "Reina" "Elnore" "Lorine" "Tildi" "Mechelle" "Ad" "Ruthe" "Jeth" "Wallie"
   "Noellyn" "Farrand" "Judd" "Dwight" "Cherry" "Mayer" "Tommie" "Ardis" "Delaney" "Saudra" "Orville"
   "Jerrie" "Sioux" "Sidney" "Audi" "Winifred" "Brooks" "Johanna" "Blisse" "Regan" "Steve" "Di" "Gordy"
   "Pamela" "Courtenay" "Kessiah" "Erv" "Shellie" "Zebedee" "Care" "Rik" "Melinda" "Latisha" "Shadow"
   "Abbye" "Robinson" "Cristina" "Dinah" "Nikoletta" "Emilio" "Giffy" "Waylan" "Rubia" "Wallas"
   "Christine" "Nickie" "Hamil" "Neysa" "Fidelia" "Merla" "Pansie" "Carolus" "Rochell" "Giovanna"
   "Karisa" "Sherwynd" "Alida" "Hugo"])

(defn gen-barista []
  {:barista/name (barista-names (dec (rand-int 200)))
   :barista/skill (inc (rand-int 5))
   :barista/speed (inc (rand-int 5))
   :barista/accuracy (inc (rand-int 5))})

;; (gen-barista)
