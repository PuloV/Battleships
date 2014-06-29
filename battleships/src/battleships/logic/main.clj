(ns battleships.logic.main)

(def all-ships
  "All ships possible to have on the field"
  [{:type :big :size 3}
        {:type :small :size 1}
        {:type :medium :size 2}
        {:type :medium :size 2}
        ])
(def user-ships {})

(defn coord->string
  [x y]
  "Tranclates the coordinates to a string"
  (clojure.string/join "_" [(str x) (str y)] ))

(defn rand-placed
  "Places ships on random spots"
  []
  (loop [ship all-ships
        ships {}]
    (if (empty? ship) ships
         (recur (rest ship)
                (conj ships { (keyword(coord->string (rand-int 10) (rand-int 10))) true })))
  ))
(defn get-field
  "Returns the value of the cell"
  [ships x , y]
  (let [field (coord->string x y)]
     (cond ( = ( (keyword field) ships) false) "X"
           ( = ( (keyword field) ships) "empty") "X"
           ( = ( (keyword field) ships) true) "@"
          :else "-")))

(defn shoot [ships coordX coordY]
  (let [field (coord->string coordX coordY)]
    (println "Shoot: " coordX coordY ships)
     (cond ( = ( (keyword field) ships) true) (-> ships (dissoc field) (assoc (keyword field) false))
           ( = ( (keyword field) ships) false) ships
          :else (-> ships (dissoc field) (assoc  (keyword field) "empty")))))

; (defn get-board [ships]
;   (prn (for [x (range 0 10)
;            y (range 0 10)]
;         (get-field ships x y))))